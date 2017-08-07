package de.dhohmann.bukkit.core;

/**
 * Enables or disables a feature
 * @author dhohmann
 * @version 1.0
 *
 */
public interface Toggleable {

    /**
     * Called, when the feature is enabled
     */
    void enable();

    /**
     * Called, when the feature is disabled
     */
    void disable();

    /**
     * Returns the state of the feature
     * @return <code>true</code>, if the feature is enabled
     */
    boolean isEnabled();

}
