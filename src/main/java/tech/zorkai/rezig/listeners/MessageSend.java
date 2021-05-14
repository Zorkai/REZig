package tech.zorkai.rezig.listeners;

import net.labymod.api.events.MessageSendEvent;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import tech.zorkai.rezig.REZig;

public class MessageSend  implements MessageSendEvent {

    private final REZig main;

    public MessageSend(REZig main) {
        this.main = main;
    }

    @Override
    public boolean onSend(String text) {
        String message = text.toLowerCase();

        if (message.startsWith("/sc toggle")) {
            main.staffChat = !main.staffChat;
            LabyMod.getInstance().displayMessageInChat("§aToggled staff chat!");
            return true;
        }

        if (main.staffChat && !message.startsWith("/")){
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/sc " + text);
            return true;
        }

        return false;
    }


}
