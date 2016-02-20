package com.thelairofmarkus.markus.jk2serverbrowser.parser;

import com.thelairofmarkus.markus.jk2serverbrowser.udp.ByteHelper;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.ResponseType;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.ServerResponse;

/**
 * Created by markus on 20.2.2016.
 */
public class GetServersParser implements IServerResponseParser {

    private static final byte RESPONSE_PREFIX_SEPARATOR = 0x5c;

    @Override
    public ServerResponse parse(byte[] msg) {
        ServerResponse response = new ServerResponse(ResponseType.GETSERVERS_RESPONSE);

        for (int i = ByteHelper.firstIndex(msg, RESPONSE_PREFIX_SEPARATOR) + 1; i < msg.length - 3; i+=7) {
            String ip = String.format("%d.%d.%d.%d", msg[i] & 0xFF, msg[i+1] & 0xFF, msg[i+2] & 0xFF, msg[i+3] & 0xFF);
            int port = ((msg[i+4] & 0xFF) << 8) + (msg[i+5] & 0xFF);
            response.addKeyValPair(ip, Integer.toString(port));
        }

        return response;
    }
}
