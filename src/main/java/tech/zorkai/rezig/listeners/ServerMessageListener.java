package tech.zorkai.rezig.listeners;

import net.labymod.api.events.MessageReceiveEvent;
import net.labymod.main.LabyMod;
import tech.zorkai.rezig.REZig;
import tech.zorkai.rezig.utils.Games;

import java.util.HashMap;
import java.util.Map;

public class ServerMessageListener implements MessageReceiveEvent {

    private final REZig main;
    private boolean waitingStats = false;
    private Map<String, Integer> stats = new HashMap<>();
    private final Games games = new Games();

    public ServerMessageListener(REZig main) {
        this.main = main;
    }

    private String removeChars(String input){
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < input.length(); i++) {
            String character = Character.toString(input.charAt(i));
            if (!character.equals("§")){
                result.append(character);
            } else {
                i++;
            }
        }

        return result.toString();
    }

    private void send(String input){
        LabyMod.getInstance().displayMessageInChat(input);
    }

    @Override
    public boolean onReceive(String rawmessage, String message) {
        if (main.playingRevive) {
            String unformattedMessage = removeChars(message);
            if (unformattedMessage.contains("You are in server ")) {
                String gameId = unformattedMessage.substring(unformattedMessage.lastIndexOf(" ") + 1);
                String game = gameId.replaceAll("[0-9]", "");
                main.discordRPC.updatePresence(games.getGameName(game), games.getGameIcon(game));
            }

            if (unformattedMessage.contains("'s stats --------")){
                waitingStats = true;
                stats = new HashMap<>();
            }

            if (waitingStats){
                if (!unformattedMessage.contains("stats.mcrevive.net")){
                    String[] values = unformattedMessage.split(": ");
                    try {
                        stats.put(values[0].toLowerCase().replace(" ", "_"),
                                Integer.parseInt(values[values.length - 1].replace(" ", "")));
                    } catch(Exception ignored) {}
                } else {
                    waitingStats = false;

                    if (stats.containsKey("victories") && stats.containsKey("games_played")){
                        int victories = stats.get("victories");
                        int games_played = stats.get("games_played");
                        double ratio = (double) victories / games_played;
                        send("§aWin Rate: §e" + Math.ceil(ratio * 100) + "%");

                        int loses = stats.get("games_played") - victories;
                        ratio = (double) victories / loses;
                        send("§aW/L Ratio: §e" + Math.round(ratio * 100.0) / 100.0);
                    }

                    if (stats.containsKey("kills") && stats.containsKey("deaths")){
                        int kills = stats.get("kills");
                        int deaths = stats.get("deaths");
                        double ratio = (double) kills / deaths;
                        send("§aKDR: §e" + Math.round(ratio * 100.0) / 100.0);
                    }

                    return true;
                }
            }

        }

        return false;
    }

}