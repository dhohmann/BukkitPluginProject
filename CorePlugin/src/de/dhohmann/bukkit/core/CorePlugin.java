package de.dhohmann.bukkit.core;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Enables various basic features for plugin development
 * 
 * @version 2.0
 * @author dhohmann
 *
 */
public class CorePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	if (label.equals("reloadplugin")) {
	    if (sender.hasPermission("core.reloadplugin")) {
		if (args.length >= 1) {
		    Plugin plugin = Bukkit.getPluginManager().getPlugin(args[1]);
		    if (plugin == null) {
			sender.sendMessage("Plugin " + args[1] + " does not exist");
		    } else {
			Bukkit.getPluginManager().disablePlugin(plugin);
			Bukkit.getPluginManager().enablePlugin(plugin);
		    }
		} else {
		    sender.sendMessage("Wrong parameter count");
		    return false;
		}
	    } else {
		sender.sendMessage(this.getCommand(label).getPermissionMessage());
	    }
	}
	if(label.equalsIgnoreCase("commandlist")){
	    // Show all available commands on the server
	}
	return super.onCommand(sender, command, label, args);
    }

}
