package com.thelairofmarkus.markus.jk2serverbrowser;

import java.nio.charset.Charset;

/**
 * Created by markus on 20.2.2016.
 */
public class GetInfoParser implements ServerResponseParser {
    @Override
    public ServerResponse parse(byte[] msg) {
        ServerResponse response = new ServerResponse(ResponseType.INFO_RESPONSE);

        String msgAsString = new String(msg, Charset.forName("UTF-8"));

        String[] pieces = msgAsString
                .replaceAll("\\^[0-9]", "")
                .split("\\\\");

        for (int i = 1; i < pieces.length-1; i += 2) {
            response.addKeyValPair(pieces[i], pieces[i+1]);
        }

        return response;
    }
}
