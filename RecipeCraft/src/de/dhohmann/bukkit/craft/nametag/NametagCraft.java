package de.dhohmann.bukkit.craft.nametag;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.Wool;

import de.dhohmann.bukkit.craft.RecipeManager;

public class NametagCraft implements RecipeManager {
	@Override
	public void onEnable() {
		ItemStack nametag = new ItemStack(Material.NAME_TAG);
		ShapedRecipe _recipe = new NameTagRecipe(nametag);
		_recipe.shape("AAB", "ACA", "AAA");
		_recipe.setIngredient('A', Material.STICK);
		_recipe.setIngredient('B', Material.STRING);
		_recipe.setIngredient('C', new Wool(DyeColor.BLACK));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.BLUE));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.BROWN));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.CYAN));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.GRAY));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.GREEN));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.LIGHT_BLUE));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.LIME));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.MAGENTA));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.ORANGE));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.PINK));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.PURPLE));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.RED));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.SILVER));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.WHITE));
		Bukkit.addRecipe(_recipe);
		_recipe.setIngredient('C', new Wool(DyeColor.YELLOW));
		Bukkit.addRecipe(_recipe);
	}

	@Override
	public void onDisable() {
	    Iterator<Recipe> iter = Bukkit.recipeIterator();
	    while(iter.hasNext()){
		Recipe r = iter.next();
		if(r instanceof NameTagRecipe){
		    System.out.println("Nametag recipe");
		}
	    }
	    
	}
}
