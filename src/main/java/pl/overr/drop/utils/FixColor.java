package pl.overr.drop.utils;

import org.bukkit.ChatColor;

public class FixColor {

    public static String fixer(String arg){
        return ChatColor.translateAlternateColorCodes('&', arg);
    }
}
