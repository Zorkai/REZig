package tech.zorkai.rezig.labymod.modules;

import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement.IconData;
import net.labymod.utils.Material;
import tech.zorkai.rezig.REZig;
import tech.zorkai.rezig.labymod.Category;

public class StaffChat extends SimpleModule {

    private final REZig main;

    public StaffChat(REZig main) {
        this.main = main;
    }

    @Override
    public String getDisplayName() {
        return "Staff Chat";
    }

    @Override
    public String getDisplayValue() {
        if (main.staffChat)
            return "On";

        return "Off";
    }

    @Override
    public String getDefaultValue() {
        return "Off";
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
        return "Staff Chat";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public String getControlName() {
        return "Staff Chat Status";
    }

    @Override
    public ModuleCategory getCategory() {
        return new Category();
    }

    @Override
    public boolean isShown() {
        return main.playingRevive;
    }

}