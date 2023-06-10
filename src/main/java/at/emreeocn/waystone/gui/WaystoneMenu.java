package at.emreeocn.waystone.gui;

import at.emreeocn.waystone.WaystonePlugin;
import at.emreeocn.waystone.item.WaystoneItem;
import at.emreeocn.waystone.model.Waystone;
import at.emreeocn.waystone.model.WaystoneStore;
import at.emreeocn.waystone.util.WaystoneAPI;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class WaystoneMenu {

    private UUID uuid;
    private SGMenu menu;

    public WaystoneMenu(UUID uuid) {
        this.uuid = uuid;
        this.menu = WaystonePlugin.getInstance().getGui().create("Waystones", 36);

        // GET WAYSTONES
        WaystoneStore waystoneStore = WaystoneAPI.getWaystoneStore(uuid);

        // ADD BUTTON FOR EVERY WAYSTONE
        int i = 0;
        int slot = 0;
        for(Waystone w : waystoneStore.getWaystones()) {
            SGButton button = new SGButton(waystoneItem(w, i))
                    .withListener((InventoryClickEvent event) -> {
                       if(event.getCurrentItem() == null) return;
                    });

            menu.setButton(slot, button);
            slot+=2;
        }
    }

    private static ItemStack waystoneItem(Waystone waystone, int i) {
        return new ItemStack(Material.STONE, i);
    }

    public SGMenu getMenu() {
        return menu;
    }
}
