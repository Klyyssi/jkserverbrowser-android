package com.thelairofmarkus.markus.jk2serverbrowser;

import java.io.IOException;

/**
 * Created by markus on 19.2.2016.
 */
public interface IUdpConnection {

    void send(Server server, byte[] message) throws IOException;

    ServerResponse receive() throws IOException;

    void close();
}
