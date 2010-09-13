/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.util.logging.Logger;

/**
 * Form containing information about the uploaded file's.
 * @author Richard Finkers
 * @version 1.0
 */
public class DataUploadValidationForm extends ActionSupport {

    private static final long serialVersionUID = 170610L;
    /** File containing the response variables */
    private File dataSheetResponseFile;
    /** content type of the data sheet */
    private String dataSheetResponseFileContentType;
    /** name of the dataSheet */
    private String dataSheetResponseFileFileName;
    /** Type of the response variables */
    private String responseType;
    /** File containing the predictor variables */
    private File dataSheetPredictorFile;
    /** content type of the data sheet */
    private String dataSheetPredictorFileContentType;
    /** name of the dataSheet */
    private String dataSheetPredictorFileFileName;
    /** Type of the predictor variables */
    private String predictorType;
    /** The logger */
    private static final Logger LOG = Logger.getLogger(DataUploadValidationForm.class.getName());
    /** File containing the xxxxx */
    private String dataSheetResponsePredictorFiles;//TODO: check and implement further

    public File getDataSheetPredictorFile() {
        return dataSheetPredictorFile;
    }

    public void setDataSheetPredictorFile(File dataSheetPredictorFile) {
        this.dataSheetPredictorFile = dataSheetPredictorFile;
    }

    public String getDataSheetPredictorFileContentType() {
        return dataSheetPredictorFileContentType;
    }

    public void setDataSheetPredictorFileContentType(String dataSheetPredictorFileContentType) {
        this.dataSheetPredictorFileContentType = dataSheetPredictorFileContentType;
    }

    public String getDataSheetPredictorFileFileName() {
        return dataSheetPredictorFileFileName;
    }

    public void setDataSheetPredictorFileFileName(String dataSheetPredictorFileFileName) {
        this.dataSheetPredictorFileFileName = dataSheetPredictorFileFileName;
    }

    public File getDataSheetResponseFile() {
        return dataSheetResponseFile;
    }

    public void setDataSheetResponseFile(File dataSheetResponseFile) {
        this.dataSheetResponseFile = dataSheetResponseFile;
    }

    public String getDataSheetResponseFileContentType() {
        return dataSheetResponseFileContentType;
    }

    public void setDataSheetResponseFileContentType(String dataSheetResponseFileContentType) {
        this.dataSheetResponseFileContentType = dataSheetResponseFileContentType;
    }

    public String getDataSheetResponseFileFileName() {
        return dataSheetResponseFileFileName;
    }

    public void setDataSheetResponseFileFileName(String dataSheetResponseFileFileName) {
        this.dataSheetResponseFileFileName = dataSheetResponseFileFileName;
    }

    public String getPredictorType() {
        return predictorType;
    }

    public void setPredictorType(String predictorType) {
        this.predictorType = predictorType;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    @Override
    public void validate() {

        //Order of importance for the order that the messages are shown.
        if (dataSheetResponseFile == null || dataSheetPredictorFile == null) {//TODO: via validation.xml?
            addActionError("Please select a response file");//TODO: resource bundle
        }
        if (responseType.equals("select")) {//TODO: via validation.xml?
            addActionError("Please select the type of data for the response file");//TODO: resource bundle
        }
        if (dataSheetPredictorFile == null) {
            addActionError("Please select a predictor file");//TODO: resource bundle
        }
        if (predictorType.equals("select")) {
            addActionError("Please select the type of data for the predictor file");//TODO: resource bundle
        }

        //TODO: filenames are not kept after validation error. Type, however, is kept.

        //        if(dataSheetFileFileName.equals("")){
//              addActionError("please select a file");//TODO: via validation.xml?
//        }

//        if (file1==null){
//            addActionError("please select a file");//TODO: via validation.xml?
//        }
//        if (file1.canRead() == false) {
//            addActionError("cannot read file");
//
//        }
    }
}
