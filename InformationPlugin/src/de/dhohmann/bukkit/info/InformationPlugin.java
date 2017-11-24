package de.dhohmann.bukkit.info;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import de.dhohmann.bukkit.plugin.CommandPrinter;
import de.dhohmann.bukkit.plugin.DJavaPlugin;
import de.dhohmann.bukkit.util.StringFormatter;

/**
 * Represents the entry point for the InformationPlugin
 * 
 * @version 0.1.0
 * @since 1.0
 * @author hohmann
 *
 */
public class InformationPlugin extends DJavaPlugin {
    private int task;
    private boolean runningTask = false;

    private List<Player> users;

    private String language;
    private final String userConfigName = "users.yml";
    private final String scoreboardConfigName = "scoreboard.yml";
    private FileConfiguration userConfig;
    private FileConfiguration scoreboardConfig;
    private File userConfigFile;
    private File scoreboardConfigFile;

    public void onEnable() {
	users = new ArrayList<>();

	// Configurations
	userConfigFile = new File(getDataFolder(), userConfigName);
	scoreboardConfigFile = new File(getDataFolder(), scoreboardConfigName);
	userConfig = new YamlConfiguration();
	scoreboardConfig = new YamlConfiguration();

	if (!userConfigFile.exists()) {
	    userConfigFile.getParentFile().mkdirs();
	    copy(getResource(userConfigName), userConfigFile);
	}
	if (!scoreboardConfigFile.exists()) {
	    scoreboardConfigFile.getParentFile().mkdirs();
	    copy(getResource(scoreboardConfigName), scoreboardConfigFile);
	}

	loadYamls();

	language = getConfig().getString("defaultLanguage", "DE");

	// Startup option
	if (getConfig().getBoolean("playerjoin.showcommands", false)) {
	    CommandPrinter.unregisterCommands(this);
	}

	// Scoreboard option
	if (getConfig().getBoolean("scoreboard.enabled", false)) {
	    task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

		@Override
		public void run() {
		    updateScoreboard();
		}

	    }, 0, 20 * getConfig().getInt("scoreboard.updatetime", 1));
	    runningTask = true;
	} else {
	    Bukkit.getLogger().info("Scoreboard not supported");
	}
    }

    private void readLastUsers() {
	List<String> names = userConfig.getStringList("users");
	for (String name : names) {
	    Player player = Bukkit.getPlayer(UUID.fromString(name));
	    if (player != null) {
		users.add(player);
	    }
	}
    }

    public void onDisable() {

	if (runningTask){
	    Bukkit.getScheduler().cancelTask(task);
	}

	List<String> useruuids = new ArrayList<>();
	for (Player p : users) {
	    useruuids.add(p.getUniqueId().toString());
	}
	for (String s : userConfig.getStringList("users")) {
	    if (!useruuids.contains(s)) {
		useruuids.add(s);
	    }
	}
	userConfig.set("users", useruuids);
	saveYamls();
    }

    private void saveYamls() {
	try {
	    saveConfig();
	    userConfig.save(userConfigFile);
	    scoreboardConfig.save(scoreboardConfigFile);
	} catch (IOException e) {
	    Bukkit.getLogger().log(Level.SEVERE, "Error during yaml saving", e);
	}
    }

    private void loadYamls() {
	try {
	    loadConfig();
	    userConfig.load(userConfigFile);
	    scoreboardConfig.load(scoreboardConfigFile);
	} catch (Exception e) {
	    Bukkit.getLogger().log(Level.SEVERE, "Error during yaml loading", e);
	}
    }

    private void updateScoreboard() {
	for (Player p : users) {
	    if (p.isOnline()) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("information", "");
		obj.setDisplayName(scoreboardConfig.getString("title", "Information"));
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		int scoreLine = 0;
		List<String> lines = scoreboardConfig.getStringList("lines");
		for (String s : lines) {

		    s = StringFormatter.formatPlaceholder(s, p);
		    s = StringFormatter.formatPlaceholder(s, p.getWorld());
		    s = StringFormatter.formatPlaceholder(s, p.getServer());
		    s = StringFormatter.formatPlaceholder(s);
		    s = StringFormatter.formatColorCode(s);

		    Score score = obj.getScore(s);
		    score.setScore(lines.size() - (scoreLine++));
		}
		p.setScoreboard(board);
	    } else {
		users.remove(p);
	    }
	}
    }

    @EventHandler
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	if (label.equalsIgnoreCase("infoplugin")) {
	    if (sender instanceof Player) {
		try {
		    if (args[0].equalsIgnoreCase("show")) {
			if (!users.contains(sender)) {
			    users.add((Player) sender);
			    System.out.println("==== > " + users.toString());
			    return true;
			}
		    } else if (args[0].equalsIgnoreCase("hide")) {
			if (users.contains(sender)) {
			    users.remove(sender);
			    System.out.println("==== > " + users.toString());
			    return true;
			}
		    }
		} catch (ArrayIndexOutOfBoundsException e) {
		    Bukkit.getLogger().log(Level.WARNING, e.getMessage());
		}
	    }
	}
	return false;
    }

}
