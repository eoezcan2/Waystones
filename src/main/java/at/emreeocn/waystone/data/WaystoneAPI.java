package at.emreeocn.waystone.data;

import at.emreeocn.waystone.WaystonePlugin;
import at.emreeocn.waystone.item.WaystoneItem;
import at.emreeocn.waystone.model.Waystone;
import at.emreeocn.waystone.model.WaystoneStore;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.*;

public class WaystoneAPI {

    private static final Set<WaystoneStore> stores = new HashSet<>();

    public static Set<WaystoneStore> getStores() {
        return stores;
    }

    private static final Set<UUID> creationMode = new HashSet<>();
    private static final Map<UUID, Location> creationLocations = new HashMap<>();

    public static Map<UUID, Location> getCreationLocations() {
        return creationLocations;
    }

    public static Set<UUID> getCreationMode() {
        return creationMode;
    }

    public static boolean createWaystone(Waystone waystone) throws IOException {
        WaystoneStore store = getWaystoneStore(waystone.getPlayer());
        if (store == null) {
            store = new WaystoneStore(waystone.getPlayer(), new HashSet<>());
            addWaystoneStore(store);
        }
        boolean created = store.addWaystone(waystone);
        if(!created) return false;

        waystone.build();
        return true;
    }

    public static void toggleVisibility(Waystone waystone) throws IOException {
        waystone.setPublic(!waystone.isPublic());
        WaystonePlugin.getInstance().getWaystoneDatabase().save();
    }

    public static Set<Waystone> getAllWaystones() {
        Set<Waystone> waystones = new HashSet<>();
        for (WaystoneStore store : stores) {
            waystones.addAll(store.getWaystones());
        }
        return waystones;
    }

    public static void addWaystoneStore(WaystoneStore store) throws IOException {
        stores.add(store);
        WaystonePlugin.getInstance().getWaystoneDatabase().save();
    }

    public static WaystoneStore getWaystoneStore(UUID player) {
        return stores.stream().filter(store -> store.getPlayer().equals(player)).findFirst().orElse(null);
    }

    public static boolean isWaystone(Block block) {
        if (block == null) return false;
        if (!(WaystoneItem.MATERIAL == block.getType())) return false;
        return stores.stream().anyMatch(store -> store.getWaystones().stream().anyMatch(waystone -> waystone.getBlocks().contains(block)));
    }

    public static Waystone getWaystoneByBlock(Block block) {
        for (WaystoneStore store : stores) {
            for (Waystone waystone : store.getWaystones()) {
                if (waystone.getBlocks().contains(block)) {
                    return waystone;
                }
            }
        }
        return null;
    }

    public static void removeWaystone(Waystone waystone) {
        WaystoneStore store = getWaystoneStore(waystone.getPlayer());
        if (store == null) return;
        waystone.destroy();
        store.getWaystones().remove(waystone);
        WaystonePlugin.getInstance().getWaystoneDatabase().removeWaystone(waystone);
    }

    public static void removePlayerFromCreation(Player player) {
        WaystoneAPI.getCreationMode().remove(player.getUniqueId());
        WaystoneAPI.getCreationLocations().remove(player.getUniqueId());
    }

    public static void setStores(Set<WaystoneStore> stores) {
        WaystoneAPI.stores.clear();
        WaystoneAPI.stores.addAll(stores);
    }

    public static boolean hasStore(UUID uuid) {
        return stores.stream().anyMatch(store -> store.getPlayer().equals(uuid));
    }
}
