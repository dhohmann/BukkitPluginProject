package de.dhohmann.bukkit.info;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import de.dhohmann.bukkit.plugin.CustomJavaPlugin;

public class InformationPlugin extends CustomJavaPlugin {
    private int task;
    private List<Player> players;

    @Override
    public boolean hasConfig() {
	return true;
    }

    /**
     * Called when the plugin is loaded
     */
    public void activate() {
	if (players == null) {
	    players = new ArrayList<>();
	}
	task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

	    @Override
	    public void run() {
		updateScoreboard();
	    }

	}, 0, 20);

	getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    /**
     * Called when reloading plugin or shutting down the server
     */
    public void deactivate() {
	Bukkit.getScheduler().cancelTask(task);
    }

    private void updateScoreboard() {
	List<String> lines = getConfig().getStringList("information.lines");

	// Create Scoreboard
	Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
	Objective obj = board.registerNewObjective("Information", "");
	obj.setDisplayName(getConfig().getString("information.title"));
	obj.setDisplaySlot(DisplaySlot.SIDEBAR);

	// Set time format
	SimpleDateFormat format = new SimpleDateFormat("HH:mm");

	// Set Scores
	List<Score> scores = new ArrayList<>();
	for (String s : lines) {
	    String tmp = s.toString();

	    // Edit lines with placeholder
	    tmp = tmp.replace("%realtime%", format.format(new Date()));
	    tmp = tmp.replace("%worldtime%", timeToString(this.getServer().getWorlds().get(0).getTime()));
	    tmp = tmp.replace("%flight_allowed%", Boolean.toString(this.getServer().getAllowFlight()));
	    tmp = tmp.replace("%online_players%", Integer.toString(this.getServer().getOnlinePlayers().size()));
	    tmp = tmp.replace("%difficulty%", this.getServer().getWorlds().get(0).getDifficulty().toString());
	    tmp = tmp.replace("%server%", (getServer().getIp().equals("") ? "localhost" : getServer().getIp()) + ":" + getServer().getPort());
	    scores.add(obj.getScore(tmp));
	}

	int i = 0, size = scores.size() - 1;
	for (Score s : scores) {
	    s.setScore(size - i);
	    i++;
	}
	Collection<? extends Player> onlinePlayers = getServer().getOnlinePlayers();
	// Send scoreboard to all players
	for (Player p : onlinePlayers) {
	    if (!p.isOnline()) {
		continue;
	    }
	    try {
		if (!players.contains(p)) {
		    p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		} else {
		    p.setScoreboard(board);
		}
	    } catch (IllegalStateException e) {
		e.printStackTrace();
	    }
	}
    }

    private String timeToString(long time) {
	int hours = (int) ((Math.floor(time / 1000.0) + 8) % 24); // '8' is the offset
	int minutes = (int) Math.floor((time % 1000) / 1000.0 * 60);
	return String.format("%02d:%02d", hours, minutes);
    }

    /**
     * Triggered when the player adds the defined command
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	if (label.equals("itoggle") || command.getName().equalsIgnoreCase("info-toggle")) {
	    Player p = (Player) sender;
	    if (players.contains(p)) {
		players.remove(p);
	    } else {
		players.add(p);
	    }
	    return true;
	}
	if (label.equals("ireload") || command.getName().equalsIgnoreCase("info-reload")) {
	    System.out.println("[INFO] Reloading config for InformationPlugin");
	    reloadConfig();
	    return true;
	}
	return false;
    }
}
