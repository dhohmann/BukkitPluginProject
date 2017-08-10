package de.dhohmann.bukkit.craft.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public interface GodLike extends Unbreakable {

    /**
     * Determines items that work as normal items
     */
    public static final int NORMAL = 1;

    /**
     * Determines items that should not break and can only be used for suiting blocks
     */
    public static final int DEMIGOD = 2;

    /**
     * Determines items that should not break and can be used for every block
     */
    public static final int GODLIKE = 3;

    /**
     * This method is called, when the user clicks on an entity or a block. Use this method to
     * manipulate the clicked entity or block.
     * <ul>
     * <li>To check the action type use: {@code e.getAction() == Action.LEFT_CLICK_AIR}
     * </ul>
     * @param e Event holding information about the player and the block
     */
    @EventHandler
    public void onItemUse(PlayerInteractEvent e);

}
