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

public class Netherstar extends CraftRecipe implements Toggleable {
    
    private FileConfiguration language;
    private boolean enabled = false;
    
    public Netherstar(FileConfiguration lang) {
	super(new ItemStack(Material.NETHER_STAR, 1));
	language = lang;
    }
    

    @Override
    public void enable() {
	if(enabled) return;
	enabled = true;
	
	ItemMeta meta = getProtectedResult().getItemMeta();
	meta.setDisplayName(language.getString("recipes.netherstar.name", "Netherstar"));
	List<String> lore = new ArrayList<>();
	lore.add(language.getString("recipes.netherstar.description", ""));
	meta.setLore(lore);
	getProtectedResult().setItemMeta(meta);
	
	CraftRecipe recipe = this;
	recipe.shape("ABA", "BCB", "ABA");
	recipe.setIngredient('A', Material.AIR);
	recipe.setIngredient('B', Material.EMERALD);
	recipe.setIngredient('C', Material.DIAMOND);
	Bukkit.addRecipe(recipe);
    }

    @Override
    public void disable() {
	if(!enabled) return;
	enabled = false;
	
	Iterator<Recipe> iter = Bukkit.getRecipesFor(getProtectedResult()).iterator();
	while(iter.hasNext()){
	    if(iter.next() instanceof Nametag){
		iter.remove();
	    }
	}
    }

    @Override
    public boolean isEnabled() {
	return enabled;
    }

}
