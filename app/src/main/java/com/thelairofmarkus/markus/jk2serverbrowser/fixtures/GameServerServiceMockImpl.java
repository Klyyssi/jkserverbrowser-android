package com.thelairofmarkus.markus.jk2serverbrowser.fixtures;

import android.util.Log;

import com.thelairofmarkus.markus.jk2serverbrowser.GameServer;
import com.thelairofmarkus.markus.jk2serverbrowser.GameServerStatus;
import com.thelairofmarkus.markus.jk2serverbrowser.IGameServerService;
import com.thelairofmarkus.markus.jk2serverbrowser.Server;

import rx.Observable;

/**
 * Created by markus on 18.2.2016.
 */
public class GameServerServiceMockImpl implements IGameServerService {

    String[] serverNames = {
            "The force awakens US",
            "Army of the Jedi",
            "Help us obi1",
            "I love Jan",
            "Darth Maul haters",
            "Crazy instagib",
            "Grenny's home"
    };

    @Override
    public Observable<GameServer> getInfo(Server server) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            Log.d("GameServiceMock", "Couldnt sleep");
        }
        return Observable.just(new GameServer(
                server.ipAddress,
                server.port,
                (int) (Math.random()*200), //ping
                serverNames[(int) (Math.random() * serverNames.length)], //server name
                (int) (Math.random() * 10) //players
        ));
    }

    @Override
    public Observable<GameServerStatus> getStatus(Server server) {
        throw new UnsupportedOperationException("Get server status not implemented.");
    }
}
