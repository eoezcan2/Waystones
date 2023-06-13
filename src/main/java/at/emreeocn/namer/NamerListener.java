package at.emreeocn.namer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NamerListener implements Listener {

    @EventHandler
    public void onRename(PrepareAnvilEvent e) {
        if (e.getResult() != null && !e.getInventory().getRenameText().equals("")) {
            if (!NamerAPI.itemNameExists(e.getInventory().getRenameText())) return;
            e.setResult(null);
        }
    }

    @EventHandler
    public void onTake(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.ANVIL) return;

        if (e.getSlot() == 2) {
            if (!NamerAPI.itemNameExists(((AnvilInventory) e.getClickedInventory()).getRenameText())) {
                NamerAPI.saveItemName(((AnvilInventory) e.getClickedInventory()).getRenameText());
                return;
            }
            e.setCancelled(true);
            ((Player) e.getWhoClicked()).closeInventory();
            ((Player) e.getWhoClicked()).sendMessage(ChatColor.RED + "This name already exists!");
        }
    }

}
