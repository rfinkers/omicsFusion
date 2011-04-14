package nl.wur.plantbreeding.logic.util;

import java.io.File;
import java.util.logging.Logger;

/**
 * Utility class to check if a file or directory exists (true / false).
 * @author Richard Finkers
 * @version 0.1
 * @since 0.1
 */
public class FileOrDirectoryExists {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(
            FileOrDirectoryExists.class.getName());

    private FileOrDirectoryExists() {
    }

    /**
     * Check if a file or directory exists in the file system.
     * @param name Name of the file or directory to check
     * @return Boolean if file/directory exists
     */
    public static boolean FileOrDirectoryExists(String name) {
        File file = new File(name);
        return file.exists();
    }
}
