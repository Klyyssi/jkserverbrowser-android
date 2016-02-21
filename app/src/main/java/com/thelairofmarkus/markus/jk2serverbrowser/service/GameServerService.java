package com.thelairofmarkus.markus.jk2serverbrowser.service;

import android.util.Log;

import com.thelairofmarkus.markus.jk2serverbrowser.domain.GameServer;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.GameServerStatus;
import com.thelairofmarkus.markus.jk2serverbrowser.udp.Messages;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.Server;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.ServerResponse;
import com.thelairofmarkus.markus.jk2serverbrowser.udp.UdpConnection;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
                .buffer(500L, TimeUnit.MILLISECONDS, 20)
                .flatMap(new Func1<List<Server>, Observable<GameServer>>() {
                    @Override
                    public Observable<GameServer> call(List<Server> servers) {
                        List<GameServer> gameServers = new ArrayList<GameServer>();
                        UdpConnection connection;

                        try {
                            connection = new UdpConnection();
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                            return Observable.error(new ConnectException("Couldn't open socket"));
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
                                        response.getValue("hostname").get(0),
                                        Integer.parseInt(response.getValue("clients").get(0)));

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
