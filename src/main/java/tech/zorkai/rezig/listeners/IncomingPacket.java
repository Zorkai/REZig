package tech.zorkai.rezig.listeners;

import net.labymod.utils.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import tech.zorkai.rezig.REZig;

public class IncomingPacket implements Consumer<Object> {

    private final REZig main;
    private final Minecraft mc = Minecraft.getMinecraft();

    public IncomingPacket(REZig main) {
        this.main = main;
    }

    @Override
    public void accept(Object rawPacket) {
        if (rawPacket instanceof S3FPacketCustomPayload && main.playingRevive){

            if (((S3FPacketCustomPayload) rawPacket).getChannelName().equals("MC|Brand")){
                try {
                    mc.thePlayer.sendChatMessage("/whereami");
                } catch (Exception ignored) { }
            }
        }

    }
}
