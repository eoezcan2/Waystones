package at.emreeocn.waystone.listener;

import at.emreeocn.waystone.WaystonePlugin;
import at.emreeocn.waystone.gui.WaystoneMenu;
import at.emreeocn.waystone.item.WaystoneItem;
import at.emreeocn.waystone.model.Waystone;
import at.emreeocn.waystone.data.WaystoneAPI;
import at.emreeocn.waystone.data.WaystoneConfig;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.util.Arrays;

public class WaystoneInteractionListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getClickedBlock().getType() != WaystoneItem.MATERIAL) return;
        if(!WaystoneAPI.isWaystone(e.getClickedBlock())) return;

        Waystone waystone = WaystoneAPI.getWaystoneByBlock(e.getClickedBlock());
        assert waystone != null;
        e.setCancelled(true);
        e.getPlayer().openInventory(new WaystoneMenu(e.getPlayer(), waystone).getInventory());
    }

    @EventHandler
    public void onWaystoneBreak(BlockBreakEvent e) {
        if(!(Arrays.asList(WaystoneItem.MATERIALS).contains(e.getBlock().getType()))) return;
        if(WaystoneAPI.getWaystoneByBlock(e.getBlock()) == null) return;

        e.getPlayer().sendMessage(WaystoneConfig.getString("message-break-fail"));
        e.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        try {
            WaystonePlugin.getInstance().getWaystoneDatabase().loadForUserOnServer(e.getPlayer());
        } catch (IOException ex) {
            System.out.println("Error loading users waystones");
        }
    }

    @EventHandler
    public void onTnt(EntityExplodeEvent e) {
        for(Block b : e.blockList()) {
            if(WaystoneAPI.isWaystone(b)) {
                e.setCancelled(true);
                return;
            }
        }
    }

}
