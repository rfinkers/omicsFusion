/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import java.io.File;

/**
 *
 * @author finke002
 */
public class DataUploadValidationForm extends ActionSupport {

    private static final long serialVersionUID = 170610L;
    /** File containing the xxxx trait */
    private File file1;
    /** File containing the xxxx matrix */
    private File file2;

    public File getFile1() {
        return file1;
    }

    public void setFile1(File file1) {
        this.file1 = file1;
    }

    public File getFile2() {
        return file2;
    }

    public void setFile2(File file2) {
        this.file2 = file2;
    }

    public static Logger getLOG() {
        return LOG;
    }

    public static void setLOG(Logger LOG) {
        ActionSupport.LOG = LOG;
    }

    @Override
    public void validate() {

//        if (file1==null){
//            addActionError("please select a file");//TODO: via validation.xml?
//        }
//        if (file1.canRead() == false) {
//            addActionError("cannot read file");
//
//        }
    }
}
