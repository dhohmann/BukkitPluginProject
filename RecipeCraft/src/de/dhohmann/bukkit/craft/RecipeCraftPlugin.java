package de.dhohmann.bukkit.craft;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class RecipeCraftPlugin extends JavaPlugin {
	private Map<String, RecipeManager> recipes;

	@Override
	public void onEnable() {
		recipes = new HashMap<>();
		loadConfig();
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO add commands for all
		return true;
	}
	
	public void loadConfig(){
		saveDefaultConfig();
		getConfig().options().copyDefaults();
		saveConfig();
	}
}
