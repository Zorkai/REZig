package tech.zorkai.rezig.listeners;

import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;
import net.minecraft.client.Minecraft;
import tech.zorkai.rezig.REZig;

public class ServerJoin implements Consumer<ServerData> {

    private final REZig main;

    public ServerJoin(REZig main) {
        this.main = main;
    }

    @Override
    public void accept(ServerData serverData) {
        boolean joinedRevive = serverData.getIp().equals("play.mcrevive.net");
        main.playingRevive = joinedRevive;
        if (joinedRevive) {
            main.discordRPC.shouldOperate = true;
            main.discordRPC.init();
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/whereami");
        }
    }
    
}