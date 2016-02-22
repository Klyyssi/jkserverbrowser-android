package com.thelairofmarkus.markus.jk2serverbrowser.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.thelairofmarkus.markus.jk2serverbrowser.R;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.GameServer;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.GameServerStatus;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.Player;
import com.thelairofmarkus.markus.jk2serverbrowser.fixtures.ServerServiceMockImpl;
import com.thelairofmarkus.markus.jk2serverbrowser.service.IServerService;
import com.thelairofmarkus.markus.jk2serverbrowser.service.ServerService;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServerStatusActivity extends AppCompatActivity {

    private GameServer gameServer;
    private final IServerService serverService = new ServerService();

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

    public void refreshClicked(View view) {
        updateServerStatus();
    }

    private void updateServerStatus() {
        final Context context = this;
        serverService.getServerStatus(gameServer)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GameServerStatus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        View view = findViewById(R.id.serverStatusLayout);
                        Snackbar.make(view, "Oops. Something went wrong!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null)
                                .show();
                    }

                    @Override
                    public void onNext(GameServerStatus gameServerStatus) {
                        TextView txtMap = (TextView) findViewById(R.id.serverStatusMapName);
                        TextView txtMod = (TextView) findViewById(R.id.serverStatusMod);
                        ListView playerList = (ListView) findViewById(R.id.serverStatusPlayers);
                        PlayerAdapter adapter = new PlayerAdapter(context, gameServerStatus.players);

                        txtMap.setText(gameServerStatus.map);
                        txtMod.setText(gameServerStatus.mod);
                        playerList.setAdapter(adapter);
                    }
                });
    }
}
