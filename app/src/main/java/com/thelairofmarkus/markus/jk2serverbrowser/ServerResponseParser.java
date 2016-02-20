package com.thelairofmarkus.markus.jk2serverbrowser;

/**
 * Created by markus on 20.2.2016.
 */
public interface ServerResponseParser {

    ServerResponse parse(byte[] msg);
}
