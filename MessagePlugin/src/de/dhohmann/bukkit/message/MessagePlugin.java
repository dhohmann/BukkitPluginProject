package de.dhohmann.bukkit.message;

import org.bukkit.plugin.java.JavaPlugin;

import de.dhohmann.bukkit.message.listener.PlayerListener;

public class MessagePlugin extends JavaPlugin {
	/**
	 * Called when the plugin is enabled<br>
	 * Use this for reading config file, creating global objects etc.
	 */
	public void onEnable() {
		// TODO Fill
		loadConfig();
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	}

	/**
	 * Called when the plugin is disabled<br>
	 * Use this to free resources
	 */
	public void onDisable() {
		// TODO Fill
	};
	
	public void loadConfig(){
		saveDefaultConfig();
		getConfig().options().copyDefaults();
		saveConfig();
	}
}
