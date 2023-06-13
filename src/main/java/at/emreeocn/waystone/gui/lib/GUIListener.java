package at.emreeocn.waystone.gui.lib;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
	    //Check if the inventory is custom
	    if (event.getView().getTopInventory().getHolder() instanceof GUI) {
	        //Cancel the event
	        event.setCancelled(true);

	        //Check if who clicked is a Player
	        if (event.getWhoClicked() instanceof Player) {
	            Player player = (Player) event.getWhoClicked();

	            //Check if the item the player clicked on is valid
	            ItemStack itemStack = event.getCurrentItem();
	            if (itemStack == null || itemStack.getType() == Material.AIR) return;

	            //Get our CustomHolder
	            GUI customHolder = (GUI) event.getView().getTopInventory().getHolder();

	            //Check if the clicked slot is any icon
	            ItemIcon icon = customHolder.getIcon(event.getRawSlot());
	            if (icon == null) return;

	            //Execute all the actions
	            for (ClickAction clickAction : icon.getClickActions()) {
	                clickAction.onClick(player, event);
	            }
	        }
	    }
	}
	
}
