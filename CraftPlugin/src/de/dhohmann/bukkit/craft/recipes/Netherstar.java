package de.dhohmann.bukkit.craft.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.dhohmann.bukkit.craft.CraftPlugin;
import de.dhohmann.bukkit.inventory.CustomShapedRecipe;
import de.dhohmann.bukkit.util.LanguageSelector;

public class Netherstar extends CustomShapedRecipe {
    
    private CraftPlugin plugin;
    private LanguageSelector languages;
    
    public Netherstar() {
	super(new ItemStack(Material.NETHER_STAR, 1));
	plugin = (CraftPlugin) Bukkit.getPluginManager().getPlugin("CraftPlugin");
	languages = plugin.getLanguages();
    }
    
    @Override
    public String getIdentifier() {
	return "NetherStar";
    }

    @Override
    public void activate() {	
	Properties config = languages.getLanguage(Locale.getDefault());
	
	ItemMeta meta = this.getResult().getItemMeta();
	meta.setDisplayName(config.getProperty("titleNetherstar", "Netherstar"));
	List<String> lore = new ArrayList<>();
	lore.add(config.getProperty("descrNetherstar", ""));
	meta.setLore(lore);
	this.getResult().setItemMeta(meta);
	
	ShapedRecipe recipe = this;
	recipe.shape("ABA", "BCB", "ABA");
	recipe.setIngredient('A', Material.AIR);
	recipe.setIngredient('B', Material.EMERALD);
	recipe.setIngredient('C', Material.DIAMOND);
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
