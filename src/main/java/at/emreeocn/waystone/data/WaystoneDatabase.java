package at.emreeocn.waystone.data;

import at.emreeocn.waystone.WaystonePlugin;
import at.emreeocn.waystone.model.Waystone;
import at.emreeocn.waystone.model.WaystoneStore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WaystoneDatabase {

    private final File file;
    private final YamlConfiguration db;

    public WaystoneDatabase() {
        try {
            if (!WaystonePlugin.getInstance().getDataFolder().exists())
                WaystonePlugin.getInstance().getDataFolder().mkdir();
            file = new File(WaystonePlugin.getInstance().getDataFolder(), "waystones.yml");
            if (!file.exists()) {
                file.createNewFile();
            }

            db = YamlConfiguration.loadConfiguration(file);

            loadYaml();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadYaml() {
        if(db.getKeys(false) == null) return;
        if(db.getKeys(false).isEmpty()) return;
        Set<WaystoneStore> stores = new HashSet<>();
        for (String key : db.getKeys(false)) {
            UUID player = UUID.fromString(key);
            Set<Waystone> waystones = new HashSet<>();
            for (String key1 : db.getConfigurationSection(key).getKeys(false)) {
                boolean isPublic = db.getBoolean(key + "." + key1 + ".isPublic");
                double x = db.getDouble(key + "." + key1 + ".location.x");
                double y = db.getDouble(key + "." + key1 + ".location.y");
                double z = db.getDouble(key + "." + key1 + ".location.z");
                String world = db.getString(key + "." + key1 + ".location.world");
                HashSet<Block> blocks = new HashSet<>();
                for (String key2 : db.getConfigurationSection(key + "." + key1 + ".blocks").getKeys(false)) {
                    Location loc = (Location) db.get(key + "." + key1 + ".blocks." + key2);
                    blocks.add(loc.getBlock());
                }
                Material icon = Material.valueOf(db.getString(key + "." + key1 + ".icon"));
                Waystone waystone = new Waystone(player, key1, new Location(Bukkit.getWorld(world), x, y, z), isPublic, blocks, icon);
                waystones.add(waystone);
            }
            WaystoneStore store = new WaystoneStore(player, waystones);
            stores.add(store);
        }
        WaystoneAPI.setStores(stores);
    }

    public void loadForUsersOnServer() throws IOException {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(!WaystoneAPI.hasStore(p.getUniqueId())) {
                WaystoneAPI.addWaystoneStore(new WaystoneStore(p.getUniqueId(), new HashSet<>()));
            }
        }
    }

    public void loadForUserOnServer(Player player) throws IOException {
        if(!WaystoneAPI.hasStore(player.getUniqueId())) {
            WaystoneAPI.addWaystoneStore(new WaystoneStore(player.getUniqueId(), new HashSet<>()));
        }
    }

    public void removeWaystone(Waystone waystone) {
        db.set(waystone.getPlayer() + "." + waystone.getName(), null);
    }

    public void save() throws IOException {
        for (WaystoneStore store : WaystoneAPI.getStores()) {
            for (Waystone waystone : store.getWaystones()) {
                db.set(store.getPlayer().toString() + "." + waystone.getName() + ".isPublic", waystone.isPublic());
                db.set(store.getPlayer().toString() + "." + waystone.getName() + ".location.x", waystone.getLocation().getX());
                db.set(store.getPlayer().toString() + "." + waystone.getName() + ".location.y", waystone.getLocation().getY());
                db.set(store.getPlayer().toString() + "." + waystone.getName() + ".location.z", waystone.getLocation().getZ());
                db.set(store.getPlayer().toString() + "." + waystone.getName() + ".location.world", waystone.getLocation().getWorld().getName());
                for(Block b : waystone.getBlocks()) {
                    db.set(store.getPlayer().toString() + "." + waystone.getName() + ".blocks." + b.hashCode(), b.getLocation());
                }
                db.set(store.getPlayer().toString() + "." + waystone.getName() + ".icon", waystone.getIcon().name());
            }
        }
        db.save(file);
    }


}
