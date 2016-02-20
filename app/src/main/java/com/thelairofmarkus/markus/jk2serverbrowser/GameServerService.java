package com.thelairofmarkus.markus.jk2serverbrowser;

import android.util.Log;

import com.thelairofmarkus.markus.jk2serverbrowser.fixtures.MockHelper;
import com.thelairofmarkus.markus.jk2serverbrowser.fixtures.UdpConnectionMockImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by markus on 19.2.2016.
 */
public class GameServerService implements IGameServerService {

    private static final String TAG = "GameServerService";

    @Override
    public Observable<GameServer> getInfo(Observable<Server> servers) {

        return servers
                .buffer(20)
                .flatMap(new Func1<List<Server>, Observable<GameServer>>() {
                    @Override
                    public Observable<GameServer> call(List<Server> servers) {
                        List<GameServer> gameServers = new ArrayList<GameServer>();
                        UdpConnection connection;

                        try {
                            connection = new UdpConnection();
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                            return Observable.empty();
                        }

                        Long currentTime = System.currentTimeMillis();

                        for (Server server : servers) {
                            try {
                                connection.send(server, Messages.GET_INFO);
                            } catch (IOException ioe) {
                                Log.d(TAG, "Couldn't send to server " + server.ipAddress + ":" + server.port);
                            }
                        }

                        for (Server notUsed : servers) {
                            try {
                                ServerResponse response = connection.receive();

                                GameServer gameServer = new GameServer(
                                        response.getMetaData("ip"),
                                        Integer.parseInt(response.getMetaData("port")),
                                        (int) (System.currentTimeMillis() - currentTime),
                                        response.getValue("hostname"),
                                        Integer.parseInt(response.getValue("clients")));

                                gameServers.add(gameServer);
                            } catch (IOException ioe) {
                                Log.d(TAG, "Failed to receive server info");
                            }
                        }

                        connection.close();

                        return Observable.from(gameServers);
                    }
                });
    }

    @Override
    public Observable<GameServerStatus> getStatus(Server server) {
        throw new UnsupportedOperationException("Get server status not implemented.");
    }
}
