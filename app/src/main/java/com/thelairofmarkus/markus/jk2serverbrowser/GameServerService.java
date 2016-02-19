package com.thelairofmarkus.markus.jk2serverbrowser;

import android.util.Log;

import com.thelairofmarkus.markus.jk2serverbrowser.fixtures.MockHelper;
import com.thelairofmarkus.markus.jk2serverbrowser.fixtures.UdpConnectionMockImpl;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by markus on 19.2.2016.
 */
public class GameServerService implements IGameServerService {

    private IUdpConnection connection = new UdpConnectionMockImpl();

    @Override
    public Observable<GameServer> getInfo(Observable<Server> servers) {

        return servers.buffer(20).flatMap(new Func1<List<Server>, Observable<GameServer>>() {
            @Override
            public Observable<GameServer> call(List<Server> servers) {
                //send to servers
                List<GameServer> gameServers = new ArrayList<GameServer>();

                for (Server server : servers) {
                    connection.send(server, Messages.GET_INFO);
                }

                try {
                    Thread.sleep(MockHelper.getRandom(1000));
                } catch (InterruptedException ie) {
                    Log.d("ServerServiceMock", "Thread sleep exception");
                }

                for (Server server : servers) {
                    ServerResponse response = connection.receive();
                    GameServer gameServer = new GameServer(
                            response.getValue("ip"),
                            Integer.parseInt(response.getValue("port")),
                            Integer.parseInt(response.getValue("ping")),
                            response.getValue("hostname"),
                            Integer.parseInt(response.getValue("clients")));

                    gameServers.add(gameServer);
                }

                return Observable.from(gameServers);
                //return Observable.from(servers).map(new Func1<Server, GameServer>() {
                //    @Override
                //    public GameServer call(Server server) {
                //        return new GameServer(server.ipAddress, server.port, MockHelper.getRandom(200), serverNames[MockHelper.getRandom(serverNames.length)], MockHelper.getRandom(10));
                //    }
                //});
            }
        });
    }

    @Override
    public Observable<GameServerStatus> getStatus(Server server) {
        throw new UnsupportedOperationException("Get server status not implemented.");
    }
}
