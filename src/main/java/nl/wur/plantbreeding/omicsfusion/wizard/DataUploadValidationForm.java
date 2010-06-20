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
 * @author Richard Finkers
 */
public class DataUploadValidationForm extends ActionSupport {

    private static final long serialVersionUID = 170610L;
    /** File containing the trait data */
    private File dataSheetFile;
    /** content type of the data sheet */
    private String dataSheetFileContentType;
    /** name of the dataSheet */
    private String dataSheetFileFileName;

    public File getDataSheetFile() {
        return dataSheetFile;
    }

    public void setDataSheetFile(File dataSheetFile) {
        this.dataSheetFile = dataSheetFile;
    }

    public String getDataSheetFileContentType() {
        return dataSheetFileContentType;
    }

    public void setDataSheetFileContentType(String dataSheetFileContentType) {
        this.dataSheetFileContentType = dataSheetFileContentType;
    }

    public String getDataSheetFileFileName() {
        return dataSheetFileFileName;
    }

    public void setDataSheetFileFileName(String dataSheetFileFileName) {
        this.dataSheetFileFileName = dataSheetFileFileName;
    }

    public static Logger getLOG() {
        return LOG;
    }

    public static void setLOG(Logger LOG) {
        ActionSupport.LOG = LOG;
    }

    @Override
    public void validate() {

        if(dataSheetFileFileName.equals("")){
              addActionError("please select a file");//TODO: via validation.xml?
        }

//        if (file1==null){
//            addActionError("please select a file");//TODO: via validation.xml?
//        }
//        if (file1.canRead() == false) {
//            addActionError("cannot read file");
//
//        }
    }
}
