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
 *
 * @author finke002
 */
public class WriteFile {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(WriteFile.class.getName());

    /**
     * Writes the data to a textfile
     * @param fileName
     * @param content
     * @throws IOException
     */
    public void WriteFile(String fileName, String content) throws IOException {

        File f = new File(fileName);//FIXME: be sure to write to the current /tmp directory for this session
        FileOutputStream fop = new FileOutputStream(f);

        if (f.exists()) {
            String str = "This data is written through the program";
            fop.write(content.getBytes());
            fop.flush();
            fop.close();
            System.out.println("The data has been written");
        } else {
            System.out.println("This file does not exist");
        }
    }
}
