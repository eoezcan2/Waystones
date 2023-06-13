package at.emreeocn.waystone.gui;

import at.emreeocn.waystone.data.WaystoneAPI;
import at.emreeocn.waystone.gui.lib.GUI;
import at.emreeocn.waystone.gui.lib.ItemIcon;
import at.emreeocn.waystone.model.Waystone;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class WaystoneDetailMenu extends GUI {

    private UUID uuid;
    private Waystone waystone;

    public WaystoneDetailMenu(UUID uuid, Waystone waystone) {
        super("Waystone Details", 9*5);
        this.uuid = uuid;
        this.waystone = waystone;

        // setIcon(0, backItem());
        setIcon(21, deleteItem());
        setIcon(23, visibilityItem());

        fillBlank();
    }

    private void fillBlank() {
        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getIcon(i) == null) {
                setIcon(i, new ItemIcon(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1)));
            }
        }
    }

    public ItemIcon backItem() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GRAY + "Back");

        item.setItemMeta(meta);

        ItemIcon icon = new ItemIcon(item);
        icon.addClickAction((player, event) -> {
            player.openInventory(new WaystoneMenu(player, waystone).getInventory());
        });

        return icon;
    }

    public ItemIcon visibilityItem() {
        ItemStack item = new ItemStack(waystone.isPublic() ? Material.LIME_DYE : Material.PURPLE_DYE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(waystone.isPublic() ? ChatColor.GREEN + "Public" : ChatColor.RED + "Private");

        String[] lore = {
                ChatColor.GRAY + "Click to change visibility"
        };

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        ItemIcon icon = new ItemIcon(item);
        icon.addClickAction((player, event) -> {
            try {
                WaystoneAPI.toggleVisibility(waystone);
            } catch (IOException e) {
                player.sendMessage(ChatColor.RED + "Error while changing visibility!");
                player.closeInventory();
                return;
            }

            // REOPEN
            player.openInventory(new WaystoneDetailMenu(uuid, waystone).getInventory());
        });

        return icon;
    }

    public ItemIcon deleteItem() {
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.RED + "Delete");

        item.setItemMeta(meta);

        ItemIcon icon = new ItemIcon(item);
        icon.addClickAction((player, event) -> {
            WaystoneAPI.removeWaystone(waystone);
            String msg = "Waystone " + ChatColor.GREEN + waystone.getName() + ChatColor.RESET + " deleted!";
            player.sendMessage(msg);

            player.closeInventory();
        });

        return icon;
    }

}
