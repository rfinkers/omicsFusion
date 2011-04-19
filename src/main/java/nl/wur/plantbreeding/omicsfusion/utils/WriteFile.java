/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Write files to the filesystem.
 * @author Richard Finkers
 * @version 1.0
 */
public class WriteFile {

    /** The logger */
    private static final Logger LOG =
            Logger.getLogger(WriteFile.class.getName());

    /**
     * Writes data to a textfile.
     * @param fileName Name of the target file.
     * @param content Text contents.
     * @throws IOException if something witn wring with file IO.
     */
    public void WriteFile(String fileName, String content) throws IOException {

        File f = new File(fileName);
        //FIXME: be sure to write to the current /tmp directory for this session
        FileOutputStream fop = new FileOutputStream(f);

        if (f.exists()) {
            fop.write(content.getBytes());
            fop.flush();
            fop.close();
        } else {
            LOG.warning("This file does not exist");//TODO throw exception?
        }
    }
}
