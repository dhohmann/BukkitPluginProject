package de.dhohmann.bukkit.craft.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import de.dhohmann.bukkit.core.Toggleable;
import de.dhohmann.bukkit.craft.CraftRecipe;

public class Nametag extends CraftRecipe implements Toggleable {

    private FileConfiguration language;
    private boolean enabled = false;

    public Nametag(FileConfiguration lang) {
	super(new ItemStack(Material.NAME_TAG, 2));
	language = lang;
    }

    @Override
    public boolean isEnabled() {
	return enabled;
    }

    @Override
    public void enable() {
	if (enabled)
	    return;
	enabled = true;

	ItemMeta meta = getProtectedResult().getItemMeta();
	meta.setDisplayName(language.getString("recipes.nametag.name", "Nametag"));
	List<String> lore = new ArrayList<>();
	lore.add(language.getString("recipe.nametag.description", ""));
	meta.setLore(lore);
	getProtectedResult().setItemMeta(meta);

	CraftRecipe recipe = this;
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
    public void disable() {
	if (!enabled)
	    return;
	enabled = false;

	Iterator<Recipe> iter = Bukkit.getRecipesFor(getProtectedResult()).iterator();
	while (iter.hasNext()) {
	    if (iter.next() instanceof Nametag) {
		iter.remove();
	    }
	}
    }

}
