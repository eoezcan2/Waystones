package at.emreeocn.waystone;

import org.bukkit.plugin.java.JavaPlugin;

public class WaystonePlugin extends JavaPlugin {

    private static WaystonePlugin instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    public static WaystonePlugin getInstance() {
        return instance;
    }
}
