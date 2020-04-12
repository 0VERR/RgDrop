package pl.overr.drop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.overr.drop.inventories.DropInventory;

public class DropCommand implements CommandExecutor {

    private final DropInventory dropInventory;

    public DropCommand(DropInventory dropInventory) {
        this.dropInventory = dropInventory;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        player.openInventory(dropInventory.createDropInventory(player));
        return true;
    }
}
