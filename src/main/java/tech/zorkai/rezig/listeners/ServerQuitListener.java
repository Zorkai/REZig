package tech.zorkai.rezig.listeners;

import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;
import tech.zorkai.rezig.REZig;
import tech.zorkai.rezig.utils.DiscordRPC;

public class ServerQuitListener implements Consumer<ServerData> {

    private final REZig main;

    public ServerQuitListener(REZig main) {
        this.main = main;
    }

    @Override
    public void accept(ServerData serverData) {
        if (serverData.getIp().equals("play.mcrevive.net") &&  main.discordRPC.shouldOperate){
            new Thread(() -> {
                main.discordRPC.shouldOperate = false;
                main.discordRPC.clearPresence();
                main.discordRPC.closeClient();
            }).start();
        }
    }

}