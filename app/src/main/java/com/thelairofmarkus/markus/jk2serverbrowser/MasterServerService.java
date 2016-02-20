package com.thelairofmarkus.markus.jk2serverbrowser;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by markus on 20.2.2016.
 */
public class MasterServerService implements IMasterServerService {

    @Override
    public Observable<Server> getServers(final MasterServer masterServer) {

        return Observable.create(new Observable.OnSubscribe<Server>() {
            @Override
            public void call(Subscriber<? super Server> subscriber) {
                try {
                    IUdpConnection connection = new UdpConnection();
                    connection.send((Server) masterServer, Messages.GET_SERVERS_16);
                    ServerResponse response = connection.receive();

                    for (Map.Entry<String, String> serverEntry : response.getKeyValPairs().entrySet()) {
                        subscriber.onNext(new Server(serverEntry.getKey(), Integer.parseInt(serverEntry.getValue())));
                    }

                    connection.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    subscriber.onError(new ConnectException("Couldn't connect to masterserver."));
                }
            }
        });
    }
}
