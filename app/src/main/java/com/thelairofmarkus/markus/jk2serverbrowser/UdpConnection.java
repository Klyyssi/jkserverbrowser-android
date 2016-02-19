package com.thelairofmarkus.markus.jk2serverbrowser;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Created by markus on 19.2.2016.
 */
public class UdpConnection implements IUdpConnection {

    private DatagramSocket serverSocket = new DatagramSocket();

    public UdpConnection() throws IOException {

    }

    @Override
    public void close() {
        serverSocket.close();
    }

    @Override
    public void send(Server server, byte[] message) throws IOException {
        InetSocketAddress inetAddr = new InetSocketAddress(server.ipAddress, server.port);
        DatagramPacket packetOut = new DatagramPacket(message, message.length, inetAddr.getAddress(), inetAddr.getPort());
        serverSocket.send(packetOut);
    }

    @Override
    public ServerResponse receive() throws IOException {
        byte[] dataIn = new byte[2048];
        DatagramPacket packetIn = new DatagramPacket(dataIn, dataIn.length);
        serverSocket.receive(packetIn);
        String responseRaw = new String(dataIn, "UTF-8");
        String[] pieces = responseRaw.replaceAll("\\^[0-9]", "").split("\\\\");
        ServerResponse response = new ServerResponse(ResponseType.INFO_RESPONSE);

        for (int i = 1; i < pieces.length-1; i += 2) {
            response.addKeyValPair(pieces[i], pieces[i+1]);
        }

        response.addKeyValPair("port", Integer.toString(packetIn.getPort()));
        response.addKeyValPair("ip", packetIn.getAddress().toString());

        return response;
    }
}
