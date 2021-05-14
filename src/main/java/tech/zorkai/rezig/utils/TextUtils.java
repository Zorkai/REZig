package tech.zorkai.rezig.utils;

import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class TextUtils {

    private final Minecraft mc = Minecraft.getMinecraft();

    public String removeChars(String input){
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < input.length(); i++) {
            String character = Character.toString(input.charAt(i));
            if (!character.equals("\u00A7")){
                result.append(character);
            } else {
                i++;
            }
        }

        return result.toString();
    }

    public void send(String text){
        LabyMod.getInstance().displayMessageInChat(text);
    }

    public void send(IChatComponent component){
        mc.thePlayer.addChatMessage(component);
    }

    public IChatComponent createEventMessage(String text, String value, ClickEvent.Action action){
        IChatComponent component = new ChatComponentText(text);
        component.getChatStyle().setChatClickEvent(new ClickEvent(action, value));
        return component;
    }

}
