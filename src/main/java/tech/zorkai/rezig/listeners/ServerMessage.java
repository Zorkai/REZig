package tech.zorkai.rezig.listeners;

import net.labymod.api.events.MessageReceiveEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import tech.zorkai.rezig.REZig;
import tech.zorkai.rezig.utils.Games;
import tech.zorkai.rezig.utils.TextUtils;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.event.ClickEvent.Action.SUGGEST_COMMAND;

public class ServerMessage implements MessageReceiveEvent {

    private final REZig main;
    private boolean waitingStats = false;
    private boolean hideStats = false;
    private boolean waitingReBroadcast = false;
    private Map<String, Integer> stats = new HashMap<>();
    private final Games games = new Games();
    private final TextUtils textUtils = new TextUtils();
    private final Minecraft mc = Minecraft.getMinecraft();

    public ServerMessage(REZig main) {
        this.main = main;
    }

    @Override
    public boolean onReceive(String rawMessage, String message) {
        if (!main.playingRevive) {
            return false;
        }

        String unformattedMessage = textUtils.removeChars(message);

        if (unformattedMessage.contains("You are in server ")) {
            String game = unformattedMessage
                    .substring(unformattedMessage.lastIndexOf(" ") + 1)
                    .replaceAll("[0-9]", "");
            main.currentGame = game;

            if (!game.equals("LOBBY")){
                main.updateRanks(game);
                hideStats = true;
                waitingStats = true;
                mc.thePlayer.sendChatMessage("/stats");
            }

            main.discordRPC.updatePresence(
                    games.getGameName(game),
                    games.getGameIcon(game));
        }

        else if (unformattedMessage.contains("BROADCAST") && unformattedMessage.contains(" » ") && !waitingReBroadcast){
            waitingReBroadcast = true;
            textUtils.send(rawMessage);
            IChatComponent reBroadcast = textUtils.createEventMessage(
                    EnumChatFormatting.GOLD + "[Re-Broadcast] ",
                    "/fb " + unformattedMessage.split(" » ")[1],
                    SUGGEST_COMMAND);
            IChatComponent replyBroadcast = textUtils.createEventMessage(
                    EnumChatFormatting.YELLOW + "[Reply]",
                    "/msg " + unformattedMessage
                            .split(" \u21C9")[0]
                            .split("\u258E ")[1] + " ",
                    SUGGEST_COMMAND);
            textUtils.send(reBroadcast.appendSibling(replyBroadcast));
            waitingReBroadcast = false;
            return true;
        }

        else if (unformattedMessage.contains("'s stats --------")){
            waitingStats = true;
            stats = new HashMap<>();
            return hideStats;
        }

        else if (waitingStats){
            if (!unformattedMessage.contains("stats.mcrevive.net")){
                String[] values = unformattedMessage.split(": ");
                try {
                    stats.put(values[0]
                                    .toLowerCase()
                                    .replace(" ", "_"),
                            Integer.parseInt(values[values.length - 1]
                                    .replace(" ", "")));
                } catch(Exception ignored) {}
            } else {
                waitingStats = false;

                if (hideStats) {
                    hideStats = false;
                    if (stats.containsKey("points")) {
                        main.points = stats.get("points");
                    }
                } else {
                    games.sendAdvancedRecords(stats);
                }
                return true;
            }
            return hideStats;
        }

        return false;
    }

}