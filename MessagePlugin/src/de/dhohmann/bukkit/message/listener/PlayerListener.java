package de.dhohmann.bukkit.message.listener;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
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

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		String message = plugin.getConfig().getString("messages.playerdeath", "%player% was killed by %killer%");
		message = formatMessage(message, e);
		message = colorize(message);
		e.setDeathMessage(message);
	}

	private static String formatMessage(String message, Event e) {
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
		if (e instanceof PlayerDeathEvent){
			message = message.replace("%player%", ((PlayerDeathEvent) e).getEntity().getDisplayName());
			message = message.replace("%killer%", (((PlayerDeathEvent) e).getEntity().getKiller()==null)? "<unknown>": ((PlayerDeathEvent) e).getEntity().getKiller().getDisplayName());
		}
		return message;
	}

	public static String colorize(String s) {
		if (s == null){
			throw new IllegalArgumentException("Argument must not be empty");
		}
		return s.replaceAll("&([0-9a-f])", "\u00A7$1");
	}
}
