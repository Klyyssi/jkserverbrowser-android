package com.thelairofmarkus.markus.jk2serverbrowser.udp;

import com.thelairofmarkus.markus.jk2serverbrowser.domain.ResponseType;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.Server;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.ServerResponse;
import com.thelairofmarkus.markus.jk2serverbrowser.parser.GetInfoParser;
import com.thelairofmarkus.markus.jk2serverbrowser.parser.GetServersParser;
import com.thelairofmarkus.markus.jk2serverbrowser.parser.GetStatusParser;
import com.thelairofmarkus.markus.jk2serverbrowser.parser.IServerResponseParser;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by markus on 19.2.2016.
 */
public class UdpConnection implements IUdpConnection {

    private DatagramSocket serverSocket;
    private static final Map<ResponseType, IServerResponseParser> responseTypeToParser = new HashMap<>();
    private static final byte LINEFEED = 0x0A;

    static {
        responseTypeToParser.put(ResponseType.GETSERVERS_RESPONSE, new GetServersParser());
        responseTypeToParser.put(ResponseType.INFO_RESPONSE, new GetInfoParser());
        responseTypeToParser.put(ResponseType.GETSTATUS_RESPONSE, new GetStatusParser());
    }

    public UdpConnection() throws IOException {
        serverSocket = new DatagramSocket();
        serverSocket.setSoTimeout(3000);
    }

    @Override
    public void close() {
        serverSocket.close();
    }

    @Override
    public void send(Server server, byte[] message) throws IOException {
        InetAddress address = InetAddress.getByName(server.ipAddress);
        InetSocketAddress inetAddr = new InetSocketAddress(address, server.port);
        DatagramPacket packetOut = new DatagramPacket(message, message.length, inetAddr.getAddress(), inetAddr.getPort());
        serverSocket.send(packetOut);
    }

    @Override
    public ServerResponse receive() throws IOException {
        byte[] dataIn = new byte[2048];
        DatagramPacket packetIn = new DatagramPacket(dataIn, dataIn.length);
        serverSocket.receive(packetIn);

        ResponseType responseType = inferResponseTypeFrom(dataIn);

        ServerResponse response = responseTypeToParser
                .get(responseType)
                .parse(ByteHelper.trim(dataIn));

        response.addMetaData("port", Integer.toString(packetIn.getPort()));
        response.addMetaData("ip", packetIn.getAddress().getHostAddress());

        return response;
    }

    private ResponseType inferResponseTypeFrom(byte[] rawServerResponse) {
        String responsePrefix = getMessagePrefix(rawServerResponse);
        return stringToResponseType(responsePrefix);
    }

    private ResponseType stringToResponseType(String string) {
        ResponseType responseType = null;
        for (ResponseType type : ResponseType.values()) {
            if (string.startsWith(type.startsWith)) {
                responseType = type;
            }
        }
        if (responseType == null) {
            throw new NullPointerException("Unknown response type");
        }
        return responseType;
    }

    private String getMessagePrefix(byte[] rawServerResponse) {
        byte[] prefix = Arrays.copyOfRange(rawServerResponse, 4, ByteHelper.firstIndex(rawServerResponse, LINEFEED));
        return new String(prefix, Charset.forName("UTF-8"));
    }
}
