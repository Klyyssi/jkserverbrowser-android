package com.thelairofmarkus.markus.jk2serverbrowser.fixtures;

import com.thelairofmarkus.markus.jk2serverbrowser.GameServer;
import com.thelairofmarkus.markus.jk2serverbrowser.IMasterServerService;
import com.thelairofmarkus.markus.jk2serverbrowser.MasterServer;
import com.thelairofmarkus.markus.jk2serverbrowser.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;

/**
 * Created by markus on 17.2.2016.
 */
public class MasterServerServiceMockImpl implements IMasterServerService {
    @Override
    public Observable<Server> getServers(MasterServer masterServer) {

        return Observable.just(
                new Server("123.123.123.123", 5050),
                new Server("123.255.2.31", 1337),
                new Server("123.1.2.3", 1234)
        );
    }
}
