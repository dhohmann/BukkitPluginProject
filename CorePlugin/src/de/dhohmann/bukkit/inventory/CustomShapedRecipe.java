package de.dhohmann.bukkit.inventory;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import de.dhohmann.bukkit.core.IToggleable;

public abstract class CustomShapedRecipe extends ShapedRecipe implements IToggleable {
    
    private ItemStack result;
    
    /**
     * Constructs a shaped recipe using the defined item stack
     * @param result
     */
    protected CustomShapedRecipe(ItemStack result) {
	super(result);
	this.result = result;
    }
    
    /**
     * Can be used to identify the recipe for access
     * @return Identifier
     */
    public abstract String getIdentifier();
    
    /**
     * Can be used to display the recipe to the player
     * @return Recipe inside an inventory
     */
    public Inventory getRecipe(){
	CraftingInventory inv = (CraftingInventory) Bukkit.createInventory(null, InventoryType.CRAFTING);
	
	String[] shape = this.getShape();
	Map<Character, ItemStack> ingredients = this.getIngredientMap();
	ItemStack[] craftingField = new ItemStack[inv.getMatrix().length];
	
	for(int i=0; i<shape.length; i++){
	    for(int j = 0; j<shape[i].length(); i++){
		craftingField[i * 3 + j] = ingredients.get(shape[i].charAt(j));
	    }
	}
	
	inv.setResult(this.getResult());
	inv.setContents(craftingField);
	return inv;
    }
    
    @Override
    public ItemStack getResult() {
	return result;
    }
    
    public ItemStack getSuperResult(){
	return super.getResult();
    }
    
}
