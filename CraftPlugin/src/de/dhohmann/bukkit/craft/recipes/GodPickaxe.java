package de.dhohmann.bukkit.craft.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.dhohmann.bukkit.core.Toggleable;
import de.dhohmann.bukkit.craft.CraftRecipe;
import de.dhohmann.bukkit.craft.listeners.GodLike;

/**
 * Recipe for a godlike pick axe that will destroy a block by hitting it once.
 * @author dhohmann
 *
 */
public class GodPickaxe extends CraftRecipe implements Toggleable, GodLike {

    private boolean enabled;
    private FileConfiguration language;
    private int mode;

    /**
     * Creates a godlike pick axe recipe
     * @param lang Language model used for the description
     * @param mode Mode of the god like behaviour
     */
    public GodPickaxe(FileConfiguration lang, int mode) {
	super(new ItemStack(Material.DIAMOND_PICKAXE, 1));
	language = lang;
	enabled = false;
	this.mode = mode;
    }

    @Override
    public void enable() {
	if (!enabled) {
	    enabled = true;

	    ItemMeta meta = getProtectedResult().getItemMeta();
	    meta.setDisplayName(language.getString("recipes.godpickaxe.name", "GodPickaxe"));
	    List<String> lore = new ArrayList<>();
	    lore.add(language.getString("recipe.godpickaxe.description", "Hit a block and break it instantly."));
	    meta.setLore(lore);

	    if (mode >= GodLike.NORMAL) {
		meta.addEnchant(Enchantment.DIG_SPEED, 100, true);
	    }

	    if (mode >= GodLike.DEMIGOD) {
		meta.addEnchant(Enchantment.LUCK, 100, true);
		meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 100, true);
	    }

	    if (mode >= GodLike.GODLIKE) {
		meta.addEnchant(Enchantment.SILK_TOUCH, 100, true);
	    }

	    getProtectedResult().setItemMeta(meta);

	    CraftRecipe recipe = this;
	    recipe.shape("AAA", "ABA", "ABA");
	    recipe.setIngredient('A', Material.DIAMOND);
	    recipe.setIngredient('B', Material.DIAMOND_PICKAXE);
	    Bukkit.addRecipe(recipe);
	}
    }

    @Override
    public void disable() {
	if (enabled) {
	    enabled = false;

	    Iterator<Recipe> iter = Bukkit.getRecipesFor(getProtectedResult()).iterator();
	    while (iter.hasNext()) {
		if (iter.next() instanceof GodPickaxe) {
		    iter.remove();
		}
	    }
	}
    }

    @Override
    public boolean isEnabled() {
	return enabled;
    }

    private boolean isCorrectItem(ItemStack stack) {
	if (mode == GodLike.NORMAL) {
	    return false;
	}
	
	boolean result = true;
	result &= (stack.getType() == getResult().getType());
	List<String> lore = stack.getItemMeta().getLore();
	if (lore.size() != getResult().getItemMeta().getLore().size())
	    result &= false;
	else {
	    for (int i = 0; i < lore.size(); i++) {
		if (!lore.get(i).equals(getResult().getItemMeta().getLore().get(i)))
		    result &= false;
	    }
	}
	if (lore.size() == 0) {
	    result &= (stack.getItemMeta().getDisplayName().equals(getResult().getItemMeta().getDisplayName()));
	}
	return result;
    }

    @Override
    public void onItemBreak(PlayerItemBreakEvent e) {
	if (isCorrectItem(e.getBrokenItem())) {
	    e.getBrokenItem().setAmount(1);
	}
    }

    @Override
    public void onItemUse(PlayerInteractEvent e) {
	if (isCorrectItem(e.getItem())) {
	    switch (e.getAction()) {
	    case LEFT_CLICK_BLOCK:
		Block b = e.getClickedBlock();
		if (!b.isLiquid() && !b.isEmpty()) {
		    if (mode == GodLike.GODLIKE) {
			b.breakNaturally();
		    } else if (mode == GodLike.DEMIGOD) {
			b.breakNaturally();
		    } else {
			b.breakNaturally(getResult());
		    }
		}
		break;
	    case RIGHT_CLICK_BLOCK:
		// e.getClickedBlock().setType(Material.DIAMOND_BLOCK);
		break;
	    default:
		// Do nothing
	    }
	}
    }

}
