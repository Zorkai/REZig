package tech.zorkai.rezig.labymod.modules;

import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement.IconData;
import net.labymod.utils.Material;
import tech.zorkai.rezig.REZig;
import tech.zorkai.rezig.labymod.Category;

import java.util.Iterator;

public class NextRank extends SimpleModule {

    private final REZig main;

    public NextRank(REZig main) {
        this.main = main;
    }

    @Override
    public String getDisplayName() {
        return "Next Rank";
    }

    @Override
    public String getDisplayValue() {
        int rankPoints = 0;

        Iterator iterator = main.currentRanks.keySet().iterator();
        String nextRankText = "";

        while (iterator.hasNext()) {
            rankPoints = (int) iterator.next();
            if (!(main.points > rankPoints)){
                if (iterator.hasNext()) nextRankText = main.currentRanks.get(iterator.next());
                break;
            }
        }

        return (rankPoints - main.points) + " - " + nextRankText;
    }

    @Override
    public String getDefaultValue() {
        return "0";
    }

    @Override
    public IconData getIconData() {
        return new IconData(Material.PAPER);
    }

    @Override
    public void loadSettings() {
    }

    @Override
    public String getSettingName() {
        return "Next Rank";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getSortingId() {
        return 1;
    }

    @Override
    public String getControlName() {
        return "Points to Next Rank";
    }

    @Override
    public ModuleCategory getCategory() {
        return new Category();
    }

    @Override
    public boolean isShown() {
        return main.playingRevive && !main.currentGame.equals("LOBBY") && !main.currentGame.equals("TIMV");
    }

}