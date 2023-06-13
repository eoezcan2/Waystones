package at.emreeocn.waystone.listener;

import at.emreeocn.waystone.item.WaystoneItem;
import at.emreeocn.waystone.model.Waystone;
import at.emreeocn.waystone.data.WaystoneAPI;
import at.emreeocn.waystone.data.WaystoneConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;
import java.util.HashSet;

public class WaystoneCreationListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(!WaystoneItem.is(e.getItemInHand())) return;

        e.getBlockPlaced().setType(Material.AIR);
        startCreation(e.getPlayer(), e.getBlockPlaced().getLocation());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (WaystoneAPI.getCreationMode().size() == 0) return;
        if (!WaystoneAPI.getCreationMode().contains(e.getPlayer().getUniqueId())) return;

        e.setCancelled(true);

        createWaystone(e.getPlayer(), e.getMessage());
    }

    private void startCreation(Player player, Location loc) {
        if (WaystoneAPI.getCreationMode().contains(player.getUniqueId())) {
            player.sendMessage(WaystoneConfig.getString("message-create-already"));
            return;
        }

        if (WaystoneAPI.getWaystoneStore(player.getUniqueId()).getWaystones().size() >= WaystoneConfig.getMaxWaystones()) {
            player.sendMessage(WaystoneConfig.getString("message-create-fail").replace("%max-waystones%", "" + WaystoneConfig.getMaxWaystones()));
            return;
        }

        WaystoneAPI.getCreationMode().add(player.getUniqueId());
        WaystoneAPI.getCreationLocations().put(player.getUniqueId(), loc);

        // MESSAGE
        player.sendMessage(WaystoneConfig.getString("message-create-name"));
    }

    private void createWaystone(Player player, String name) {
        if (name.length() > 16) {
            player.sendMessage(WaystoneConfig.getString("message-create-name-too-long"));
            return;
        }

        Location loc = WaystoneAPI.getCreationLocations().get(player.getUniqueId());
        Location iconLoc = loc.clone();
        Waystone waystone = new Waystone(player.getUniqueId(), name, loc, false, new HashSet<>(), iconLoc.add(0, -1, 0).getBlock().getType());

        try {
            boolean created = WaystoneAPI.createWaystone(waystone);
            if (!created) {
                String msg = WaystoneConfig.getString("message-create-fail").replace("%max-waystone%", waystone.getName());
                player.sendMessage(msg);
                player.getInventory().addItem(new WaystoneItem());
                stopCreation(player);
                return;
            } else {
                String msg = WaystoneConfig.getString("message-successful-create").replace("%waystone%", waystone.getName());
                Bukkit.getPlayer(waystone.getPlayer()).sendMessage(msg);
            }

        } catch (IOException e) {
            player.sendMessage(WaystoneConfig.getString("message-error"));
        }
        stopCreation(player);
    }

    private void stopCreation(Player player) {
        WaystoneAPI.removePlayerFromCreation(player);
    }

}
