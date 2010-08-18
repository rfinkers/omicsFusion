/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.omicsfusion.excel.ValidateDataSheets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import nl.wur.plantbreeding.omicsfusion.excel.DataSheetValidationException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

            File responseSheet = new File("/tmp/" + request.getSession().getId() + "/" + getDataSheetResponseFileFileName());//TODO: get tmp directory from environment
            File predictorSheet = new File("/tmp/" + request.getSession().getId() + "/" + getDataSheetPredictorFileFileName());//TODO: get tmp directory from environment
            //Copy the uploaded excel sheets to a temporary directory?  Test if required
            FileUtils.copyFile(getDataSheetPredictorFile(), predictorSheet);
            FileUtils.copyFile(getDataSheetResponseFile(), responseSheet);

            System.out.println("Predictor: " + getDataSheetPredictorFileFileName());
            System.out.println("Type: " + getPredictorType());
            System.out.println("Response: " + getDataSheetResponseFileFileName());
            System.out.println("Type: " + getResponseType());

            //validate the correctness of the format of the excelsheet.
            ValidateDataSheets.validateExcelSheets(responseSheet, predictorSheet);

            //TODO: should we prepare the list with selectionboxes here depending of the type of sheets?
        } catch (InvalidFormatException e) {
            addActionError(e.getMessage());
            return INPUT;
        } catch (DataSheetValidationException e) {
            addActionError(e.getMessage());
            return INPUT;
        } catch (FileNotFoundException e) {
            addActionError(e.getMessage());
            return INPUT;
        } catch (IOException e) {
            addActionError(e.getMessage());
            return INPUT;
        }
        System.out.println("Action: upload data completed");//TODO; remove debug code

        HashMap<String, String> sheets = new HashMap<String, String>(2);
        sheets.put("predictor", getDataSheetPredictorFileFileName());
        sheets.put("response", getDataSheetResponseFileFileName());

        request.getSession().setAttribute("sheets", sheets);

        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
