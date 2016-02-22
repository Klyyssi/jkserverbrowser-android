package com.thelairofmarkus.markus.jk2serverbrowser.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thelairofmarkus.markus.jk2serverbrowser.R;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.Player;

import java.util.List;

/**
 * Created by markus on 22.2.2016.
 */
public class PlayerAdapter extends ArrayAdapter<Player> {

    private final Context context;

    public PlayerAdapter(Context context, List<Player> objects) {
        super(context, R.layout.player_row_layout, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.player_row_layout, parent, false);

        TextView name = (TextView) rowView.findViewById(R.id.playerName);
        TextView score = (TextView) rowView.findViewById(R.id.playerScore);
        TextView ping = (TextView) rowView.findViewById(R.id.playerPing);

        Player player = getItem(position);

        name.setText(player.name);
        score.setText(Integer.toString(player.score));
        ping.setText(Integer.toString(player.ping));

        return rowView;
    }
}
