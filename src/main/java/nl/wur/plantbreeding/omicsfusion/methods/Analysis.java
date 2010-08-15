/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import java.io.FileOutputStream;
import java.util.logging.Logger;

/**
 *
 * @author finke002
 */
public class Analysis {

    /** A logger */
    private static final Logger LOG = Logger.getLogger(Analysis.class.getName());
    /** Write R files to the required directory */
    private FileOutputStream fos;

    /**
     * @return the fos
     */
    public FileOutputStream getFos() {
        return fos;
    }

    /**
     * @param fos the fos to set
     */
    public void setFos(FileOutputStream fos) {
        this.fos = fos;
    }

    public void readExcelSheet(String sessionID) {
    }


}
