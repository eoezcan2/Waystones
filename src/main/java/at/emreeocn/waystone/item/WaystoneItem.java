package at.emreeocn.waystone.item;

import at.emreeocn.waystone.data.WaystoneConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class WaystoneItem extends ItemStack {

    public static final Material[] MATERIALS = {Material.STONE_BRICK_SLAB, Material.BEACON, Material.CHISELED_STONE_BRICKS, Material.POLISHED_BLACKSTONE_BUTTON};
    public static final Material MATERIAL = Material.BEACON;

    public WaystoneItem() {
        super(MATERIAL, 1);

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

    public static boolean is(ItemStack item) {
        if (item.getType() != MATERIAL) return false;
        if (!new WaystoneItem().getItemMeta().equals(item.getItemMeta())) return false;
        return true;
    }

    public static boolean materialEquals(Material material) {
        return material == MATERIAL;
    }



}
