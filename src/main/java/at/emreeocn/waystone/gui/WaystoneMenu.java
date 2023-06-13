package at.emreeocn.waystone.gui;

import at.emreeocn.waystone.gui.lib.GUI;
import at.emreeocn.waystone.gui.lib.ItemIcon;
import at.emreeocn.waystone.model.Waystone;
import at.emreeocn.waystone.data.WaystoneAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class WaystoneMenu extends GUI {

    private final Player player;
    private final Waystone current;

    public WaystoneMenu(Player player, Waystone current) {
        super("Waystones", 9*6);
        this.player = player;
        this.current = current;

        int slot = 0;
        for (Waystone w : WaystoneAPI.getAllWaystones()) {
            if (!w.isPublic() && !(w.getPlayer().equals(player.getUniqueId()))) continue;
            ItemIcon icon = new ItemIcon(waystoneItem(w));
            icon.addClickAction((p, event) -> {
                if (event.isLeftClick()) {
                    if(w == current) return;
                    p.closeInventory();
                    w.teleport(p);
                }

                if (event.isRightClick()) {
                    if(w.getPlayer().equals(p.getUniqueId())) {
                        p.openInventory(new WaystoneDetailMenu(p.getUniqueId(), w).getInventory());
                        return;
                    } else {
                        return;
                    }
                }

                p.closeInventory();
            });
            setIcon(slot, icon);

            slot++;
        }

        fillBlank();
    }

    private void fillBlank() {
        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getIcon(i) == null) {
                setIcon(i, new ItemIcon(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1)));
            }
        }
    }

    private ItemStack waystoneItem(Waystone waystone) {
        ItemStack item = new ItemStack(waystone.getIcon());

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + waystone.getName());

        String owner = "";
        if(Bukkit.getPlayer(waystone.getPlayer()) == null) {
            owner = Bukkit.getOfflinePlayer(waystone.getPlayer()).getName();
        } else {
            owner = Bukkit.getPlayer(waystone.getPlayer()).getDisplayName();
        }

        boolean isOwner = this.current.getPlayer().equals(waystone.getPlayer());

        String[] lore = {
                "",
                ChatColor.GRAY + "Owner: " + ChatColor.DARK_GRAY + owner,
                isOwner ? ChatColor.GRAY + "Rightclick to view" : "",
        };

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

}
