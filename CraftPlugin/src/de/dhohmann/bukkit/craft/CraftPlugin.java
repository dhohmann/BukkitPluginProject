package de.dhohmann.bukkit.craft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;

import de.dhohmann.bukkit.core.Toggleable;
import de.dhohmann.bukkit.craft.recipes.GodAxe;
import de.dhohmann.bukkit.craft.recipes.GodPickaxe;
import de.dhohmann.bukkit.craft.recipes.GodShovel;
import de.dhohmann.bukkit.craft.recipes.Nametag;
import de.dhohmann.bukkit.craft.recipes.Netherstar;
import de.dhohmann.bukkit.craft.recipes.Saddle;
import de.dhohmann.bukkit.plugin.DJavaPlugin;

public class CraftPlugin extends DJavaPlugin {

    private List<Recipe> recipes;
    private String language;

    public static final String ENABLE = "enable";
    public static final String DISABLE = "disable";

    @Override
    public void onEnable() {
	try {
	    registerLanguageFolder("languages");
	} catch (InvalidConfigurationException | IOException e) {
	    Bukkit.getLogger().log(Level.SEVERE, "Could not load languages");
	}
	loadConfig();
	getLanguage(Locale.getDefault());
	recipes = new ArrayList<>();

	// Nametag
	if (config.getBoolean("recipes.nametag")) {
	    Bukkit.getLogger().log(Level.INFO, "Adding nametag recipe");
	    System.out.println("\n\n\n" + new Nametag(language) + "\n\n\n");
	    recipes.add(new Nametag(language));
	}

	// Netherstar
	if (config.getBoolean("recipes.netherstar")) {
	    Bukkit.getLogger().log(Level.INFO, "Adding netherstar recipe");
	    recipes.add(new Netherstar(language));
	}

	// Saddle
	if (config.getBoolean("recipes.saddle")) {
	    Bukkit.getLogger().log(Level.INFO, "Adding saddle recipe");
	    recipes.add(new Saddle(language));
	}

	// GodPickAxe
	if (config.getBoolean("recipes.godpickaxe")) {
	    Bukkit.getLogger().log(Level.INFO, "Adding godpickaxe recipe");
	    recipes.add(new GodPickaxe(language, config.getInt("godlike.godpickaxe", 0)));
	}

	// GodAxe
	if (config.getBoolean("recipes.godaxe")) {
	    Bukkit.getLogger().log(Level.INFO, "Adding godaxe recipe");
	    recipes.add(new GodAxe(language, config.getInt("godlike.godaxe", 0)));
	}
	// GodPickAxe
	if (config.getBoolean("recipes.godshovel")) {
	    Bukkit.getLogger().log(Level.INFO, "Adding godshovel recipe");
	    recipes.add(new GodShovel(language, config.getInt("godlike.godshovel", 0)));
	}

	// Listeners
	for (Recipe r : recipes) {
	    if (r instanceof Listener) {
		Bukkit.getPluginManager().registerEvents((Listener) r, this);
	    }
	}

	// Autocomplete
	getCommand("recipe").setTabCompleter(new CommandAutocomplete());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	try {
	    if (label.equals("recipe")) {
		if (args[0].equals(ENABLE) || args[0].equals(DISABLE)) {
		    if (!sender.hasPermission("recipe.statechange")) {
			sender.sendMessage(command.getPermissionMessage());
			return true;
		    }
		    if (setRecipeState(args[1], args[0])) {
			sender.sendMessage(language.getString("commands.statechange.success", "State of the recipe changed"));
		    } else {
			sender.sendMessage(language.getString("commands.statechange.failure", "Error while changing recipe state"));
		    }
		    return true;
		}

		if (!(sender instanceof Player)) {
		    sender.sendMessage(language.getString("commands.noserver", "Command can only be executed by players"));
		    return true;
		}

		String recipeName = args[0];
		Iterator<Recipe> iter = Bukkit.recipeIterator();
		while (iter.hasNext()) {
		    Recipe r = iter.next();
		    if (r.getResult().getItemMeta().getDisplayName().equals(recipeName)) {
			((Player) sender).openInventory(RecipeInventory.buildRecipeInventory(r));
			break;
		    }
		}
		sender.sendMessage(language.getString("commands.showrecipe.notfound", "Could not found recipe"));
		return true;
	    }
	    if (label.equals("recipelist")) {
		List<String> recipes = getAvailableRecipes(null);
		sender.sendMessage(language.getString("commands.listrecipes.title", "Available recipes"));
		sender.sendMessage("### BEGIN");
		sender.sendMessage(recipes.toArray(new String[recipes.size()]));
		sender.sendMessage("### END");
	    }

	    return false;
	} catch (ArrayIndexOutOfBoundsException e) {
	    sender.sendMessage(language.getString("commands.wrongargnumber", "Wrong argument number"));
	    return true;
	}
    }

    private List<String> getAvailableRecipes(String prefix) {
	Iterator<Recipe> iterator = Bukkit.recipeIterator();
	List<String> recipes = new ArrayList<>();
	String start = (prefix == null ? "" : prefix);

	while (iterator.hasNext()) {
	    Recipe r = iterator.next();
	    String recipeName = r.getResult().getItemMeta().getDisplayName();
	    if (recipeName.startsWith(start) && !recipes.contains(recipeName)) {
		recipes.add(r.getResult().getItemMeta().getDisplayName());
	    }
	}
	return recipes;
    }

    public boolean setRecipeState(String recipeName, String state) {
	Recipe recipe = null;
	for (Recipe r : recipes) {
	    if (r.getResult().getItemMeta().getDisplayName().equals(recipeName)) {
		recipe = r;
	    }
	}
	if (recipe != null) {
	    switch (state) {
	    case ENABLE:
		if (recipe instanceof Toggleable) {
		    ((Toggleable) recipe).enable();
		    return ((Toggleable) recipe).isEnabled();
		}
		break;
	    case DISABLE:
		if (recipe instanceof Toggleable) {
		    ((Toggleable) recipe).disable();
		    return !((Toggleable) recipe).isEnabled();
		}
		break;
	    }
	}
	return false;
    }

    @Override
    public void onDisable() {
	super.onDisable();
	for (Recipe r : recipes) {
	    if (r instanceof Toggleable) {
		((Toggleable) r).disable();
	    }
	}
	HandlerList.unregisterAll(this);
    }

}
