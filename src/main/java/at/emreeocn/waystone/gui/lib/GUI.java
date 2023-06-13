package at.emreeocn.waystone.gui.lib;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class GUI implements InventoryHolder {

	private final Map<Integer, ItemIcon> icons = new HashMap<>();
	
	private String title;
	private int size;

	public GUI(String title, int size) {
		this.title = title;
		this.size = size;
	}
	
	public void setIcon(int position, ItemIcon icon) {
        this.icons.put(position, icon);
    }
 
    public ItemIcon getIcon(int position) {
        return this.icons.get(position);
    }
	
	@Override
	public Inventory getInventory() {
		Inventory inventory = Bukkit.createInventory(this, size, title);
		
		for(Entry<Integer, ItemIcon> entry : this.icons.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().getItem());
        }
		
		return inventory;
	}
	
	public String getTitle() {
		return title;
	}

	public int getSize() {
		return size;
	}
	
	public static boolean isAmountValidForUI(int amount) {
		if(amount > 0 && amount < 65) return true;
		else return false;
	}
	
}
