package de.dhohmann.bukkit.craft.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;

/**
 * Interface that determines that an item is unbreakable
 * @author dhohmann
 *
 */
public interface Unbreakable extends Listener {

    /**
     * Use this method to prevent the item from breaking
     * @param e Event holding player and item information
     */
    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent e);

}
