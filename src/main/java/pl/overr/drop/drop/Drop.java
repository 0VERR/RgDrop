package pl.overr.drop.drop;

import org.bukkit.inventory.ItemStack;

public class Drop {
    private final String dropName;
    private final ItemStack itemStack;
    private final double chance;

    public String getDropInGuiName() {
        return dropInGuiName;
    }

    private final String dropInGuiName;
    public String getDropName() {
        return dropName;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public double getChance() {
        return chance;
    }



    public String getDropMessage() {
        return dropMessage;
    }

    private final String dropMessage;

    public Drop(String dropName, ItemStack itemStack, double chance,  String dropMessage, String dropInGuiName) {
        this.dropName = dropName;
        this.itemStack = itemStack;
        this.chance = chance;
        this.dropInGuiName = dropInGuiName;
        this.dropMessage = dropMessage;
    }
}
