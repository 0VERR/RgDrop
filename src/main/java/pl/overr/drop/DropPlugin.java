package pl.overr.drop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.overr.drop.commands.DropCommand;
import pl.overr.drop.drop.DropLoader;
import pl.overr.drop.inventories.DropInventory;
import pl.overr.drop.listeners.InventoryClickListener;
import pl.overr.drop.listeners.PlayerJoinListener;
import pl.overr.drop.listeners.PlayerMineListener;
import pl.overr.drop.managers.DropManager;
import pl.overr.drop.managers.UserManager;
import pl.overr.drop.storage.Hikari;

public class DropPlugin extends JavaPlugin {


    public static DropPlugin getDropPlugin() {
        return dropPlugin;
    }

    private static DropPlugin dropPlugin;


    private UserManager userManager;
    private DropManager dropManager;
    private DropInventory dropInventory;
    private DropLoader dropLoader;
    private Hikari hikari;
    @Override
    public void onEnable(){
        dropPlugin = this;
        saveDefaultConfig();
        this.hikari = new Hikari();
        this.userManager = new UserManager(hikari);
        this.dropManager = new DropManager();
        this.dropLoader = new DropLoader(dropManager);
        this.dropInventory = new DropInventory(userManager,dropManager);
        getCommand("drop").setExecutor(new DropCommand(dropInventory));
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryClickListener(userManager,dropManager), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(userManager,dropManager), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerMineListener(dropManager,userManager), this);

        userManager.loadUsers();
    }

    @Override
    public void onDisable(){
        userManager.getUserHashMap().values().forEach(user ->{
            userManager.updateSQL(user);
        });
    }
}
