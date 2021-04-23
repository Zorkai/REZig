package tech.zorkai.rezig.listeners;

import net.labymod.api.events.MessageReceiveEvent;
import tech.zorkai.rezig.REZig;
import tech.zorkai.rezig.utils.DiscordRPC;
import tech.zorkai.rezig.utils.Games;

public class ServerMessageListener implements MessageReceiveEvent {

    private final REZig main;

    public ServerMessageListener(REZig main) {
        this.main = main;
    }

    @Override
    public boolean onReceive(String message, String unformattedMessage) {
        if (main.playingRevive && unformattedMessage.contains("You are in server ")){
            String gameId = unformattedMessage.substring(unformattedMessage.lastIndexOf(" ")+1);
            String game = gameId.replaceAll("[0-9]","");
            DiscordRPC.updatePresence(Games.getGameName(game), Games.getGameIcon(game));
        }

        return false;
    }

}