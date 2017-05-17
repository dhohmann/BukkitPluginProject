package de.dhohmann.bukkit.craft.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import de.dhohmann.bukkit.craft.CraftPlugin;
import de.dhohmann.bukkit.inventory.CustomShapedRecipe;
import de.dhohmann.bukkit.util.LanguageSelector;

public class Nametag extends CustomShapedRecipe {
    
    private LanguageSelector languages;
    private CraftPlugin plugin;
    
    public Nametag() {
	super(new ItemStack(Material.NAME_TAG, 2));
	plugin = (CraftPlugin) Bukkit.getPluginManager().getPlugin("CraftPlugin");
	languages = plugin.getLanguages();
    }
    
    @Override
    public String getIdentifier() {
	return "Nametag";
    }

    @Override
    public void activate() {
	Properties config = languages.getLanguage(Locale.getDefault());
	
	ItemMeta meta = this.getResult().getItemMeta();
	meta.setDisplayName(config.getProperty("titleNametag", "Nametag"));
	List<String> lore = new ArrayList<>();
	lore.add(config.getProperty("descrNametag", ""));
	meta.setLore(lore);
	this.getResult().setItemMeta(meta);
	
	ShapedRecipe recipe = this;
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
    public void deactivate() {
	Iterator<Recipe> iter = Bukkit.recipeIterator();
	while (iter.hasNext()) {
	    Recipe r = iter.next();
	    if (r instanceof CustomShapedRecipe) {
		if(((CustomShapedRecipe)r).getIdentifier().equals(this.getIdentifier())){
		    iter.remove();
		}
	    }
	}
    }

}
