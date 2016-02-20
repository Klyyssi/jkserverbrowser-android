package com.thelairofmarkus.markus.jk2serverbrowser.service;

import com.thelairofmarkus.markus.jk2serverbrowser.domain.MasterServer;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.Server;

import rx.Observable;

/**
 * Created by markus on 17.2.2016.
 */
public interface IMasterServerService {

    Observable<Server> getServers(MasterServer masterServer);
}
