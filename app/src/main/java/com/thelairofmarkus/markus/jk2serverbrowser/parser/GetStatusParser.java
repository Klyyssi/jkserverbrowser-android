package com.thelairofmarkus.markus.jk2serverbrowser.parser;

import com.thelairofmarkus.markus.jk2serverbrowser.domain.Player;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.ResponseType;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.ServerResponse;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by markus on 22.2.2016.
 */
public class GetStatusParser implements IServerResponseParser {

    public static final String KEY_PLAYER = "Player";
    private static final byte RESPONSE_LINEFEED = 0x0a;

    @Override
    public ServerResponse parse(byte[] msg) {
        ServerResponse response = new ServerResponse(ResponseType.GETSTATUS_RESPONSE);

        String msgAsString = new String(msg, Charset.forName("UTF-8"));
        String[] splitMsg = msgAsString.split("\n");

        String serverStatus = splitMsg[1];
        String[] players = Arrays.copyOfRange(splitMsg, 2, splitMsg.length);

        String[] pieces = serverStatus
                .split("\\\\");

        for (int i = 1; i < pieces.length - 1; i+=2) {
            response.addKeyValPair(pieces[i], pieces[i+1]);
        }

        for (String player : players) {
            response.addKeyValPair(KEY_PLAYER, player.replaceAll("\\^[a-z0-9]|\"", ""));
        }

        return response;
    }

    /**
     * Score Ping Name
     *
     * e.g. 1 50 Padawan
     */
    public static Player parse(String fromString) {
        String[] pieces = fromString.split(" ");
        return new Player(pieces[2], Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]));
    }
}
