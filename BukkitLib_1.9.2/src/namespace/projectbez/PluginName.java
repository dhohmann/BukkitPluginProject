package namespace.projectbez;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginName extends JavaPlugin {
	/**
	 * Called when the plugin is enabled<br>
	 * Use this for reading config file, creating global objects etc.
	 */
	public void onEnable() {
		// TODO Fill
	}

	/**
	 * Called when the plugin is disabled<br>
	 * Use this to free resources
	 */
	public void onDisable() {
		// TODO Fill
	};

	/**
	 * Called when you have declared a command in your plugin.yml file<br>
	 * Use this to react to the declared commands
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		//Return true if the command execution was successful
		//Return false if the command is not correctly used(will print the defined description from plugin.yml)
		return false;
	}
}
