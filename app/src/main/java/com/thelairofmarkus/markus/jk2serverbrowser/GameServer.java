package com.thelairofmarkus.markus.jk2serverbrowser;

/**
 * Created by markus on 17.2.2016.
 */
public class GameServer extends Server {

    public final int ping;
    public final int players;
    public final String serverName;

    public GameServer(String ipAddress, int port, int ping, String serverName, int players) {
        super(ipAddress, port);
        this.ping = ping;
        this.serverName = serverName;
        this.players = players;
    }
}
