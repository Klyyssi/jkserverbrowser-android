package com.thelairofmarkus.markus.jk2serverbrowser;

import rx.Observable;

/**
 * Created by markus on 17.2.2016.
 */
public interface IMasterServerService {

    Observable<Server> getServers(MasterServer masterServer);
}
