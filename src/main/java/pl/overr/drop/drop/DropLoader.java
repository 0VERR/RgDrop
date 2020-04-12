package pl.overr.drop.drop;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.overr.drop.DropPlugin;
import pl.overr.drop.managers.DropManager;
import pl.overr.drop.utils.FixColor;

public class DropLoader {

    private final FileConfiguration cfg = DropPlugin.getDropPlugin().getConfig();

    private final DropManager dropManager;

    public DropLoader(DropManager dropManager) {
        this.dropManager = dropManager;
        loadDrop();
    }

    public void loadDrop(){
        for (String drop : cfg.getConfigurationSection("Drop").getKeys(false)){
            String dropInGuiName = FixColor.fixer(cfg.getString("Drop." + drop +".nameInGui"));
            String stringMaterial = cfg.getString("Drop." + drop + ".material");
            String dropMessage = cfg.getString("Drop." + drop + ".message");
            short durability = (short) cfg.getInt(cfg.getString("Drop." + drop + ".durability"));
            double chance = cfg.getDouble("Drop." + drop + ".chance");

            dropManager.addToDrop(new Drop(drop, new ItemStack(Material.getMaterial(stringMaterial),1,durability), chance, dropMessage, dropInGuiName));
        }
    }
}
