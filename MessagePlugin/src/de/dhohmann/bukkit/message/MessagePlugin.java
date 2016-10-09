package de.dhohmann.bukkit.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.dhohmann.bukkit.message.listener.PlayerListener;

public class MessagePlugin extends JavaPlugin {
	private int autoMsgTask = -1;
	private List<String> autoMsg;
	/**
	 * Called when the plugin is enabled<br>
	 * Use this for reading config file, creating global objects etc.
	 */
	public void onEnable() {
		loadConfig();
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		reateAutomessageTask();
	}

	private void reateAutomessageTask() {
		if(!getConfig().getBoolean("automessages.enabled", false)) return;
		List<?> list = getConfig().getList("automessages.list", null);
		if(list == null) return;
		autoMsg = new ArrayList<>();
		for(Object s : list){
			autoMsg.add((String) s);
		}
		
		autoMsgTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				String msg = autoMsg.get((int)(Math.random()*autoMsg.size()));
				Collection<? extends Player> players = Bukkit.getOnlinePlayers();
				for(Player p : players){
					p.sendMessage(msg);
				}
			}

		}, 0, getConfig().getLong("automessage.repeat-minutes", 1)*20*60);
	}

	/**
	 * Called when the plugin is disabled<br>
	 * Use this to free resources
	 */
	public void onDisable() {
		if(autoMsgTask != -1){
			Bukkit.getScheduler().cancelTask(autoMsgTask);
			autoMsg=null;
		}
	};
	
	public void loadConfig(){
		saveDefaultConfig();
		getConfig().options().copyDefaults();
		saveConfig();
	}
}
