package de.dhohmann.bukkit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
    /**
     * Copies files from a source folder to a destination folder. It copies subfolders.
     * @param src Source folder
     * @param destination Destination folder
     * @return true, if everything has gone right
     * @throws IllegalArgumentException if one of the folders does not exist
     * @throws IOException
     */
    public static boolean copyFilesToFolder(File src, File destination) throws IllegalArgumentException, IOException {
	if (!src.exists()) {
	    throw new IllegalArgumentException("src " + src.getName() + " does not exist");
	}
	if (!destination.exists()) {
	    throw new IllegalArgumentException("destination folder " + destination.getName() + " does not exist");
	}
	for (File f : src.listFiles()) {
	    if (f.isDirectory()) {
		File subFolder = new File(destination, f.getName());
		if (!subFolder.exists()) {
		    subFolder.mkdir();
		}
		copyFilesToFolder(f, subFolder);
	    } else {
		InputStream is = null;
		OutputStream os = null;

		try {
		    is = new FileInputStream(f);
		    os = new FileOutputStream(new File(destination, f.getName()));
		    byte[] buffer = new byte[256];
		    int length = 0;
		    while ((length = is.read(buffer)) != -1) {
			os.write(buffer, 0, length);
		    }
		} finally {
		    is.close();
		    os.close();
		}
	    }
	}
	return true;
    }
}
