package com.thelairofmarkus.markus.jk2serverbrowser.fixtures;

import com.thelairofmarkus.markus.jk2serverbrowser.GameServer;
import com.thelairofmarkus.markus.jk2serverbrowser.GameServerStatus;
import com.thelairofmarkus.markus.jk2serverbrowser.IGameServerService;
import com.thelairofmarkus.markus.jk2serverbrowser.IMasterServerService;
import com.thelairofmarkus.markus.jk2serverbrowser.IServerService;
import com.thelairofmarkus.markus.jk2serverbrowser.MasterServer;
import com.thelairofmarkus.markus.jk2serverbrowser.Server;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by markus on 18.2.2016.
 */
public class ServerServiceMockImpl implements IServerService {

    private IGameServerService gameServerService = new GameServerServiceMockImpl();
    private IMasterServerService masterServerService = new MasterServerServiceMockImpl();

    @Override
    public Observable<GameServer> getServers(MasterServer masterServer) {
        return gameServerService
                .getInfo(masterServerService.getServers(masterServer));
    }

    @Override
    public Observable<GameServerStatus> getServerStatus(GameServer gameServer) {
        throw new UnsupportedOperationException("Mock get server status not implemented.");
    }
}
