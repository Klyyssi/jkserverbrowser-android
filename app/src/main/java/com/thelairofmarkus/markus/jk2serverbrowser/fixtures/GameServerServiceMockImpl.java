package com.thelairofmarkus.markus.jk2serverbrowser.fixtures;

import android.util.Log;

import com.thelairofmarkus.markus.jk2serverbrowser.domain.GameServer;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.GameServerStatus;
import com.thelairofmarkus.markus.jk2serverbrowser.service.IGameServerService;
import com.thelairofmarkus.markus.jk2serverbrowser.udp.IUdpConnection;
import com.thelairofmarkus.markus.jk2serverbrowser.udp.Messages;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.Server;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.ServerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by markus on 18.2.2016.
 */
public class GameServerServiceMockImpl implements IGameServerService {

    private String[] serverNames = {
            "The force awakens US",
            "Army of the Jedi",
            "Help us obi1",
            "I love Jan",
            "Darth Maul haters",
            "Crazy instagib",
            "Grenny's home"
    };

    private IUdpConnection connection = new UdpConnectionMockImpl();

    @Override
    public Observable<GameServer> getInfo(Observable<Server> servers) {

        return servers.buffer(20).flatMap(new Func1<List<Server>, Observable<GameServer>>() {
            @Override
            public Observable<GameServer> call(List<Server> servers) {
                //send to servers
                List<GameServer> gameServers = new ArrayList<GameServer>();

                for (Server server : servers) {
                    try {
                        connection.send(server, Messages.GET_INFO);
                    } catch (IOException e) {

                    }
                }

                try {
                    Thread.sleep(MockHelper.getRandom(1000));
                } catch (InterruptedException ie) {
                    Log.d("ServerServiceMock", "Thread sleep exception");
                }

                for (Server server : servers) {
                    try {
                        ServerResponse response = connection.receive();
                        GameServer gameServer = new GameServer(
                                response.getValue("ip").get(0),
                                Integer.parseInt(response.getValue("port").get(0)),
                                Integer.parseInt(response.getValue("ping").get(0)),
                                response.getValue("hostname").get(0),
                                Integer.parseInt(response.getValue("clients").get(0)));

                        gameServers.add(gameServer);
                    } catch (IOException ie ) {

                    }
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
    public Observable<GameServerStatus> getStatus(GameServer server) {
        throw new UnsupportedOperationException("Get server status not implemented.");
    }
}
