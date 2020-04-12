package pl.overr.drop.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.overr.drop.drop.Drop;
import pl.overr.drop.managers.DropManager;
import pl.overr.drop.managers.UserManager;
import pl.overr.drop.user.User;
import pl.overr.drop.utils.FixColor;

import java.lang.management.PlatformLoggingMXBean;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PlayerMineListener implements Listener {

    private final DropManager dropManager;
    private final UserManager userManager;

    public PlayerMineListener(DropManager dropManager, UserManager userManager) {
        this.dropManager = dropManager;
        this.userManager = userManager;
    }

    @EventHandler
    public void onMine(BlockBreakEvent event){

        Player player = event.getPlayer();

        if (event.getBlock().getType() == Material.COAL_ORE || event.getBlock().getType() == Material.DIAMOND_ORE || event.getBlock().getType() == Material.GOLD_ORE || event.getBlock().getType() == Material.IRON_ORE || event.getBlock().getType() == Material.REDSTONE_ORE){
            player.sendMessage(FixColor.fixer("&cDrop z rud jest wylaczony"));
            return;
        }


        if (event.getBlock().getType() == Material.STONE && player.getGameMode() != GameMode.CREATIVE){
        double random = ThreadLocalRandom.current().nextDouble(0,100);
        User user = userManager.getUser(player.getUniqueId());


        dropManager.getDropSet().stream()
                .filter(drop -> drop.getChance() > random)
                .filter(drop -> user.getActiveDrop().get(drop.getDropName()))
                .forEach(drop ->{
                    player.getInventory().addItem(drop.getItemStack());

                    if (user.isActiveMessage()) player.sendMessage(FixColor.fixer(drop.getDropMessage()));

                });

        user.setMinedStone(user.getMinedStone() + 1);
        }
    }
}
