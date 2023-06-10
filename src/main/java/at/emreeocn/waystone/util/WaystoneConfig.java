package at.emreeocn.waystone.util;

import at.emreeocn.waystone.WaystonePlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class WaystoneConfig {

    public static int getMaxWaystones() {
        return WaystonePlugin.getInstance().getConfig().getInt("max-waystones", 4);
    }

    public static String getString(String path) {
        return ChatColor.translateAlternateColorCodes('&', WaystonePlugin.getInstance().getConfig().getString(path));
    }

}
