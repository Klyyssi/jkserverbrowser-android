package com.thelairofmarkus.markus.jk2serverbrowser.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by markus on 19.2.2016.
 */
public class ServerResponse {

    private final ResponseType responseType;
    private final List<Tuple<String, String>> keyValPairs = new ArrayList<>();
    private final Map<String, String> metaData = new HashMap<>();

    public ServerResponse(ResponseType responseType) {
        this.responseType = responseType;
    }

    public List<String> getValue(String key) {
        List<String> list = new ArrayList<>();
        for (Tuple<String, String> tuple : keyValPairs) {
            if (tuple.x.equals(key)) {
                list.add(tuple.y);
            }
        }

        return list;
    }

    public void updateFirst(String key, String valueToUpdate) {
        Tuple<String, String> toUpdate = null;

        for (Tuple<String, String> tuple : keyValPairs) {
            if (tuple.x.equals(key)) {
                toUpdate = tuple;
                break;
            }
        }

        if (toUpdate != null) {
            keyValPairs.remove(toUpdate);
            keyValPairs.add(new Tuple<>(key, valueToUpdate));
        }
    }

    public void addMetaData(String key, String value) {
        metaData.put(key, value);
    }

    public String getMetaData(String key) {
        return metaData.get(key);
    }

    public void addKeyValPair(String key, String value) {
        keyValPairs.add(new Tuple<>(key, value));
    }
    
    public List<Tuple<String, String>> getKeyValPairs() {
        return keyValPairs;
    }
}
