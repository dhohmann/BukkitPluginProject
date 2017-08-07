package de.dhohmann.bukkit.craft;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeInventory {

    /**
     * Constructs an inventory based an the recipe
     * @param recipe The recipe that should be parsed
     * @return Empty chest inventory if the recipe type cannot be found, the relevant inventory
     *         otherwise
     */
    public static Inventory buildRecipeInventory(Recipe recipe) {
	if (recipe instanceof ShapedRecipe || recipe instanceof ShapelessRecipe) {
	    return buildWorkbenchRecipe(recipe);
	}
	if (recipe instanceof FurnaceRecipe) {
	    return buildFurnaceRecipe(recipe);
	}
	if (recipe instanceof CraftRecipe) {
	    return buildWorkbenchRecipe(recipe);
	}
	return Bukkit.createInventory(null, InventoryType.CHEST, "ERROR: recipe type does not match");
    }

    /**
     * Parses a recipe to a player's crafting inventory
     * @param recipe The recipe that should be parsed
     * @return Crafting inventory with 4 crafting slots and one result slot
     */
    public static Inventory buildCraftingRecipe(Recipe recipe) {
	CraftingInventory inventory = (CraftingInventory) Bukkit.createInventory(null, InventoryType.CRAFTING,
		recipe.getResult().getItemMeta().getDisplayName());
	inventory.setResult(recipe.getResult());
	if (recipe instanceof ShapedRecipe) {
	    ShapedRecipe r = (ShapedRecipe) recipe;
	    ItemStack[] matrix = new ItemStack[4];
	    String[] shape = r.getShape();
	    Map<Character, ItemStack> ingredients = r.getIngredientMap();
	    for (int i = 0; i < shape.length; i++) {
		for (int j = 0; j < shape[i].length(); i++) {
		    ItemStack item = ingredients.get(shape[i].charAt(j));
		    if (item != null)
			matrix[i * 2 + j] = item;
		}
	    }
	} else if (recipe instanceof ShapelessRecipe) {
	    ShapelessRecipe r = (ShapelessRecipe) recipe;
	    inventory.setMatrix(r.getIngredientList().toArray(new ItemStack[r.getIngredientList().size()]));
	}
	return inventory;
    }

    /**
     * Parses a recipe to a workbench crafting inventory
     * @param recipe The recipe that should be parsed
     * @return Crafting inventory with 9 crafting slots and one result slot
     */
    public static Inventory buildWorkbenchRecipe(Recipe recipe) {
	CraftingInventory inventory = (CraftingInventory) Bukkit.createInventory(null, InventoryType.WORKBENCH,
		recipe.getResult().getItemMeta().getDisplayName());
	inventory.setResult(recipe.getResult());
	if (recipe instanceof ShapedRecipe) {
	    ShapedRecipe r = (ShapedRecipe) recipe;
	    ItemStack[] matrix = new ItemStack[9];
	    String[] shape = r.getShape();
	    Map<Character, ItemStack> ingredients = r.getIngredientMap();
	    for (int i = 0; i < shape.length; i++) {
		for (int j = 0; j < shape[i].length(); i++) {
		    ItemStack item = ingredients.get(shape[i].charAt(j));
		    if (item != null)
			matrix[i * 3 + j] = item;
		}
	    }
	} else if (recipe instanceof ShapelessRecipe) {
	    ShapelessRecipe r = (ShapelessRecipe) recipe;
	    inventory.setMatrix(r.getIngredientList().toArray(new ItemStack[r.getIngredientList().size()]));
	}
	return inventory;
    }

    /**
     * Parses a recipe to a furnace inventory
     * @param recipe The recipe that should be parsed
     * @return Crafting inventory with one input slot, one empty fuel slot and one result slot
     */
    public static Inventory buildFurnaceRecipe(Recipe recipe) {
	FurnaceInventory inventory = (FurnaceInventory) Bukkit.createInventory(null, InventoryType.FURNACE,
		recipe.getResult().getItemMeta().getDisplayName());
	inventory.setResult(recipe.getResult());
	if (recipe instanceof FurnaceRecipe) {
	    FurnaceRecipe r = (FurnaceRecipe) recipe;
	    inventory.setSmelting(r.getInput());
	}
	return inventory;
    }

}
