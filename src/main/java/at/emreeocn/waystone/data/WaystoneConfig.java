package at.emreeocn.waystone.data;

import at.emreeocn.waystone.WaystonePlugin;
import org.bukkit.ChatColor;

public class WaystoneConfig {

    public static int getMaxWaystones() {
        return WaystonePlugin.getInstance().getConfig().getInt("max-waystones", 4);
    }

    public static String getString(String path) {
        return ChatColor.translateAlternateColorCodes('&', WaystonePlugin.getInstance().getConfig().getString(path));
    }

}
