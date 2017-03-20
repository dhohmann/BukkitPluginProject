package de.dhohmann.bukkit.craft;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CustomRecipe extends ShapedRecipe {
    
    /**
     * Defines the name of the custom recipe. Can be used for identifying different custom recipes.
     */
    private String name;
    
    /**
     * Constructs a shaped recipe using the given {@link org.bukkit.inventory.ItemStack ItemStack}.
     * @param stack item stack of the custom recipe, defines the shape
     * @param name name of the custom recipe, used for identification
     */
    public CustomRecipe(ItemStack stack, String name) {
	super(stack);
	this.name = name;
    }
    
    /**
     * Getter for the name property
     * @return name of the custom recipe
     */
    public String getName(){
	return name;
    }

    
}
