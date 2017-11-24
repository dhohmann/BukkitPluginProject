package de.dhohmann.bukkit.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Represents a Java Bukkit plugin. Provides additional features for inheriting classes
 * <p>
 * <ul>
 * <li>Additional methods for language management
 * <li>Methods for repairing and loading the default configuration file
 * <li>Methods for copying resources
 * </ul>
 * 
 * @author hohmann
 * @version 2.0
 * @since 1.0
 */
public abstract class DJavaPlugin extends JavaPlugin {

    /**
     * Identifies a property as the name of the supported language
     */
    public static final String LANGUAGE_IDENTIFIER = "language";

    private Map<String, FileConfiguration> languages;

    /**
     * Registers the specified resource as a language file. If the file does not exist in the data
     * folder, the file will be created and filled with the default values from the jar. The
     * configuration file must contain the property {@value #LANGUAGE_IDENTIFIER} to support the
     * registration. Otherwise the it will be ignored.
     * 
     * @param resource Relative name of the resource file
     * @throws InvalidConfigurationException if the given resource is not a valid configuration
     * @throws IOException if the given resource is a directory
     * 
     * @see #registerLanguageFolder(String)
     * @see #registerLanguage(FileConfiguration)
     */
    protected void registerLanguage(String resource) throws InvalidConfigurationException, IOException {
	File file = new File(getDataFolder(), resource);
	if (file.isDirectory())
	    throw new IOException("The resource " + resource + " is a directory.");
	if (!file.exists()) {
	    file.getParentFile().mkdirs();
	    copy(getResource(resource), file);
	}
	FileConfiguration config = new YamlConfiguration();
	try {
	    config.load(file);
	} catch (IOException e) {
	    Bukkit.getLogger().log(Level.WARNING, "Configuration file " + resource + " cannot be opened.");
	}
	registerLanguage(config);
    }

    /**
     * Registers the given configuration as a language. The configuration must contain the property
     * {@value #LANGUAGE_IDENTIFIER} to support the registration. Otherwise it will be ignored.
     * <p>
     * If another language is registered with the same name, the configuration will be overwritten.
     * 
     * @param config Configuration holding the language information
     * 
     * @see #registerLanguage(String)
     * @see #registerLanguageFolder(String)
     */
    protected void registerLanguage(FileConfiguration config) {
	String name = config.getString(LANGUAGE_IDENTIFIER, null);
	if (name != null) {
	    languages.put(name, config);
	} else {
	    Bukkit.getLogger().log(Level.WARNING, "A language configuration was not added.", config);
	}
    }

    /**
     * Registers all files inside the folder as language files. If the files do not exist in the
     * data folder, the files will be created and filled with the default values from the jar. The
     * configuration files must each contain the property {@value #LANGUAGE_IDENTIFIER} to support
     * the registration. Otherwise the configuration file will be ignored.
     * 
     * @param name Relative name of the resource folder
     * @throws InvalidConfigurationException if one of the files is not a valid configuration
     * @throws IOException if the resource is not a directory.
     * 
     * @see #registerLanguage(String)
     * @see #registerLanguage(FileConfiguration)
     */
    protected void registerLanguageFolder(String name) throws InvalidConfigurationException, IOException {
	File file = new File(getDataFolder(), name);
	if (!file.isDirectory()) {
	    throw new IOException("The resource " + name + " is not a directory.");
	}
	if (!file.exists()) {
	    file.mkdirs();
	    copyFiles(file);
	}

	for (File f : file.listFiles()) {
	    FileConfiguration config = new YamlConfiguration();
	    if (f.exists()) {
		try {
		    config.load(f);
		} catch (IOException e) {
		    Bukkit.getLogger().log(Level.WARNING,
			    "Configuration file " + name + FileSystems.getDefault().getSeparator() + f.getName() + " cannot be opened.");
		}
		registerLanguage(config);
	    }
	}
    }

    /**
     * Saves all the registered languages to the given destination inside the data folder
     * <p>
     * Note: All existing files inside the destination directory will be overwritten.
     * 
     * @param name Destination directory inside the data folder
     * @throws IOException if a language could not be saved
     */
    protected void saveLanguages(String name) throws IOException {
	File file = new File(getDataFolder(), name);
	if (!file.exists()) {
	    file.mkdirs();
	}
	for (String lang : languages.keySet()) {
	    FileConfiguration f = languages.get(lang);
	    File dest = new File(file, lang + ".yml");
	    f.save(dest);
	}
    }

    /**
     * Loads the default configuration file "config.yml". If the file does not exist in the data
     * folder, the default values from the jar will be loaded. Otherwise the existing file will be
     * used.
     * 
     * @throws InvalidConfigurationException if the configuration file is not valid
     * @throws IOException if file could not be read
     */
    protected void loadConfig() throws FileNotFoundException, IOException, InvalidConfigurationException {
	File file = new File(getDataFolder(), "config.yml");
	if (!file.exists()) {
	    copy(getResource("config.yml"), file);
	}
	getConfig().load(file);
    }

    /**
     * Resets the default configuration file "config.yml" and includes every missing property from
     * the default with the default value. All other properties will remain unchanged.
     * @throws InvalidConfigurationException if the config.yml
     * @throws IOException
     */
    protected void resetConfig() throws UnsupportedEncodingException, IOException, InvalidConfigurationException {
	FileConfiguration defaultConfig = new YamlConfiguration();
	FileConfiguration presentConfig = getConfig();
	InputStream stream = getResource("config.yml");
	if (stream != null) {
	    defaultConfig.load(new InputStreamReader(stream));
	}

	for (String key : defaultConfig.getKeys(true)) {
	    if (!presentConfig.contains(key, true)) {
		presentConfig.set(key, defaultConfig.get(key));
	    }
	}
    }

    /**
     * Returns the language specified by language of the location
     * @deprecated
     * 
     * @param location Requested location
     * @return The requested language configuration if it is found, an empty configuration
     *         otherwise.
     * 
     */
    protected FileConfiguration getLanguage(Locale location) {
	try {
	    Locale l = Locale.forLanguageTag(location.getLanguage());
	    FileConfiguration config = languages.get(l);
	    return config == null ? new YamlConfiguration() : config;

	} catch (Exception e) {
	    Bukkit.getLogger().log(Level.SEVERE, "Something went wrong while reading language configuration", e);
	    return new YamlConfiguration();
	}
    }

    /**
     * Returns a registered string for a language
     * 
     * @param language Language name
     * @param path Path for the string
     * @return {@code null}, if the language is not registered or does not contain the given entity
     */
    protected String getLanguageString(String language, String path) {
	FileConfiguration config = languages.get(language);
	if (config != null) {
	    return config.getString(path, null);
	}
	return null;
    }

    /**
     * Returns a registered string for a language. If the string could not be found, the
     * defaultValue will be returned
     * 
     * @param language Language name
     * @param path Path for the string
     * @param defaultValue Default value
     * @return {@code defaultValue}, if the language is not registered or does not contain the given
     *         entity
     */
    protected String getLanguageString(String language, String path, String defaultValue) {
	String result = getLanguageString(language, path);
	if (result != null) {
	    return result;
	}
	return defaultValue;
    }

    /**
     * Copies the content of an input stream into the specified file
     * 
     * Note: If the output file exists, all data will be overwritten.
     * 
     * @param in input stream that retrieves the data
     * @param file destination for the output
     */
    protected void copy(InputStream in, File file) {
	if (in == null) {
	    Bukkit.getLogger().log(Level.WARNING, "InputStream for file " + file.getName() + " is null.");
	} else {
	    try {
		OutputStream out = new FileOutputStream(file);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
		    out.write(buf, 0, len);
		}
		out.close();
		in.close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    private void copyFiles(File folder) {
	try {
	    JarFile jarFile = new JarFile(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
	    Enumeration<JarEntry> entries = jarFile.entries();
	    while (entries.hasMoreElements()) {
		JarEntry entry = entries.nextElement();
		if (!entry.isDirectory() && entry.getName().startsWith(folder.getName())) {
		    String name = entry.getName().substring(entry.getName().lastIndexOf("/") + 1);
		    File file = new File(folder, name);
		    if (!file.exists()) {
			copy(jarFile.getInputStream(entry), file);
		    }
		}
	    }
	    jarFile.close();
	} catch (Exception e) {
	    Bukkit.getLogger().log(Level.SEVERE, "Something went wrong while reading files from jar", e);
	}
    }

}
