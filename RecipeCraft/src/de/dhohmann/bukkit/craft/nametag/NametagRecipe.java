package de.dhohmann.bukkit.craft.nametag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;

import de.dhohmann.bukkit.craft.CustomRecipe;
import de.dhohmann.bukkit.craft.RecipeManager;

public class NametagRecipe implements RecipeManager {

    public static final String IDENTIFIER = "NameTag";
    
    @Override
    public void onEnable() {
	ItemStack stack = new ItemStack(Material.NAME_TAG);
	
	Plugin plugin = Bukkit.getPluginManager().getPlugin("RecipeCraft");
	FileConfiguration config = plugin.getConfig();
	
	ItemMeta meta = stack.getItemMeta();
	meta.setDisplayName(config.getString("names.nametag", "Nametag"));
	List<String> lore = new ArrayList<>();
	lore.add(config.getString("descriptions.nametag", ""));
	meta.setLore(lore);
	
	
	ShapedRecipe recipe = new CustomRecipe(stack, IDENTIFIER);
	recipe.shape("AAB", "ACA", "AAA");
	recipe.setIngredient('A', Material.STICK);
	recipe.setIngredient('B', Material.STRING);
	recipe.setIngredient('C', new Wool(DyeColor.BLACK));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.BLUE));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.BROWN));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.CYAN));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.GRAY));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.GREEN));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.LIGHT_BLUE));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.LIME));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.MAGENTA));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.ORANGE));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.PINK));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.PURPLE));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.RED));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.SILVER));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.WHITE));
	Bukkit.addRecipe(recipe);
	recipe.setIngredient('C', new Wool(DyeColor.YELLOW));
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
