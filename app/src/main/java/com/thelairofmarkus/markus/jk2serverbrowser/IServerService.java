package com.thelairofmarkus.markus.jk2serverbrowser;

import rx.Observable;

/**
 * Created by markus on 18.2.2016.
 */
public interface IServerService {

    Observable<GameServer> getServers(MasterServer masterServer);

    Observable<GameServerStatus> getServerStatus(GameServer gameServer);
}
