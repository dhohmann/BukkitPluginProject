package de.dhohmann.bukkit.craft.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.dhohmann.bukkit.core.Toggleable;
import de.dhohmann.bukkit.craft.CraftRecipe;

public class Saddle extends CraftRecipe implements Toggleable {

    private FileConfiguration language;
    private boolean enabled = false;

    /**
     * Constructs a recipe with a saddle as the result
     */
    public Saddle(FileConfiguration lang) {
	super(new ItemStack(Material.SADDLE, 1));
	language = lang;
    }

    @Override
    public void enable() {
	/* Set item metadata */
	ItemMeta meta = getProtectedResult().getItemMeta();
	meta.setDisplayName(language.getString("recipes.saddle.name", "Saddle"));
	List<String> lore = new ArrayList<>();
	lore.add(language.getString("recipes.saddle.description", ""));
	meta.setLore(lore);
	getProtectedResult().setItemMeta(meta);

	/* Set shape */
	CraftRecipe recipe = this;
	recipe.shape("AAA", "A A", "B B");
	recipe.setIngredient('A', Material.LEATHER);
	recipe.setIngredient('B', Material.IRON_INGOT);
	Bukkit.addRecipe(recipe);
    }

    @Override
    public void disable() {
	if (!enabled)
	    return;
	enabled = false;

	Iterator<Recipe> iter = Bukkit.getRecipesFor(getProtectedResult()).iterator();
	while (iter.hasNext()) {
	    Recipe r = iter.next();
	    if (r instanceof Saddle) {
		iter.remove();
	    }
	}
    }

    @Override
    public boolean isEnabled() {
	return enabled;
    }
}
