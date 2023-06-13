package at.emreeocn.waystone.gui.lib;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemIcon {

	private final ItemStack item;
	
	public final List<ClickAction> clickActions = new ArrayList<>();
	
	public ItemIcon(ItemStack item) {
		this.item = item;
	}

	public ItemIcon addClickAction(ClickAction clickAction) {
        this.clickActions.add(clickAction);
        return this;
    }

    public List<ClickAction> getClickActions() {
        return this.clickActions;
    }
	
	public ItemStack getItem() {
		return item;
	}
	
}
