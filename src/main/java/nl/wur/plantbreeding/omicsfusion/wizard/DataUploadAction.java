/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.omicsfusion.excel.ValidateDataSheet;
import java.io.File;
import java.io.FileNotFoundException;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Action which handles upload of the data sheets and validates the if they are conform the requried standards. Data will be stored in a database?
 * 
 * @author Richard Finkers
 */
public class DataUploadAction extends DataUploadValidationForm implements ServletRequestAware {

    private static final long serialVersionUID = 170610L;
    /** the request */
    private HttpServletRequest request;

    @Override
    public String execute() throws Exception {
        try {

            File theFile = getDataSheetPredictorFile();

            File destFile = new File("/tmp/" + request.getSession().getId() + "/" + getDataSheetPredictorFileFileName());//TODO: get tmp directory from environment
            FileUtils.copyFile(theFile, destFile);

            System.out.println("Predictor: " + getDataSheetPredictorFileFileName());
            System.out.println("Type: " + getPredictorType());
            System.out.println("Response: " + getDataSheetResponseFileFileName());
            System.out.println("Type: "+ getResponseType());
            
            //validate excelsheet. validation depends on the type of the sheet

            boolean validationPredictorSheet = ValidateDataSheet.validate(destFile);
            if (validationPredictorSheet == false) {
                addActionError("Excel sheet validation failed");//TODO: more detailed errors should be given.
                return INPUT;
            }
            System.out.println("validation: " + validationPredictorSheet);
            //TODO: catch the different exceptions and handle them in a correct manner

            //TODO: should we prepare the list with selectionboxes here?
        } catch (FileNotFoundException e) {

            addActionError(e.getMessage());

            return INPUT;

        }
        System.out.println("Action: upload data completed");//TODO; remove debug code

        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
