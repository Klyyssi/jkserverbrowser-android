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
                new Server("185.25.149.174", 28071),
                new Server("62.75.169.194", 1338),
                new Server("87.106.127.115", 28078),
                new Server("185.7.199.7", 28071)
        );
    }
}
