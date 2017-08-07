package de.dhohmann.bukkit.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class JoinMessenger implements Listener {

    /* Static methods and fields */
    
    private static JoinMessenger messenger;

    /**
     * Getter for the instance
     * @return Instance of the JoinMessenger
     */
    private static JoinMessenger getMessenger() {
	if (messenger == null) {
	    messenger = new JoinMessenger();
	}
	return messenger;
    }

    /**
     * Registers a message for a plugin
     * @param p Plugin triggering this method
     * @param message Message that will be displayed
     */
    public static void registerPluginMessage(Plugin p, String message) {
	getMessenger().registerMessage(p, message);
    }

    /**
     * Registers messages for a plugin
     * @param p Plugin triggering this method
     * @param messages Messages that will be displayed
     */
    public static void registerPluginMessages(Plugin p, List<String> messages) {
	for(String s : messages) getMessenger().registerMessage(p, s);
    }
    
    /**
     * Registers a message from a player
     * @param p Player triggering this method
     * @param message Message that will be displayed
     */
    public static void registerPlayerMessage(Player p, String message){
	getMessenger().registerMessage(p, message);
    }

    /* Non-static methods and fields */
    
    private Map<String, List<String>> lines;

    private JoinMessenger() {
	lines = new ConcurrentHashMap<>();
	Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("CorePlugin"));
    }

    private void registerMessage(Plugin p, String m){
	String key = p.getName();
	if(lines.containsKey(key)){
	    lines.get(key).add(m);
	} else {
	    List<String> messages = new ArrayList<>();
	    messages.add(m);
	    lines.put(key, messages);
	}
    }
    
    private void registerMessage(Player p, String m){
	String key = p.getDisplayName();
	if(lines.containsKey(key)){
	    lines.get(key).add(m);
	} else {
	    List<String> messages = new ArrayList<>();
	    messages.add(m);
	    lines.put(key, messages);
	}
    }

    /* Event handling methods */
    
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
	    player.sendMessage("*** " + key + " ***");
	    Iterator<String> messages = lines.get(key).iterator();
	    while (messages.hasNext()) {
		player.sendMessage(messages.next());
	    }
	}
    }

}
