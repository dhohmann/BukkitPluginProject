package de.dhohmann.bukkit.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

/**
 * Displays registered commands to players when they join the server
 * 
 * @author hohmann
 * @version 0.1.0
 * @since 2.0
 */
public class CommandPrinter implements Listener {

    private static CommandPrinter instance;

    private static CommandPrinter getPrinter() {
	if (instance == null)
	    instance = new CommandPrinter();
	return instance;
    }

    private Map<String, List<String>> output;
    private String channel;
    private Plugin plugin;

    private CommandPrinter() {
	output = new HashMap<>();
	channel = "Available Commands";
	plugin = Bukkit.getPluginManager().getPlugin("CorePlugin");
	
	init();
    }

    private void init() {
	Bukkit.getPluginManager().registerEvents(this, plugin);
	Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, channel);
    }

    /**
     * Registers all commands of the plugin to display them to a joining player. Excludes all
     * specified command names.
     * @param plugin The plugin whose commands you want to register for player join
     * @param exceptions The command names you want to exclude
     */
    public static void registerCommands(Plugin plugin, String... exceptions) {
	getPrinter().registerPlugin(plugin, exceptions);
    }

    private void registerPlugin(Plugin plugin, String[] exceptions) {
	Map<String, Map<String, Object>> commands = plugin.getDescription().getCommands();
	List<String> lines = new ArrayList<>();
	String[] names = {};
	if (exceptions != null) {
	    names = exceptions;
	}
	Arrays.sort(names);
	for (String s : commands.keySet()) {
	    if (Arrays.binarySearch(names, s) < 0) {
		lines.add(s + ": " + ((Command) commands.get(s)).getDescription());
	    }
	}
	output.put(plugin.getName(), lines);
    }

    /**
     * Unregisters all commands of the plugin.
     * @param plugin The plugin whose commands you want to unregister
     */
    public static void unregisterCommands(Plugin plugin) {
	getPrinter().unregisterPlugin(plugin);
    }

    private void unregisterPlugin(Plugin plugin) {
	output.remove(plugin.getName());
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {
	Player player = e.getPlayer();
	for (String pluginName : output.keySet()) {
	    StringBuilder builder = new StringBuilder("=== "+pluginName + " ===\n");
	    for (String command : output.get(pluginName)) {
		builder.append(command + "\n");
	    }
	    builder.append("====");
	    for(int i = 0; i<pluginName.length(); i++){
		builder.append('=');
	    }
	    builder.append("====");
	    player.sendPluginMessage(plugin, channel, builder.toString().getBytes());
	}
    }
}
