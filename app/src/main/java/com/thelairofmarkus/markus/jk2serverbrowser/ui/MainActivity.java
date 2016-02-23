package com.thelairofmarkus.markus.jk2serverbrowser.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.AdapterView;
import android.widget.ListView;

import com.thelairofmarkus.markus.jk2serverbrowser.domain.GameServer;
import com.thelairofmarkus.markus.jk2serverbrowser.fixtures.ServerServiceMockImpl;
import com.thelairofmarkus.markus.jk2serverbrowser.service.IServerService;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.MasterServer;
import com.thelairofmarkus.markus.jk2serverbrowser.R;
import com.thelairofmarkus.markus.jk2serverbrowser.service.ServerService;

import java.util.ArrayList;
import java.util.Comparator;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_SERVER = "com.thelairofmarkus.markus.jk2serverbrowser.ui.EXTRA_SERVER";

    private IServerService serverService = new ServerService();
    private static MasterServer masterServer = new MasterServer("master.ouned.de", 28060);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView serverList = (ListView) findViewById(R.id.serverlist);
        final Context context = this;

        // serverlist item click handler
        serverList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GameServer server = (GameServer) parent.getItemAtPosition(position);
                Intent intent = new Intent(context, ServerStatusActivity.class);
                intent.putExtra(EXTRA_SERVER, server);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshClicked(View view) {
        refreshServers();
    }

    private void refreshServers() {
        final ListView serverList = (ListView) findViewById(R.id.serverlist);
        final ServerAdapter adapter = new ServerAdapter(this, new ArrayList<GameServer>());
        serverList.setAdapter(adapter);

        serverService.getServers(masterServer)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GameServer>() {
                    @Override
                    public void onCompleted() {
                        adapter.sort(new Comparator<GameServer>() {
                            @Override
                            public int compare(GameServer lhs, GameServer rhs) {
                                return rhs.players - lhs.players;
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(serverList, "Oops. Something went wrong!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null)
                                .show();
                    }

                    @Override
                    public void onNext(GameServer gameServer) {
                        adapter.add(gameServer);
                    }
                });

    }
}
