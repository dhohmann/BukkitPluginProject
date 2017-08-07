package de.dhohmann.bukkit.craft;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.Recipe;

public class CommandAutocomplete implements TabCompleter {

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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
	List<String> result = null;

	if (label.equalsIgnoreCase("recipe")) {
	    try {
		if (args[0].equalsIgnoreCase(CraftPlugin.DISABLE) || args[0].equalsIgnoreCase(CraftPlugin.ENABLE)) {
		    if (args.length > 1) {
			result = getAvailableRecipes(args[1]);
		    } else {
			result = getAvailableRecipes(null);
		    }
		} else {
		    result = getAvailableRecipes(args[0]);
		}
	    } catch (ArrayIndexOutOfBoundsException e) {
		sender.sendMessage("Wrong argument number");
	    }
	}

	if (result == null)
	    result = new ArrayList<>();
	return result;
    }

}
