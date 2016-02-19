package com.thelairofmarkus.markus.jk2serverbrowser;

/**
 * Created by markus on 17.2.2016.
 */
public class Server {

    public final String ipAddress;
    public final int port;

    public Server(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    @Override
    public String toString() {
        return String.format("%s:%d",ipAddress,port);
    }
}
