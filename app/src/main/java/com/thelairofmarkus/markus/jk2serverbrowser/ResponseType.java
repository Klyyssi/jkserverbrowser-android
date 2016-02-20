package com.thelairofmarkus.markus.jk2serverbrowser;

/**
 * Created by markus on 19.2.2016.
 */
public enum ResponseType {
    INFO_RESPONSE("infoResponse"),
    GETSERVERS_RESPONSE("getserversResponse");

    public final String startsWith;

    private ResponseType(String startsWith) {
        this.startsWith = startsWith;
    }
}
