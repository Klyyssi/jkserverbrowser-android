package com.thelairofmarkus.markus.jk2serverbrowser.fixtures;

import com.thelairofmarkus.markus.jk2serverbrowser.domain.GameServer;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.GameServerStatus;
import com.thelairofmarkus.markus.jk2serverbrowser.service.IGameServerService;
import com.thelairofmarkus.markus.jk2serverbrowser.service.IMasterServerService;
import com.thelairofmarkus.markus.jk2serverbrowser.service.IServerService;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.MasterServer;

import rx.Observable;

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
