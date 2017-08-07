package de.dhohmann.bukkit.info;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
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

import de.dhohmann.bukkit.plugin.DJavaPlugin;
import de.dhohmann.bukkit.util.JoinMessenger;
import de.dhohmann.bukkit.util.StringFormatter;

public class InformationPlugin extends DJavaPlugin {
    private int task;

    private List<Player> users;

    private FileConfiguration language;
    private FileConfiguration userConfig;
    private FileConfiguration scoreboardConfig;
    private File userConfigFile;
    private File scoreboardConfigFile;

    public void onEnable() {
	super.onEnable();

	users = new ArrayList<>();

	// Configuration files
	userConfigFile = new File(getDataFolder(), "users.yml");
	scoreboardConfigFile = new File(getDataFolder(), "scoreboard.yml");

	// Configurations
	userConfig = new YamlConfiguration();
	scoreboardConfig = new YamlConfiguration();
	language = getLanguage(Locale.getDefault());

	if (!userConfigFile.exists()) {
	    userConfigFile.getParentFile().mkdirs();
	    copy(getResource("user.yml"), userConfigFile);
	}
	if (!scoreboardConfigFile.exists()) {
	    scoreboardConfigFile.getParentFile().mkdirs();
	    copy(getResource("scoreboard.yml"), scoreboardConfigFile);
	}

	loadYamls();

	// Reading users for scoreboard
	List<String> names = userConfig.getStringList("users");
	for (String name : names) {
	    Player player = Bukkit.getPlayer(UUID.fromString(name));
	    if (player != null) {
		users.add(player);
	    }
	}

	// Startup option
	if (getConfig().getBoolean("onstartup.showcommands", false)) {
	    Set<String> commandNames = this.getDescription().getCommands().keySet();
	    JoinMessenger.registerPluginMessage(this, language.getString("onstartup.availablecommands", "Available commands"));
	    for (String s : commandNames) {
		JoinMessenger.registerPluginMessage(this, "  - " + s);
	    }
	}
	if (getConfig().getBoolean("onstartup.showlines", false)) {
	    List<String> lines = language.getStringList("onstartup.lines");
	    JoinMessenger.registerPluginMessages(this, lines);
	}

	// Scoreboard option
	if (getConfig().getBoolean("scoreboard.enable", false)) {
	    task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

		@Override
		public void run() {
		    updateScoreboard();
		}

	    }, 0, 20 * getConfig().getInt("scoreboard.updatetime", 1));
	}
    }

    public void onDisable() {
	super.onDisable();

	Bukkit.getScheduler().cancelTask(task);

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
	    userConfig.save(userConfigFile);
	    scoreboardConfig.save(scoreboardConfigFile);
	} catch (IOException e) {
	    Bukkit.getLogger().log(Level.SEVERE, "Error during yaml saving", e);
	}
    }

    private void loadYamls() {
	try {
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
			}
		    } else if (args[0].equalsIgnoreCase("hide")) {
			if (users.contains(sender)) {
			    users.remove(sender);
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
