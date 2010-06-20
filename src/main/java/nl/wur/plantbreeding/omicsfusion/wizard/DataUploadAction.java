/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.omicsfusion.excel.ValidateDataSheet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

            File theFile = getDataSheetFile();

            File destFile = new File("/tmp/" + request.getSession().getId() + "/" + getDataSheetFileFileName());//TODO: get tmp directory from environment
            FileUtils.copyFile(theFile, destFile);

            //validate excelsheet

            boolean validation = ValidateDataSheet.validate(destFile);
            if (validation == false) {
                addActionError("Excel sheet validation failed");//TODO: more detailed errors should be given.
                return INPUT;
            }
            System.out.println("validation: " + validation);
            //TODO: catch the different exceptions and handle them in a correct manner
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
