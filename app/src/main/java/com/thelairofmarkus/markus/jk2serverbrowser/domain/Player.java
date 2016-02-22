package com.thelairofmarkus.markus.jk2serverbrowser.domain;

/**
 * Created by markus on 22.2.2016.
 */
public class Player {

    public final String name;
    public final int score;
    public final int ping;

    public Player(String name, int score, int ping) {
        this.name = name;
        this.score = score;
        this.ping = ping;
    }
}
