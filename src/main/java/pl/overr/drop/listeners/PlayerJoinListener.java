package pl.overr.drop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.overr.drop.drop.Drop;
import pl.overr.drop.managers.DropManager;
import pl.overr.drop.managers.UserManager;
import pl.overr.drop.user.User;

import java.util.HashMap;
import java.util.Map;

public class PlayerJoinListener implements Listener {

    private final UserManager userManager;
    private final DropManager dropManager;
    public PlayerJoinListener(UserManager userManager, DropManager dropManager) {
        this.userManager = userManager;
        this.dropManager = dropManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (!userManager.checkContains(player.getUniqueId())){
            Map<String,Boolean> dropMap = new HashMap<>();
            for (Drop drop : dropManager.getDropSet()) {
                dropMap.put(drop.getDropName(),true);
            }

            User user = new User(player.getUniqueId(), true, dropMap,0);
            userManager.addToUsers(user);
            userManager.insertIntoMySQL(user);
        }
    }

}
