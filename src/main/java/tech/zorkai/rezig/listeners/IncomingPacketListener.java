package tech.zorkai.rezig.listeners;

import net.labymod.utils.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import tech.zorkai.rezig.REZig;

public class IncomingPacketListener implements Consumer<Object> {

    private final REZig main;

    public IncomingPacketListener(REZig main) {
        this.main = main;
    }

    @Override
    public void accept(Object rawPacket) {
        if (rawPacket instanceof S3FPacketCustomPayload && main.playingRevive){
            if (((S3FPacketCustomPayload) rawPacket).getChannelName().equals("MC|Brand")){
                try {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/whereami");
                } catch (Exception ignored) {
                }
            }
        }
    }
}
