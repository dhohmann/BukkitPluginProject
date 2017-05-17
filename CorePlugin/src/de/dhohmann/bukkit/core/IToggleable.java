package de.dhohmann.bukkit.core;

public interface IToggleable {
    /**
     * Called for starting the functionality of the implementing class.
     * Initializes all resources.
     */
    public void activate();
    
    /**
     * Called for ending the functionality of the implementing class. 
     * Frees all resources.
     */
    public void deactivate();
}
