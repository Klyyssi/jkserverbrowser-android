package com.thelairofmarkus.markus.jk2serverbrowser;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ListView;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private IServerService serverService = new ServerService();
    private static MasterServer masterServer = new MasterServer("62.113.242.115", 28060);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                .subscribe(new Action1<GameServer>() {
                    @Override
                    public void call(GameServer gameServer) {
                        adapter.add(gameServer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Snackbar.make(serverList, "Oops. Something went wrong!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null)
                                .show();
                    }
                });

    }
}
