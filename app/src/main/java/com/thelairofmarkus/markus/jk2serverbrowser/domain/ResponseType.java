package com.thelairofmarkus.markus.jk2serverbrowser.domain;

/**
 * Created by markus on 19.2.2016.
 */
public enum ResponseType {
    INFO_RESPONSE("infoResponse"),
    GETSERVERS_RESPONSE("getserversResponse"),
    GETSTATUS_RESPONSE("statusResponse");

    public final String startsWith;

    private ResponseType(String startsWith) {
        this.startsWith = startsWith;
    }
}
