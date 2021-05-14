package tech.zorkai.rezig.utils;

import org.json.JSONObject;
import org.json.JSONTokener;
import tech.zorkai.rezig.REZig;

public class RankUtils {

    public final JSONObject deathrun = new JSONObject(
            new JSONTokener(
                    REZig.class.getResourceAsStream("/games/deathrun.json")
            ));

    public final JSONObject blockparty = new JSONObject(
            new JSONTokener(
                    REZig.class.getResourceAsStream("/games/blockparty.json")
            ));

    public final JSONObject timv = new JSONObject(
            new JSONTokener(
                    REZig.class.getResourceAsStream("/games/timv.json")
            ));

    public final JSONObject bedwars = new JSONObject(
            new JSONTokener(
                    REZig.class.getResourceAsStream("/games/bedwars.json")
            ));

}
