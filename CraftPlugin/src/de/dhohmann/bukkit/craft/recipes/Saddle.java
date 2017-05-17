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

public class Saddle extends CustomShapedRecipe {

    private CraftPlugin plugin;
    private LanguageSelector languages;

    /**
     * Constructs a recipe with a saddle as the result
     */
    public Saddle() {
	super(new ItemStack(Material.SADDLE, 1));
	plugin = (CraftPlugin) Bukkit.getPluginManager().getPlugin("CraftPlugin");
	languages = plugin.getLanguages();
    }

    @Override
    public String getIdentifier() {
	return "Saddle";
    }

    @Override
    public void activate() {
	/* Set item metadata */
	Properties config = languages.getLanguage(Locale.getDefault());
	ItemMeta meta = this.getResult().getItemMeta();
	meta.setDisplayName(config.getProperty("titleSaddle", "Saddle"));
	List<String> lore = new ArrayList<>();
	lore.add(config.getProperty("descrSaddle", ""));
	meta.setLore(lore);
	this.getResult().setItemMeta(meta);
	
	/* Set shape */
	ShapedRecipe recipe = this;
	recipe.shape("AAA", "A A", "B B");
	recipe.setIngredient('A', Material.LEATHER);
	recipe.setIngredient('B', Material.IRON_INGOT);
	Bukkit.addRecipe(recipe);
    }

    @Override
    public void deactivate() {
	Iterator<Recipe> iter = Bukkit.recipeIterator();
	while (iter.hasNext()) {
	    Recipe r = iter.next();
	    if (r instanceof CustomShapedRecipe) {
		if (((CustomShapedRecipe) r).getIdentifier().equals(this.getIdentifier())) {
		    iter.remove();
		}
	    }
	}
    }
}
