package com.thelairofmarkus.markus.jk2serverbrowser.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thelairofmarkus.markus.jk2serverbrowser.R;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.GameServer;

import java.util.Comparator;
import java.util.List;

/**
 * Created by markus on 18.2.2016.
 */
public class ServerAdapter extends ArrayAdapter<GameServer> {

    private final Context context;

    public ServerAdapter(Context context, List<GameServer> objects) {
        super(context, R.layout.row_layout, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);

        TextView name = (TextView) rowView.findViewById(R.id.serverName);
        TextView players = (TextView) rowView.findViewById(R.id.players);
        TextView ping = (TextView) rowView.findViewById(R.id.ping);

        GameServer server = getItem(position);

        name.setText(server.serverName);
        players.setText(Integer.toString(server.players));
        ping.setText(Integer.toString(server.ping));

        return rowView;
    }
}
