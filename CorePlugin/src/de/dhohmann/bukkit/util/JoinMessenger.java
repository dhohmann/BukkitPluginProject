package de.dhohmann.bukkit.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import de.dhohmann.bukkit.core.Core;

public class JoinMessenger implements Listener {

    /**
     * Contains all lines related to the plugin they are related to
     */
    private Map<String, List<String>> lines;

    /**
     * Constructs a new JoinMessenger
     */
    public JoinMessenger() {
	lines = new ConcurrentHashMap<>();
	Bukkit.getLogger().log(Level.INFO, "Enabling event handling for join messenger");
	Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin(Core.getCorePluginName()));
    }

    /**
     * Registers a message for the player join
     * @param p Plugin the message is related to
     * @param message Message that should be added
     */
    public void registerMessage(Plugin p, String message) {
	if(p == null || message == null) return;
	if (!lines.containsKey(p.getName())) {
	    lines.put(p.getName(), new ArrayList<>());
	}
	lines.get(p).add(message);
    }

    /**
     * Registers a bunch of messages for the player join.
     * @param p Plugin the messages are related to
     * @param messages Some messages that should be added
     */
    public void registerMessages(Plugin p, List<String> messages) {
	if(p == null || messages == null) return;
	if(!lines.containsKey(p.getName())){
	    lines.put(p.getName(), messages);
	} else {
	    Iterator<String> iter = messages.iterator();
	    while(iter.hasNext()){
		registerMessage(p, iter.next());
	    }
	}
    }

    /**
     * Removes all messages related to a plugin
     * @param p Plugin the messages are related to
     */
    public void removeMessages(Plugin p) {
	if(p == null) return;
	if (lines.containsKey(p.getName())) {
	    lines.remove(p.getName());
	}
    }

    /**
     * EventHandler when a player joins the server
     * @param event Event containing player information
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
	Player player = event.getPlayer();
	Iterator<String> keys = lines.keySet().iterator();
	while (keys.hasNext()) {
	    String key = keys.next();
	    player.sendMessage("*** Plugin: " + key + " ***");
	    Iterator<String> messages = lines.get(key).iterator();
	    while (messages.hasNext()) {
		player.sendMessage(messages.next());
	    }
	}
    }

}
