package com.thelairofmarkus.markus.jk2serverbrowser.domain;

/**
 * Created by markus on 18.2.2016.
 */
public class GameServerStatus extends GameServer {

    public GameServerStatus(String ipAddress, int port, int ping, String serverName, int players) {
        super(ipAddress, port, ping, serverName, players);
    }

}
