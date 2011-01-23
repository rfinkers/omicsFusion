/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    /** The logger */
    private static final Logger LOG = Logger.getLogger(DataUploadValidationForm.class.getName());
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
    /** File containing the xxxxx */
    private File dataSheetPredictResponseFile = null;
    /** Name */
    private String dataSheetPredictResponseFileFileName = null;
    /** type of the PredictResponse */
    private String dataSheetPredictResponseFileContentType = null;

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

    public File getDataSheetPredictResponseFile() {
        return dataSheetPredictResponseFile;
    }

    public void setDataSheetPredictResponseFile(File dataSheetPredictResponseFile) {
        this.dataSheetPredictResponseFile = dataSheetPredictResponseFile;
    }

    public String getDataSheetPredictResponseFileContentType() {
        return dataSheetPredictResponseFileContentType;
    }

    public void setDataSheetPredictResponseFileContentType(String dataSheetPredictResponseFileContentType) {
        this.dataSheetPredictResponseFileContentType = dataSheetPredictResponseFileContentType;
    }

    public String getDataSheetPredictResponseFileFileName() {
        return dataSheetPredictResponseFileFileName;
    }

    public void setDataSheetPredictResponseFileFileName(String dataSheetPredictResponseFileFileName) {
        this.dataSheetPredictResponseFileFileName = dataSheetPredictResponseFileFileName;
    }

    @Override
    public void validate() {
        LOG.info("Start Excel form validation");

        //Order of test is important for the order in which the messages are shown.
        if (dataSheetResponseFile == null) {//TODO: via validation.xml?
            addActionError(getText("select.response"));//"Please select a response file");//TODO: resource bundle
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
//              addActionError("please select a file");
//        }

//        if (file1==null){
//            addActionError("please select a file");
//        }
//        if (file1.canRead() == false) {
//            addActionError("cannot read file");
//
//        }
    }
}
