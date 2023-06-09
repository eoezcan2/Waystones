package at.emreeocn.waystone.util;

import at.emreeocn.waystone.WaystonePlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class WaystoneConfig {

    public static int getMaxWaystones() {
        return WaystonePlugin.getInstance().getConfig().getInt("max-waystones", 4);
    }

}