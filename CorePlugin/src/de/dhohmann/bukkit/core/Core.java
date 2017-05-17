package de.dhohmann.bukkit.core;

import de.dhohmann.bukkit.util.JoinMessenger;

public class Core {
    
    private static final String CORE_PLUGIN_NAME = "CorePlugin";
    private static JoinMessenger joinMessenger;
    
    public static JoinMessenger getJoinMessenger(){
	if(joinMessenger == null){
	    joinMessenger = new JoinMessenger();
	}
	return joinMessenger;
    }
    
    public static String getCorePluginName(){
	return CORE_PLUGIN_NAME;
    }
}
