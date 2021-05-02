package tech.zorkai.rezig;

import net.labymod.api.EventManager;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;
import tech.zorkai.rezig.listeners.*;
import tech.zorkai.rezig.utils.DiscordRPC;

import java.util.List;

public class REZig extends LabyModAddon {

    public boolean playingRevive = false;
    public DiscordRPC discordRPC = new DiscordRPC();

    @Override
    public void onEnable() {
        EventManager eventManager = this.getApi().getEventManager();
        eventManager.register(new ServerMessageListener(this));
        eventManager.registerOnIncomingPacket(new IncomingPacketListener(this));
        eventManager.registerOnJoin(new ServerJoinListener(this));
        eventManager.registerOnQuit(new ServerQuitListener(this));
    }

    @Override
    public void loadConfig() {
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
    }

}