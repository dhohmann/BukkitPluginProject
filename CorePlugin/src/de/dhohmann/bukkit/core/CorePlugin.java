package de.dhohmann.bukkit.core;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import de.dhohmann.bukkit.plugin.CustomJavaPlugin;
import de.dhohmann.bukkit.util.MessageFormatter;

public class CorePlugin extends CustomJavaPlugin {
    @Override
    public void activate() {
	Core.getJoinMessenger().registerMessage(this, MessageFormatter.formatColorCode(getConfig().getString("message.welcome", null)));
    }

    @Override
    public void deactivate() {
	Core.getJoinMessenger().removeMessages(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

	if (label.equals("pluginreload")) {
	    if (args.length > 1) {
		Plugin p = Bukkit.getPluginManager().getPlugin(args[1]);
		if (p instanceof CustomJavaPlugin) {
		    ((CustomJavaPlugin) p).activate();
		    ((CustomJavaPlugin) p).deactivate();
		    return true;
		}
	    }
	    return false;
	}
	return super.onCommand(sender, command, label, args);
    }

    @Override
    public boolean hasConfig() {
	return false;
    }

}
