package com.thelairofmarkus.markus.jk2serverbrowser.udp;

import com.thelairofmarkus.markus.jk2serverbrowser.domain.Server;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.ServerResponse;

import java.io.IOException;

/**
 * Created by markus on 19.2.2016.
 */
public interface IUdpConnection {

    void send(Server server, byte[] message) throws IOException;

    ServerResponse receive() throws IOException;

    void close();
}
