package at.emreeocn.waystone;

import at.emreeocn.namer.NamerDatabase;
import at.emreeocn.namer.NamerListener;
import at.emreeocn.waystone.commands.WaystoneCommand;
import at.emreeocn.waystone.gui.lib.GUIListener;
import at.emreeocn.waystone.item.WaystoneItem;
import at.emreeocn.waystone.listener.WaystoneCreationListener;
import at.emreeocn.waystone.listener.WaystoneInteractionListener;
import at.emreeocn.waystone.data.WaystoneDatabase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class WaystonePlugin extends JavaPlugin {

    private static WaystonePlugin instance;

    private WaystoneDatabase waystoneDatabase;
    private NamerDatabase namerDatabase;

    @Override
    public void onEnable() {
        instance = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        this.waystoneDatabase = new WaystoneDatabase();
        this.namerDatabase = new NamerDatabase();

        setRecipes();

        Bukkit.getPluginManager().registerEvents(new WaystoneCreationListener(), this);
        Bukkit.getPluginManager().registerEvents(new WaystoneInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new NamerListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);

        getCommand("waystone").setExecutor(new WaystoneCommand());

        try {
            this.waystoneDatabase.loadForUsersOnServer();
        } catch (IOException e) {
            System.out.println("Could not load waystones!");
        }
    }

    @Override
    public void onDisable() {
        try {
            this.waystoneDatabase.save();
        } catch (IOException e) {
            System.out.println("Could not save waystones!");
        }
    }

    private void setRecipes() {
        Bukkit.addRecipe(WaystoneItem.getRecipe());
    }

    public static WaystonePlugin getInstance() {
        return instance;
    }

    public WaystoneDatabase getWaystoneDatabase() {
        return waystoneDatabase;
    }

    public NamerDatabase getNamerDatabase() {
        return namerDatabase;
    }
}
