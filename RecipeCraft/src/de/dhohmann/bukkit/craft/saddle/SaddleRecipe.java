package de.dhohmann.bukkit.craft.saddle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import de.dhohmann.bukkit.craft.CustomRecipe;
import de.dhohmann.bukkit.craft.RecipeManager;

public class SaddleRecipe implements RecipeManager {
    public static final String IDENTIFIER = "saddle";

    @Override
    public void onEnable() {
	ItemStack stack = new ItemStack(Material.NETHER_STAR);
	ShapedRecipe recipe = new CustomRecipe(stack, IDENTIFIER);
	recipe.shape("AAA", "A A", "B B");
	
	Plugin plugin = Bukkit.getPluginManager().getPlugin("RecipeCraft");
	FileConfiguration config = plugin.getConfig();
	
	ItemMeta meta = stack.getItemMeta();
	meta.setDisplayName(config.getString("names.saddle", "Saddle"));
	List<String> lore = new ArrayList<>();
	lore.add(config.getString("descriptions.saddle", ""));
	meta.setLore(lore);
	
	recipe.setIngredient('A', Material.LEATHER);
	recipe.setIngredient('B', Material.IRON_INGOT);
	Bukkit.addRecipe(recipe);
    }

    @Override
    public void onDisable() {
	Iterator<Recipe> iter = Bukkit.recipeIterator();
	while (iter.hasNext()) {
	    Recipe r = iter.next();
	    if (r instanceof CustomRecipe) {
		if(((CustomRecipe)r).getName().equals(IDENTIFIER)){
		    iter.remove();
		}
	    }
	}
    }

}
