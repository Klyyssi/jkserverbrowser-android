package com.thelairofmarkus.markus.jk2serverbrowser.fixtures;

import com.thelairofmarkus.markus.jk2serverbrowser.udp.IUdpConnection;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.ResponseType;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.Server;
import com.thelairofmarkus.markus.jk2serverbrowser.domain.ServerResponse;

import java.io.IOException;

/**
 * Created by markus on 19.2.2016.
 */
public class UdpConnectionMockImpl implements IUdpConnection {

    private String[] servers = {

        "����infoResponse"
        +"\\mvhttp\\18201\\sv_allowAnonymous\\0\\game\\saber\\maxPing\\999\\fdisable\\163837\\wdisable\\65531\\truejedi\\0\\needpass\\0\\gametype\\0\\sv_maxclients\\30\\clients\\0\\mapname\\ffa_CloudShark\\hostname\\ ^1#1 ^7Polish League\\protocol\\16\\challenge\\xxx",

        "����infoResponse"
        +"\\sv_allowAnonymous\\0\\game\\league\\fdisable\\163837\\wdisable\\65531\\truejedi\\0\\needpass\\0\\gametype\\5\\sv_maxclients\\20\\clients\\0\\mapname\\ffa_bespin\\hostname\\^7zedi^2.^7TFFA^2'\\protocol\\16\\challenge\\xxx",

            "����infoResponse"
        +"\\mvhttp\\28071\\sv_allowAnonymous\\0\\game\\Twimod_JumpServer\\fdisable\\163837\\wdisable\\65531\\truejedi\\0\\needpass\\0\\gametype\\0\\sv_maxclients\\12\\clients\\1\\mapname\\ffa_bespin\\hostname\\^2^3[^7DARK^3]J^7umping^3S^7erver\\protocol\\16\\challenge\\xxx",

        "����infoResponse"
        +"\\sv_allowAnonymous\\0\\game\\academy\\fdisable\\163837\\wdisable\\65531\\truejedi\\0\\needpass\\0\\gametype\\7\\sv_maxclients\\18\\clients\\0\\mapname\\ctf_imperial\\hostname\\^0.=)^1L^3o^1D^0(=.^7InstaCTF\\protocol\\16\\challenge\\xxx"

    };
    @Override
    public void send(Server server, byte[] message) throws IOException {
        // doesnt need to be implemented
    }

    @Override
    public ServerResponse receive() throws IOException {
        ServerResponse response = new ServerResponse(ResponseType.INFO_RESPONSE);
        String[] pieces = servers[MockHelper.getRandom(servers.length)].replaceAll("\\^[0-9]", "").split("\\\\");
        for (int i = 1; i < pieces.length-1; i += 2) {
            response.addKeyValPair(pieces[i], pieces[i+1]);
        }
        response.addKeyValPair("port", "1337");
        response.addKeyValPair("ip", "123.123.123.123");
        response.addKeyValPair("ping", Integer.toString(MockHelper.getRandom(200)));
        return response;
    }

    @Override
    public void close() {
        //nothing
    }
}
