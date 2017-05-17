package de.dhohmann.bukkit.util;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

public class MessageFormatter {

    /**
     * Replaces every placeholder in the message with the given value
     * @param message
     * @param placeholder
     * @param value
     * @return Formatted message
     */
    public static String replacePlaceholder(String message, String placeholder, String value) {
	StringBuilder builder = new StringBuilder();
	String[] parts = message.split(" ");
	for (int i = 0; i < parts.length; i++) {
	    String p = parts[i];
	    if (p.equalsIgnoreCase(placeholder)) {
		builder.append(value);
	    } else {
		builder.append(p);
	    }
	    builder.append(" ");
	}
	return builder.toString();
    }

    /**
     * Formats a message using the Minecraft Color Codes
     * @param message Message that will be formatted
     * @return Formatted message
     */
    public static String formatColorCode(String message) {
	if (message == null) {
	    return message;
	}
	StringBuilder builder = new StringBuilder();
	String[] parts = message.split(" ");
	for (int i = 0; i < parts.length; i++) {
	    String p = parts[i];
	    if (p.equalsIgnoreCase("§0") || p.equalsIgnoreCase("\u00A70")) {
		builder.append(ChatColor.BLACK);
	    } else if (p.equalsIgnoreCase("§1") || p.equalsIgnoreCase("\u00A71")) {
		builder.append(ChatColor.DARK_BLUE);
	    } else if (p.equalsIgnoreCase("§2") || p.equalsIgnoreCase("\u00A72")) {
		builder.append(ChatColor.DARK_GREEN);
	    } else if (p.equalsIgnoreCase("§3") || p.equalsIgnoreCase("\u00A73")) {
		builder.append(ChatColor.DARK_AQUA);
	    } else if (p.equalsIgnoreCase("§4") || p.equalsIgnoreCase("\u00A74")) {
		builder.append(ChatColor.DARK_RED);
	    } else if (p.equalsIgnoreCase("§5") || p.equalsIgnoreCase("\u00A75")) {
		builder.append(ChatColor.DARK_PURPLE);
	    } else if (p.equalsIgnoreCase("§6") || p.equalsIgnoreCase("\u00A76")) {
		builder.append(ChatColor.GOLD);
	    } else if (p.equalsIgnoreCase("§7") || p.equalsIgnoreCase("\u00A77")) {
		builder.append(ChatColor.GRAY);
	    } else if (p.equalsIgnoreCase("§8") || p.equalsIgnoreCase("\u00A78")) {
		builder.append(ChatColor.DARK_GRAY);
	    } else if (p.equalsIgnoreCase("§9") || p.equalsIgnoreCase("\u00A79")) {
		builder.append(ChatColor.BLUE);
	    } else if (p.equalsIgnoreCase("§a") || p.equalsIgnoreCase("\u00A7a")) {
		builder.append(ChatColor.GREEN);
	    } else if (p.equalsIgnoreCase("§b") || p.equalsIgnoreCase("\u00A7b")) {
		builder.append(ChatColor.AQUA);
	    } else if (p.equalsIgnoreCase("§c") || p.equalsIgnoreCase("\u00A7c")) {
		builder.append(ChatColor.RED);
	    } else if (p.equalsIgnoreCase("§d") || p.equalsIgnoreCase("\u00A7d")) {
		builder.append(ChatColor.LIGHT_PURPLE);
	    } else if (p.equalsIgnoreCase("§e") || p.equalsIgnoreCase("\u00A7e")) {
		builder.append(ChatColor.YELLOW);
	    } else if (p.equalsIgnoreCase("§f") || p.equalsIgnoreCase("\u00A7f")) {
		builder.append(ChatColor.WHITE);
	    } else if (p.equalsIgnoreCase("§l") || p.equalsIgnoreCase("\u00A7l")) {
		builder.append(ChatColor.BOLD);
	    } else if (p.equalsIgnoreCase("§o") || p.equalsIgnoreCase("\u00A7o")) {
		builder.append(ChatColor.ITALIC);
	    } else if (p.equalsIgnoreCase("§n") || p.equalsIgnoreCase("\u00A7n")) {
		builder.append(ChatColor.UNDERLINE);
	    } else if (p.equalsIgnoreCase("§m") || p.equalsIgnoreCase("\u00A7m")) {
		builder.append(ChatColor.STRIKETHROUGH);
	    } else if (p.equalsIgnoreCase("§r") || p.equalsIgnoreCase("\u00A7r")) {
		builder.append(ChatColor.RESET);
	    } else {
		builder.append(p);
	    }
	    builder.append(" ");
	}
	return builder.toString();
    }

    /**
     * Formats the given message with the entity properties
     * <p>
     * <b>Supported placeholders:</b>
     * <ul>
     * <li><code>%entity_name%</code> will be replaced by the name of the entity.
     * </ul>
     * @param message Message containing the placeholder
     * @param entity Entity object
     * @return Formatted message
     */
    public static String formatPlaceholder(String message, Entity entity) {
	String result = message;
	result = replacePlaceholder(message, "%entity_name%", entity.getName());
	result = replacePlaceholder(message, "%entity_location%", entity.getLocation().toString());
	return result;
    }

    /**
     * Formats the given message with the entity properties
     * <p>
     * <b>Supported placeholders:</b>
     * <ul>
     * <li><code>%entity_name%</code> will be replaced by the name of the entity.
     * <li><code>%entity_location%</code> will be replaced by the location of the entity.
     * <li><code>%entity_health%</code> will be replaced by the health of the entity.
     * <li><code>%entity_killer%</code> will be replaced by the name of the killer of the entity.
     * </ul>
     * @param message Message containing the placeholder
     * @param entity LivingEntity object
     * @return Formatted message
     */
    public static String formatPlaceholder(String message, LivingEntity entity) {
	String result = message;
	result = replacePlaceholder(message, "%entity_name%", entity.getName());
	result = replacePlaceholder(message, "%entity_location%", entity.getLocation().toString());
	result = replacePlaceholder(message, "%entity_health%", Double.toString(entity.getHealth()));
	result = replacePlaceholder(message, "%entity_killer%", entity.getKiller().getDisplayName());
	return result;
    }

    /**
     * Formats the given message with the plugin properties
     * <p>
     * <b>Supported placeholders:</b>
     * <ul>
     * <li><code>%plugin_name%</code> will be replaced by the name of the plugin.
     * </ul>
     * @param message Message containing the placeholder
     * @param plugin Plugin object
     * @return Formatted message
     */
    public static String formatPlaceholder(String message, Plugin plugin) {
	String result = message;
	result = replacePlaceholder(message, "%plugin_name%", plugin.getName());
	return result;
    }

    /**
     * Formats the given message with the server properties
     * <p>
     * <b>Supported placeholders:</b>
     * <ul>
     * <li><code>%server_name%</code> will be replaced by the name of the server.
     * <li><code>%server_ip%</code> will be replaced by the server ip.
     * <li><code>%server_motd%</code> will be replaced by the motd of the server.
     * <li><code>%world_type%</code> will be replaced by the world type.
     * <li><code>%server_version%</code> will be replaced by the server implementation version.
     * </ul>
     * @param message Message containing the placeholder
     * @param server Server object
     * @return Formatted message
     */
    public static String formatPlaceholder(String message, Server server) {
	String result = message;
	result = replacePlaceholder(message, "%server_name%", server.getServerName());
	result = replacePlaceholder(message, "%server_ip%", server.getIp());
	result = replacePlaceholder(message, "%server_motd%", server.getMotd());
	result = replacePlaceholder(message, "%world_type%", server.getWorldType());
	result = replacePlaceholder(message, "%server_version%", server.getVersion());
	result = replacePlaceholder(message, "%flight_allowed%", Boolean.toString(server.getAllowFlight()));
	return result;
    }

    /**
     * Formats the given message with the world properties
     * <p>
     * <b>Supported placeholders:</b>
     * <ul>
     * <li><code>%world_name%</code> will be replaced by the name of the world.
     * <li><code>%world_time%</code> will be replaced by the time of the world in 24h format.
     * <li><code>%world_time_raw%</code> will be replaced by the time of the world.
     * <li><code>%world_difficulty%</code> will be replaced by the difficulty of the world.
     * <li><code>%world_height%</code> will be replaced by the maximum height of the world.
     * <li><code>%world_seed%</code> will be replaced by the world seed.
     * <li><code>%world_height%</code> will be replaced by the current number of players.
     * </ul>
     * @param message Message containing the placeholder
     * @param world World object
     * @return Formatted message
     */
    public static String formatPlaceholder(String message, World world) {
	String result = message;
	int hours = (int) ((Math.floor(world.getTime() / 1000.0) + 8) % 24); // '8' is the offset
	int minutes = (int) Math.floor((world.getTime() % 1000) / 1000.0 * 60);
	result = replacePlaceholder(message, "%world_time%", String.format("%02d:%02d", hours, minutes));
	result = replacePlaceholder(message, "%world_time_raw%", Long.toString(world.getTime()));
	result = replacePlaceholder(message, "%world_name%", world.getName());
	result = replacePlaceholder(message, "world_difficulty", world.getDifficulty().name());
	result = replacePlaceholder(message, "world_height", Integer.toString(world.getMaxHeight()));
	result = replacePlaceholder(message, "world_seed", Long.toString(world.getSeed()));
	result = replacePlaceholder(message, "world_players", Integer.toString(world.getPlayers().size()));
	return result;
    }
}
