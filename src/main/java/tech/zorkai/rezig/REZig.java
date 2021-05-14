package tech.zorkai.rezig;

import net.labymod.api.EventManager;
import net.labymod.api.LabyModAPI;
import net.labymod.api.LabyModAddon;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.settings.elements.SettingsElement;
import org.json.JSONObject;
import tech.zorkai.rezig.labymod.Category;
import tech.zorkai.rezig.labymod.modules.NextRank;
import tech.zorkai.rezig.labymod.modules.StaffChat;
import tech.zorkai.rezig.listeners.*;
// import tech.zorkai.rezig.nametag.NameTagRenderer;
import tech.zorkai.rezig.utils.DiscordRPC;
import tech.zorkai.rezig.utils.RankUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class REZig extends LabyModAddon {

    public DiscordRPC discordRPC = new DiscordRPC();
    public boolean playingRevive = false;
    public boolean staffChat = false;
    public int points = 0;
    public String currentGame = "";
    public Map<Integer, String> currentRanks = new TreeMap<>();
    public final RankUtils ranksUtils = new RankUtils();

    @Override
    public void onEnable() {
        LabyModAPI api = this.getApi();
        EventManager eventManager = api.getEventManager();
        eventManager.registerOnIncomingPacket(new IncomingPacket(this));
        //eventManager.register(new NameTagRenderer(this)); Add back when API out
        eventManager.register(new ServerMessage(this));
        eventManager.register(new MessageSend(this));
        eventManager.registerOnJoin(new ServerJoin(this));
        eventManager.registerOnQuit(new ServerQuit(this));

        ModuleCategoryRegistry.loadCategory(new Category());
        api.registerModule(new StaffChat(this));
        api.registerModule(new NextRank(this));
    }

    @Override
    public void loadConfig() {
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
    }

    public void updateRanks(String game){
        JSONObject ranks;
        currentRanks = new TreeMap<>();

        if (game.equals("DR")) {
            ranks = ranksUtils.deathrun;
        } else if (game.equals("BP")){
            ranks = ranksUtils.blockparty;
        } else if (game.equals("TIMV")){
            ranks = ranksUtils.timv;
        } else if (game.contains("BW")){
            ranks = ranksUtils.bedwars;
        } else {
            return;
        }

        ranks = (JSONObject) ranks.get("ranks");
        for (String key : ranks.keySet()){
            System.out.println(key);
            JSONObject rank = (JSONObject) ranks.get(key);
            currentRanks.put(rank.getInt("max_points"),
                    rank.getString("display"));
        }

    }

}