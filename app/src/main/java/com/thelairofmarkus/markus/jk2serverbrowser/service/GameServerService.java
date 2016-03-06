package com.thelairofmarkus.markus.jk2serverbrowser.service;

import android.util.Log;

import com.thelairofmarkus.markus.jk2serverbrowser.domain.GameServer;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.GameServerStatus;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.Player;
import com.thelairofmarkus.markus.jk2serverbrowser.parser.GetStatusParser;
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
import rx.Subscriber;
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

                        Long timeBeforeSending = System.currentTimeMillis();

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
                                        (int) (System.currentTimeMillis() - timeBeforeSending),
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
    public Observable<GameServerStatus> getStatus(final GameServer server) {

        return Observable.create(new Observable.OnSubscribe<GameServerStatus>() {
            @Override
            public void call(Subscriber<? super GameServerStatus> subscriber) {
                try {
                    UdpConnection connection = new UdpConnection();
                    Long timeBeforeSending = System.currentTimeMillis();
                    connection.send((Server) server, Messages.GET_STATUS);
                    ServerResponse response = connection.receive();
                    int ping = (int) (System.currentTimeMillis() - timeBeforeSending);

                    List<Player> players = getPlayersFromResponse(response);

                    GameServerStatus status = new GameServerStatus(
                            server.ipAddress,
                            server.port,
                            ping,
                            server.serverName,
                            players.size(),
                            players,
                            response.getValue("mapname").get(0),
                            response.getValue("gamename").get(0));

                    subscriber.onNext(status);
                    subscriber.onCompleted();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    subscriber.onError(new ConnectException("Unable to connect to server."));
                }
            }
        });
    }

    private List<Player> getPlayersFromResponse(ServerResponse response) {
        List<Player> players = new ArrayList<>();
        for (String playerAsString : response.getValue(GetStatusParser.KEY_PLAYER)) {
            players.add(GetStatusParser.parse(playerAsString));
        }
        return players;
    }
}
