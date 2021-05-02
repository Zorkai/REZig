package tech.zorkai.rezig.utils;

import org.json.JSONObject;
import org.json.JSONTokener;
import tech.zorkai.rezig.REZig;

import java.io.InputStream;

public class Games {


    InputStream gameNamesStream = REZig.class.getResourceAsStream("/gameNames.json");
    JSONObject gameNamesObject = new JSONObject(new JSONTokener(gameNamesStream));

    InputStream gameIconsStream = REZig.class.getResourceAsStream("/gameIcons.json");
    JSONObject gameIconsObject = new JSONObject(new JSONTokener(gameIconsStream));

    public String getGameName(String game){
        return gameNamesObject.getString(game);
    }

    public String getGameIcon(String game){
        return gameIconsObject.getString(game);
    }

}
