package de.dhohmann.bukkit.info;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerListener implements Listener{
	private Plugin plugin;
	public PlayerListener(Plugin p) {
		plugin = p;
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		List<String> messages = plugin.getConfig().getStringList("information.start-lines");
		Player p = e.getPlayer();
		for(String s: messages){
			p.sendMessage(s);
		}
	}
}
