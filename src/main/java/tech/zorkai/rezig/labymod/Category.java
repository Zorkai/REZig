package tech.zorkai.rezig.labymod;


import net.labymod.ingamegui.ModuleCategory;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

public class Category extends ModuleCategory {

    public Category() {
        super("REZig", true, new ControlElement.IconData(Material.REDSTONE));
    }

}