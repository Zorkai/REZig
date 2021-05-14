package tech.zorkai.rezig.listeners;

import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;
import tech.zorkai.rezig.REZig;

public class ServerQuit implements Consumer<ServerData> {

    private final REZig main;

    public ServerQuit(REZig main) {
        this.main = main;
    }

    @Override
    public void accept(ServerData serverData) {
        if (serverData.getIp().equals("play.mcrevive.net") &&  main.discordRPC.shouldOperate){
            main.staffChat = false;

            new Thread(() -> {
                main.discordRPC.shouldOperate = false;
                main.discordRPC.clearPresence();
                main.discordRPC.closeClient();
            }).start();
        }
    }

}