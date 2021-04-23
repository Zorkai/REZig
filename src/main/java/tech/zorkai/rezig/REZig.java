package tech.zorkai.rezig;

import net.labymod.api.EventManager;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;
import tech.zorkai.rezig.listeners.IncomingPacketListener;
import tech.zorkai.rezig.listeners.ServerJoinListener;
import tech.zorkai.rezig.listeners.ServerMessageListener;
import tech.zorkai.rezig.listeners.ServerQuitListener;

import java.util.List;

public class REZig extends LabyModAddon {

    public boolean playingRevive = false;

    @Override
    public void onEnable() {
        EventManager eventManager = this.getApi().getEventManager();
        eventManager.register(new ServerMessageListener(this));
        eventManager.registerOnIncomingPacket(new IncomingPacketListener(this));
        eventManager.registerOnJoin(new ServerJoinListener(this));
        eventManager.registerOnQuit(new ServerQuitListener());
    }

    @Override
    public void loadConfig() {
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
    }

}