package at.emreeocn.waystone.gui.lib;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ClickAction {

	void onClick(Player player, InventoryClickEvent event);
	
}
