package com.thelairofmarkus.markus.jk2serverbrowser;

import rx.Observable;

/**
 * Created by markus on 18.2.2016.
 */
public interface IGameServerService {

    Observable<GameServer> getInfo(Server server);

    Observable<GameServerStatus> getStatus(Server server);
}
