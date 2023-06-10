package at.emreeocn.waystone;

import at.emreeocn.waystone.commands.WaystoneCommand;
import at.emreeocn.waystone.item.WaystoneItem;
import at.emreeocn.waystone.util.WaystoneConfig;
import at.emreeocn.waystone.util.WaystoneDatabase;
import com.samjakob.spigui.SpiGUI;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class WaystonePlugin extends JavaPlugin {

    private static WaystonePlugin instance;

    private WaystoneDatabase database;
    private SpiGUI gui;

    @Override
    public void onEnable() {
        instance = this;
        this.gui = new SpiGUI(this);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        this.database = new WaystoneDatabase();

        setRecipes();

        getCommand("waystone").setExecutor(new WaystoneCommand());
    }

    @Override
    public void onDisable() {
        try {
            database.saveGson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setRecipes() {
        Bukkit.addRecipe(WaystoneItem.getRecipe());
    }

    public static WaystonePlugin getInstance() {
        return instance;
    }

    public WaystoneDatabase getDatabase() {
        return database;
    }

    public SpiGUI getGui() {
        return gui;
    }
}
