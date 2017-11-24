package de.dhohmann.bukkit.info;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 * Represents the autocomplete for the InformationPlugin
 * 
 * @version 0.0.1
 * @since 2.0
 * @author hohmann
 *
 */
public class CommandAutocomplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
	List<String> result = new ArrayList<>();
	if (label.equalsIgnoreCase("infoplugin")) {
	    if (args.length == 0) {
		result.add("show");
		result.add("hide");
	    } else if (args.length == 1) {
		if (args[0].startsWith("s")) {
		    result.add("show");
		} else if (args[0].startsWith("h")) {
		    result.add("hide");
		}
	    }
	}
	return result;
    }

}
