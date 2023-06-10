package at.emreeocn.waystone.item;

import at.emreeocn.waystone.util.WaystoneConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class WaystoneItem extends ItemStack {

    public static final Material MATERIAL = Material.REINFORCED_DEEPSLATE;

    public WaystoneItem() {
        super(Material.REINFORCED_DEEPSLATE, 1);

        ItemMeta meta = getItemMeta();
        meta.setDisplayName(WaystoneConfig.getString("item-name"));

        // LORE
        String[] lore = {"", WaystoneConfig.getString("item-description")};
        meta.setLore(Arrays.asList(lore));

        setItemMeta(meta);
    }

    public static ShapedRecipe getRecipe() {
        ItemStack item = new WaystoneItem();
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape("ABA", "BCB", "DDD");
        recipe.setIngredient('A', Material.DIAMOND);
        recipe.setIngredient('B', Material.IRON_BLOCK);
        recipe.setIngredient('C', Material.ENDER_PEARL);
        recipe.setIngredient('D', Material.OBSIDIAN);
        return recipe;
    }

}
