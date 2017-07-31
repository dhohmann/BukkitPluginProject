package de.dhohmann.bukkit.craft;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import de.dhohmann.bukkit.craft.recipes.Nametag;
import de.dhohmann.bukkit.craft.recipes.Netherstar;
import de.dhohmann.bukkit.craft.recipes.Saddle;
import de.dhohmann.bukkit.inventory.CustomShapedRecipe;
import de.dhohmann.bukkit.plugin.CustomJavaPlugin;
import de.dhohmann.bukkit.util.LanguageSelector;

public class CraftPlugin extends CustomJavaPlugin {

    private LanguageSelector languages;

    public LanguageSelector getLanguages() {
	if (languages == null) {
	    File langFolder = new File(getDataFolder(), "language");
	    if (!langFolder.exists()) {
		langFolder.mkdirs();
	    }
	    languages = LanguageSelector.createSelector(langFolder);
	}
	return languages;
    }

    /**
     * Creates the data folder if needed and copies everything from the resources folder to it.
     */
    private void createDataFolder() {
	File dataFolder = getDataFolder();
	if (!dataFolder.exists()) {
	    Bukkit.getLogger().log(Level.INFO, "Creating datafolder " + dataFolder.getName());
	    dataFolder.mkdirs();
	}
	try {
	    JarInputStream jar = new JarInputStream(getClass().getProtectionDomain().getCodeSource().getLocation().openStream());
	    JarEntry entry = null;
	    while ((entry = jar.getNextJarEntry()) != null) {
		if (entry.getName().contains("resources")) {
		    File dest = new File(dataFolder, entry.getName().substring(entry.getName().indexOf("/") + 1, entry.getName().length()));
		    if (!dest.exists()) {
			dest.getParentFile().mkdirs();
			if (dest.createNewFile()) {
			    Bukkit.getLogger().log(Level.INFO, "Copying resource " + dest.getName() + " from jar");
			    OutputStream os = new FileOutputStream(dest);
			    byte[] buffer = new byte[256];
			    int length = -1;
			    while ((length = jar.read(buffer)) != -1) {
				os.write(buffer, 0, length);
			    }
			    os.close();
			}
		    }
		}
	    }
	    jar.close();
	} catch (Exception e) {
	    Bukkit.getLogger().log(Level.SEVERE, "Something went wrong while creating data folder", e);
	}
    }

    private List<CustomShapedRecipe> recipes;

    @Override
    public boolean hasConfig() {
	return true;
    }

    @Override
    public void activate() {
	createDataFolder();
	recipes = new ArrayList<>();

	FileConfiguration config = getConfig();
	// Nametag
	if (config.getBoolean("recipes.nametag")) {
	    Bukkit.getLogger().log(Level.INFO, "Adding nametag recipe.");
	    recipes.add(new Nametag());
	}

	// Netherstar
	if (config.getBoolean("recipes.netherstar")) {
	    Bukkit.getLogger().log(Level.INFO, "Adding netherstar recipe.");
	    recipes.add(new Netherstar());
	}

	// Saddle
	if (config.getBoolean("recipes.saddle")) {
	    Bukkit.getLogger().log(Level.INFO, "Adding saddle recipe.");
	    recipes.add(new Saddle());
	}

	// Enabling loaded recipes
	Iterator<CustomShapedRecipe> iter = recipes.iterator();
	while (iter.hasNext()) {
	    CustomShapedRecipe recipe = iter.next();
	    recipe.activate();
	}
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	if (label.equals("recipes")) {
	    StringBuilder builder = new StringBuilder("Available Recipes\n");
	    builder.append("************\n");
	    for (CustomShapedRecipe r : recipes) {
		builder.append("\t - ");
		builder.append(r.getResult().getItemMeta().getDisplayName());
		builder.append("\n");
	    }
	    builder.append("************\n");
	    sender.sendMessage(builder.toString());
	} else if (label.equals("recipe")) {
	    if (args.length >= 1) {
		String name = args[0];
		CustomShapedRecipe recipe = null;
		for (CustomShapedRecipe r : recipes) {
		    if(r.getIdentifier().equalsIgnoreCase(name)){
			recipe = r;
		    }
		}
		if(sender instanceof Player){
		    ((Player)sender).openInventory(recipe.getRecipe());
		}
	    } else {
		return false;
	    }
	}

	return true;
    }

    @Override
    public void deactivate() {
	Iterator<CustomShapedRecipe> iter = recipes.iterator();
	while (iter.hasNext()) {
	    CustomShapedRecipe recipe = iter.next();
	    recipe.deactivate();
	}
    }

}
