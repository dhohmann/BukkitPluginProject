package de.dhohmann.bukkit.message.listener;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PlayerListener implements Listener {
	private Plugin plugin;

	public PlayerListener(Plugin p) {
		plugin = p;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		String message = plugin.getConfig().getString("messages.playerjoin", "%player% has joined the server");
		message = formatMessage(message, e);
		message = colorize(message);
		e.setJoinMessage(message);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		String message = plugin.getConfig().getString("messages.playerleave", "%player% has left the server");
		message = formatMessage(message, e);
		message = colorize(message);
		e.setQuitMessage(message);
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent e) {
		String message = plugin.getConfig().getString("messages.playerkick", "%player% has left the server");
		message = formatMessage(message, e);
		message = colorize(message);
		e.setLeaveMessage(message);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		String message = plugin.getConfig().getString("chat-prefix.player", "&f <%player%> %message%");
		message = formatMessage(message, e);
		message = colorize(message);
		e.setFormat(message);
	}
	
	/*
	 * TODO Event Handling
	 * - Player Death Event
	 * - 
	 */

	private static String formatMessage(String message, Event e) {
		System.out.println("[INFO] to format: " + message);
		if (e instanceof PlayerJoinEvent) {
			message = message.replace("%player%", ((PlayerJoinEvent) e).getPlayer().getDisplayName());
		}
		if (e instanceof AsyncPlayerChatEvent) {
			message = message.replace("%player%", "%s");
			message = message.replace("%message%", "%s");
		}
		if (e instanceof PlayerQuitEvent) {
			message = message.replace("%player%", ((PlayerQuitEvent) e).getPlayer().getDisplayName());
		}
		if (e instanceof PlayerKickEvent) {
			message = message.replace("%player%", ((PlayerKickEvent) e).getPlayer().getDisplayName());
			message = message.replace("%reason%", ((PlayerKickEvent) e).getReason());
		}
		System.out.println("[INFO] formatted:" + message);
		return message;
	}

	public static String colorize(String s) {
		if (s == null)
			return null;
		return s.replaceAll("&([0-9a-f])", "\u00A7$1");
	}
}
