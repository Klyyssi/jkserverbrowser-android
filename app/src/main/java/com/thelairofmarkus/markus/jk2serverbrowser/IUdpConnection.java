package com.thelairofmarkus.markus.jk2serverbrowser;

/**
 * Created by markus on 19.2.2016.
 */
public interface IUdpConnection {

    void send(Server server, byte[] message);

    ServerResponse receive();
}
