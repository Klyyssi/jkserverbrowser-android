package com.thelairofmarkus.markus.jk2serverbrowser.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by markus on 17.2.2016.
 */
public class GameServer extends Server implements Parcelable {

    public final int ping;
    public final int players;
    public final String serverName;

    public GameServer(String ipAddress, int port, int ping, String serverName, int players) {
        super(ipAddress, port);
        this.ping = ping;
        this.serverName = serverName;
        this.players = players;
    }

    public static final Parcelable.Creator<GameServer> CREATOR = new Parcelable.Creator<GameServer>() {
        @Override
        public GameServer createFromParcel(Parcel source) {
            String[] strArr = source.createStringArray();
            int[] intArr = source.createIntArray();
            return new GameServer(strArr[0], intArr[0], intArr[1], strArr[1], intArr[2]);
        }

        @Override
        public GameServer[] newArray(int size) {
            return new GameServer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeStringArray(new String[] { ipAddress, serverName });
        out.writeIntArray(new int[] {port, ping, players});
    }
}
