package de.dhohmann.bukkit.craft;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.dhohmann.bukkit.craft.nametag.NametagRecipe;
import de.dhohmann.bukkit.craft.netherstar.NetherstarRecipe;
import de.dhohmann.bukkit.craft.saddle.SaddleRecipe;

public class RecipeCraftPlugin extends JavaPlugin {

    private static final String ENABLED = "enabled";
    private static final String DISABLED = "disabled";
    private static final String UNDEFINED = "undefined";
    
    private FileConfiguration cconf;

    private Map<String, RecipeManager> recipes;

    @Override
    public void onEnable() {
	recipes = new HashMap<>();
	loadConfig();

	FileConfiguration config = getConfig();
	String param;

	// Nametag
	param = config.getString("recipes.nametag", UNDEFINED);
	if (param.equalsIgnoreCase(ENABLED)) {
	    Bukkit.getLogger().log(Level.INFO, "Adding nametag recipe.");
	    recipes.put(NametagRecipe.IDENTIFIER, new NametagRecipe());
	} else {
	    if (param.equalsIgnoreCase(UNDEFINED)) {
		Bukkit.getLogger().log(Level.INFO, "Parameter 'nametag' found: No custom recipe will be added");
	    }
	}

	// Netherstar
	param = config.getString("recipes.netherstar", UNDEFINED);
	if (param.equalsIgnoreCase(ENABLED)) {
	    Bukkit.getLogger().log(Level.INFO, "Adding netherstar recipe.");
	    recipes.put(NetherstarRecipe.IDENTIFIER, new NetherstarRecipe());
	} else {
	    if (param.equalsIgnoreCase(UNDEFINED)) {
		Bukkit.getLogger().log(Level.INFO, "Parameter 'netherstar' found: No custom recipe will be added");
	    }
	}

	// Saddle
	param = config.getString("recipes.saddle", UNDEFINED);
	if (param.equalsIgnoreCase(ENABLED)) {
	    Bukkit.getLogger().log(Level.INFO, "Adding saddle recipe.");
	    recipes.put(SaddleRecipe.IDENTIFIER, new SaddleRecipe());
	} else {
	    if (param.equalsIgnoreCase(UNDEFINED)) {
		Bukkit.getLogger().log(Level.INFO, "Parameter 'saddle' found: No custom recipe will be added");
	    }
	}
	
	// Enabling loaded recipes
	Iterator<String> iter = recipes.keySet().iterator();
	while(iter.hasNext()){
	    String key = iter.next();
	    recipes.get(key).onEnable();
	}
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	
	// TODO Planned Commands
	/*
	 * show all recipes (recipe list)
	 * show specific recipe (recipe <name>
	 * recipe-admin enable <name>
	 * recipe-admin disable <name>
	 */
	
	return true;
    }

    public void loadConfig() {
	saveDefaultConfig();
	getConfig().options().copyDefaults();
	saveConfig();
	cconf = getConfig();
    }

    @Override
    public void onDisable() {
	// Disabling loaded recipes
	Iterator<String> iter = recipes.keySet().iterator();
	while(iter.hasNext()){
	    String key = iter.next();
	    recipes.get(key).onEnable();
	}
    }
    
}
