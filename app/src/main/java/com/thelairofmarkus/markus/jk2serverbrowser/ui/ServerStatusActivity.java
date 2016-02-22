package com.thelairofmarkus.markus.jk2serverbrowser.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.thelairofmarkus.markus.jk2serverbrowser.R;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.GameServer;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.GameServerStatus;
import com.thelairofmarkus.markus.jk2serverbrowser.fixtures.ServerServiceMockImpl;
import com.thelairofmarkus.markus.jk2serverbrowser.service.IServerService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServerStatusActivity extends AppCompatActivity {

    private GameServer gameServer;
    private final IServerService serverService = new ServerServiceMockImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        GameServer server = (GameServer) intent.getParcelableExtra(MainActivity.EXTRA_SERVER);
        gameServer = server;

        fillGameServerDetails(server);
    }

    private void fillGameServerDetails(GameServer gameServer) {
        TextView txtServerName = (TextView) findViewById(R.id.serverStatusName);
        TextView txtIpPort = (TextView) findViewById(R.id.serverStatusIpAndPort);

        txtServerName.setText(gameServer.serverName);
        txtIpPort.setText(String.format("%s:%d", gameServer.ipAddress, gameServer.port));
    }

    private void updateServerStatus() {


        serverService.getServerStatus(gameServer)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GameServerStatus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GameServerStatus gameServerStatus) {
                        
                    }
                });
    }
}
