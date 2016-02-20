package com.thelairofmarkus.markus.jk2serverbrowser.parser;

import com.thelairofmarkus.markus.jk2serverbrowser.domain.ServerResponse;

/**
 * Created by markus on 20.2.2016.
 */
public interface ServerResponseParser {

    ServerResponse parse(byte[] msg);
}
