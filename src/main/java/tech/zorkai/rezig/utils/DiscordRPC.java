package tech.zorkai.rezig.utils;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

import java.time.OffsetDateTime;

public class DiscordRPC {

    public static boolean shouldOperate = true;
    public static IPCClient rpcClient;

    public static void init() {
        IPCClient client = new IPCClient(833015588720345130L);
        client.setListener(new IPCListener() {
            @Override
            public void onReady(IPCClient client) {
                rpcClient = client;
            }
        });
        try {
            client.connect();
        } catch (NoDiscordClientException e) {
            e.printStackTrace();
        }
    }

    public static void updatePresence(String game, String icon) {
        if (!shouldOperate) {
            clearPresence();
            closeClient();
        }
        new Thread(() -> {
            try {
                RichPresence.Builder builder = new RichPresence.Builder();
                builder.setState("Playing in " + game)
                        .setDetails("play.mcrevive.net")
                        .setStartTimestamp(OffsetDateTime.now())
                        .setLargeImage("512", "Vibin' on revive")
                        .setSmallImage(icon, game);
                rpcClient.sendRichPresence(builder.build());
            } catch (Throwable e) {
                shouldOperate = false;
            }
        }).start();
    }

    public static void clearPresence() {
        if (rpcClient != null) rpcClient.sendRichPresence(null);
    }


    public static void closeClient() {
        if (rpcClient != null) rpcClient.close();
    }

}