package de.dhohmann.bukkit.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import de.dhohmann.bukkit.core.IToggleable;

public abstract class CustomJavaPlugin extends JavaPlugin implements IToggleable {

    @Override
    public void onEnable() {
	if(hasConfig()) loadConfig();
        activate();
    }
    
    @Override
    public void onDisable() {
        deactivate();
    }
    
    public abstract boolean hasConfig();
    
    /**
     * Loads the configuration file if it exists
     */
    private void loadConfig(){
	saveDefaultConfig();
	getConfig().options().copyDefaults();
	saveConfig();
    }
    
}
