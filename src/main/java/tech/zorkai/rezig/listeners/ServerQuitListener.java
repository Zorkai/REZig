package tech.zorkai.rezig.listeners;

import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;
import tech.zorkai.rezig.utils.DiscordRPC;

public class ServerQuitListener implements Consumer<ServerData> {

    @Override
    public void accept(ServerData serverData) {
        if (serverData.getIp().equals("play.mcrevive.net") && DiscordRPC.shouldOperate){
            new Thread(() -> {
                DiscordRPC.shouldOperate = false;
                DiscordRPC.clearPresence();
                DiscordRPC.closeClient();
            }).start();
        }
    }

}