package tech.zorkai.rezig.utils;

import org.json.JSONObject;
import org.json.JSONTokener;
import tech.zorkai.rezig.REZig;

import java.io.InputStream;

public class Games {


    static InputStream gameNamesStream = REZig.class.getResourceAsStream("/gameNames.json");
    static JSONObject gameNamesObject = new JSONObject(new JSONTokener(gameNamesStream));

    static InputStream gameIconsStream = REZig.class.getResourceAsStream("/gameIcons.json");
    static JSONObject gameIconsObject = new JSONObject(new JSONTokener(gameIconsStream));

    public static String getGameName(String game){
        return gameNamesObject.getString(game);
    }

    public static String getGameIcon(String game){
        return gameIconsObject.getString(game);
    }

}
