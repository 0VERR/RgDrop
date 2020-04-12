package pl.overr.drop.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.overr.drop.drop.Drop;
import pl.overr.drop.managers.DropManager;
import pl.overr.drop.managers.UserManager;
import pl.overr.drop.user.User;
import pl.overr.drop.utils.FixColor;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class DropInventory {

    private final UserManager userManager;
    private final DropManager dropManager;
    
    public DropInventory(UserManager userManager, DropManager dropManager) {
        this.userManager = userManager;
        this.dropManager = dropManager;
    }

    public static ItemStack offItemStack(){
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(FixColor.fixer("&cWylacz wiadomosci o dropie"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack onItemStack(){
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(FixColor.fixer("&aWlacz wiadomosci o dropie"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public Inventory createDropInventory(Player player){
        Inventory inventory = Bukkit.createInventory(null,27, FixColor.fixer("&aDrop"));


        for (Drop drop : dropManager.getDropSet()) {
            ItemStack itemStack = drop.getItemStack().clone();
            String itemName = FixColor.fixer(drop.getDropInGuiName());
            String chance = String.valueOf(drop.getChance());
            User user = userManager.getUser(player.getUniqueId());
            boolean active = user.getActiveDrop().get(drop.getDropName());
            String activeString;



            if (active){
                activeString = FixColor.fixer("&aTak");
            } else {
                activeString = FixColor.fixer("&cNie");
            }

            List<String> lore = Arrays.asList(FixColor.fixer("&8» &2Szansa na drop&8: &c" + chance), FixColor.fixer("&8» &2Aktywny&8: " + activeString));

            ItemMeta itemMeta = itemStack.getItemMeta();
            if (active) itemMeta.addEnchant(Enchantment.DURABILITY,0,true);
            itemMeta.setDisplayName(itemName);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            inventory.addItem(itemStack);

            if (user.isActiveMessage()){
                inventory.setItem(inventory.getSize() - 1, offItemStack());
            } else {
                inventory.setItem(inventory.getSize() - 1, onItemStack());

            }
        }
        return inventory;
    }
}
