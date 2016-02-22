package com.thelairofmarkus.markus.jk2serverbrowser.domain;

import java.util.List;

/**
 * Created by markus on 18.2.2016.
 */
public class GameServerStatus extends GameServer {

    public final List<Player> players;
    public final String map;
    public final String mod;

    public GameServerStatus(String ipAddress, int port, int ping, String serverName, int players, List<Player> playerList, String map, String mod) {
        super(ipAddress, port, ping, serverName, players);
        this.players = playerList;
        this.map = map;
        this.mod = mod;
    }


}
