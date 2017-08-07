package de.dhohmann.bukkit.inventory;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import de.dhohmann.bukkit.core.Toggleable;

/**
 * Represents a custom recipe
 * @version 1.0
 * @author dhohmann
 *
 */
public abstract class DShapedRecipe extends ShapedRecipe implements Toggleable {

    /**
     * The result of the shaped recipe
     */
    protected ItemStack result;

    /**
     * Creates a shaped recipe using the given item stack
     * @param result - The result of the recipe
     */
    public DShapedRecipe(ItemStack result) {
	super(result);
	enable();
    }

    /**
     * Parses the recipe in an inventory
     * @return Inventory representation of the recipe
     */
    public Inventory toInventory() {
	CraftingInventory inv = (CraftingInventory) Bukkit.createInventory(null, InventoryType.WORKBENCH);
	String[] shape = this.getShape();
	Map<Character, ItemStack> ingredients = this.getIngredientMap();
	ItemStack[] craftingField = new ItemStack[inv.getMatrix().length];
	for (int i = 0; i < shape.length; i++) {
	    for (int j = 0; j < shape[i].length(); i++) {
		craftingField[i * 3 + j] = ingredients.get(shape[i].charAt(j));
	    }
	}
	inv.setResult(result);
	inv.setContents(craftingField);
	return inv;
    }

}
