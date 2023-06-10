package at.emreeocn.waystone.util;

import at.emreeocn.waystone.item.WaystoneItem;
import at.emreeocn.waystone.model.Waystone;
import at.emreeocn.waystone.model.WaystoneStore;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WaystoneAPI {

    private static Set<WaystoneStore> stores = new HashSet<>();

    public static void addWaystone(Waystone waystone) {
        WaystoneStore store = getWaystoneStore(waystone.getPlayer());
        store.addWaystone(waystone);
    }

    public static void addWaystoneStore(WaystoneStore store) {
        stores.add(store);
    }

    public static WaystoneStore getWaystoneStore(UUID player) {
        return stores.stream().filter(store -> store.getPlayer().equals(player)).findFirst().orElse(null);
    }

    public static boolean isWaystone(Block block) {
        if(block == null) return false;
        if(block.getType() != WaystoneItem.MATERIAL) return false;
        return stores.stream().anyMatch(store -> store.getWaystones().stream().anyMatch(waystone -> waystone.getLocation().equals(block.getLocation())));
    }

}
