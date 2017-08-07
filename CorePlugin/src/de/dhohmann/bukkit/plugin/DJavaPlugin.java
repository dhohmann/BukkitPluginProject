package de.dhohmann.bukkit.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class DJavaPlugin extends JavaPlugin {

    private File languageFolder;
    private Map<String, FileConfiguration> languages;

    @Override
    public void onEnable() {
	languageFolder = new File(getDataFolder(), "languages");
	if (!languageFolder.exists()) {
	    languageFolder.mkdirs();
	}
	copyFiles(languageFolder);
	loadLanguages();
    }
    
    @Override
    public void onDisable() {
	saveLanguages();
	saveConfig();
    }

    private void loadLanguages() {
	File[] languageFiles = languageFolder.listFiles();
	languages = new HashMap<>();

	for (File f : languageFiles) {
	    try {
		FileConfiguration config = new YamlConfiguration();
		config.load(f);
		Locale l = Locale.forLanguageTag(f.getName().substring(0, f.getName().lastIndexOf(".")));
		if (l == null) {

		}
		languages.putIfAbsent(l.getLanguage(), config);
	    } catch (Exception e) {
		Bukkit.getLogger().log(Level.SEVERE, "Something went wrong while reading language configuration", e);
	    }
	}
    }

    private void saveLanguages() {
	for (String lang : languages.keySet()) {
	    try {
		File f = new File(languageFolder, lang + ".yml");
		if (!f.exists()) {
		    throw new FileNotFoundException("File " + f.getName() + " not found");
		}
		FileConfiguration config = languages.get(lang);
		config.save(f);
	    } catch (Exception e) {
		Bukkit.getLogger().log(Level.SEVERE, "Something went wrong while saving language configuration", e);
	    }
	}
    }

    /**
     * Returns the language specified by language of the location
     * @param location Requested location
     * @return The requested language configuration if it is found, an empty configuration
     *         otherwise.
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
     * Copies the content of an input stream into a file
     * @param in - Specified input stream
     * @param file - Destination file
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
