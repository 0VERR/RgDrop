package pl.overr.drop.listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.overr.drop.drop.Drop;
import pl.overr.drop.inventories.DropInventory;
import pl.overr.drop.managers.DropManager;
import pl.overr.drop.managers.UserManager;
import pl.overr.drop.user.User;
import pl.overr.drop.utils.FixColor;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class InventoryClickListener implements Listener {


    private final UserManager userManager;
    private final DropManager dropManager;
    public InventoryClickListener(UserManager userManager, DropManager dropManager) {
        this.userManager = userManager;
        this.dropManager = dropManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        final Inventory inventory = event.getInventory();

        if (inventory.getName().equalsIgnoreCase(FixColor.fixer("&aDrop"))) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            User user = userManager.getUser(event.getWhoClicked().getUniqueId());
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem.getType() == Material.PAPER){
                if (user.isActiveMessage()){
                    user.setActiveMessage(false);
                    inventory.setItem(inventory.getSize() -1, DropInventory.onItemStack());
                } else {
                    inventory.setItem(inventory.getSize() -1, DropInventory.offItemStack());
                    user.setActiveMessage(true);
                }
                  return;
                }

                for (Drop drop : dropManager.getDropSet()) {
                    if (drop.getDropInGuiName().equalsIgnoreCase(clickedItem.getItemMeta().getDisplayName())) {
                        ItemMeta itemMeta = clickedItem.getItemMeta();
                        List<String> fixLore = itemMeta.getLore();

                        if (user.getActiveDrop().get(drop.getDropName())) {
                            fixLore.set(1, FixColor.fixer("&8» &2Aktywny&8: &cNie"));
                            user.getActiveDrop().put(drop.getDropName(), false);
                            itemMeta.removeEnchant(Enchantment.DURABILITY);
                        } else {
                            fixLore.set(1, FixColor.fixer("&8» &2Aktywny&8: &aTak"));
                            user.getActiveDrop().put(drop.getDropName(), true);
                            itemMeta.addEnchant(Enchantment.DURABILITY, 0, true);

                        }

                        itemMeta.setLore(fixLore);
                        clickedItem.setItemMeta(itemMeta);

                    }
                }

            }
        }
}
