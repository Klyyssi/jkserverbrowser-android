package com.thelairofmarkus.markus.jk2serverbrowser;

import com.thelairofmarkus.markus.jk2serverbrowser.fixtures.MasterServerServiceMockImpl;

import rx.Observable;

/**
 * Created by markus on 19.2.2016.
 */
public class ServerService implements IServerService {

    private IGameServerService gameServerService = new GameServerService();
    private IMasterServerService masterServerService = new MasterServerService();

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
