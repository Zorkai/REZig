package tech.zorkai.rezig.utils;

import org.json.JSONObject;
import org.json.JSONTokener;
import tech.zorkai.rezig.REZig;

import java.util.Map;

public class Games {

    private final JSONObject names = new JSONObject(
            new JSONTokener(
                    REZig.class.getResourceAsStream("/games/names.json")
            ));

    private final JSONObject icons = new JSONObject(
            new JSONTokener(
                    REZig.class.getResourceAsStream("/games/icons.json")
            ));

    private final TextUtils textUtils = new TextUtils();

    public String getGameName(String game){
        return names.getString(game);
    }

    public String getGameIcon(String game){
        return icons.getString(game);
    }

    private String getWinRate(int victories, int games_played){
        double rate = (double) victories / games_played;
        return (Math.round(rate * 10000.0) / 100.0) + "%";
    }

    private double getWinLoseRatio(int victories, int loses){
        double ratio = (double) victories / loses;
        return Math.round(ratio * 100.0) / 100.0;
    }

    private double getKillDeathRatio(int kills, int deaths){
        double ratio = (double) kills / deaths;
        return Math.round(ratio * 100.0) / 100.0;
    }

    public void sendAdvancedRecords(Map<String, Integer> stats){
        if (stats.containsKey("victories") && stats.containsKey("games_played")){
            int victories = stats.get("victories");
            int games_played = stats.get("games_played");

            textUtils.send("§aWin Rate: §e" + getWinRate(victories, games_played));

            int loses = stats.get("games_played") - victories;
            textUtils.send("§aW/L Ratio: §e" + getWinLoseRatio(victories, loses));
        }

        if (stats.containsKey("kills") && stats.containsKey("deaths")){
            int kills = stats.get("kills");
            int deaths = stats.get("deaths");
            textUtils.send("§aK/D Ratio: §e" + getKillDeathRatio(kills, deaths));
        }
    }

    public int getPointsToNextRank(String game){
        return 0;
    }

}
