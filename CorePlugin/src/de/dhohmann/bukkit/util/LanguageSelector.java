package de.dhohmann.bukkit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class can be used to use multiple languages.
 * <p>
 * <i>Note:</i> Project structure must contain a folder called languages
 * @author dhohmann
 *
 */
public class LanguageSelector {

    /**
     * Creates a language selector holding various languages
     * @param folder
     * @return
     */
    public static LanguageSelector createSelector(File folder) {
	return new LanguageSelector(folder);
    }

    private Map<Object, Properties> languages;

    private LanguageSelector(File folder) {
	languages = new ConcurrentHashMap<>();
	try {
	    readFiles(folder);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Reads all possible property files inside a folder
     * @param f
     * @throws IOException
     */
    private void readFiles(File f) throws IOException {
	if (f.exists() && f.isDirectory()) {
	    for (File file : f.listFiles()) {
		readFiles(file);
	    }
	} else if (f.exists() && f.isFile()) {
	    String extension = f.getName().substring(f.getName().indexOf('.'), f.getName().length());
	    System.out.println(extension);
	    System.out.println(f.getName());
	    Properties prop = new Properties();

	    // Read File
	    InputStream input = new FileInputStream(f);
	    try {
		if (extension.equals(".xml")) {
		    prop.loadFromXML(input);
		} else if (extension.equals(".properties")) {
		    prop.load(input);
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    } finally {
		input.close();
	    }

	    // Insert Properties
	    String lang = f.getName().substring(0, f.getName().indexOf(extension));
	    Locale l = Locale.forLanguageTag(lang);
	    if (l.getLanguage() != null) {
		languages.putIfAbsent(l, prop);
	    }
	}

    }

    /**
     * Returns the requested language
     * @param l
     * @return
     */
    public Properties getLanguage(Locale l) {
	for (Object loc : languages.keySet()) {
	    if (loc instanceof Locale) {
		if (((Locale) loc).getLanguage().equalsIgnoreCase(l.getLanguage())) {
		    return languages.get(loc);
		}
	    }
	}
	return new Properties();
    }
    
    public List<String> availableLanguages(){
	List<String> available = new ArrayList<String>();
	for(Object loc : languages.keySet()){
	    if(loc instanceof Locale){
		available.add(((Locale)loc).getLanguage());
	    }
	}
	return available;
    }

}
