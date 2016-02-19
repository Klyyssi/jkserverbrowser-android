package com.thelairofmarkus.markus.jk2serverbrowser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by markus on 19.2.2016.
 */
public class ServerResponse {

    private final ResponseType responseType;
    private final Map<String, String> keyValPairs = new HashMap<>();

    public ServerResponse(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getValue(String key) {
        return keyValPairs.get(key);
    }

    public void addKeyValPair(String key, String value) {
        keyValPairs.put(key, value);
    }
    
    public Map<String, String> getKeyValPairs() {
        return keyValPairs;
    }
}
