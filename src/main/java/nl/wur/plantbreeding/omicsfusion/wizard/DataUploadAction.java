package nl.wur.plantbreeding.omicsfusion.wizard;

import java.util.logging.Level;
import java.util.logging.Logger;
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
 * Action which handles upload of the data sheets and validates the if they are
 * conform the requried standards. Data will be stored in a database?
 *
 * @author Richard Finkers
 * @version 1.0
 */
public class DataUploadAction extends DataUploadValidationForm implements ServletRequestAware {

    private static final long serialVersionUID = 170610L;
    /** The logger */
    private static final Logger LOG = Logger.getLogger(DataUploadAction.class.getName());
    /** the request */
    private HttpServletRequest request;

    @Override
    public String execute() throws Exception {
        try {

            String tempdir = System.getProperty("java.io.tmpdir");
            if (!(tempdir.endsWith("/") || tempdir.endsWith("\\"))) {
                tempdir += System.getProperty("file.separator");
            }

            File responseSheet = new File(tempdir + request.getSession().getId()
                    + "/" + getDataSheetResponseFileFileName());
            File predictorSheet = new File(tempdir + request.getSession().getId()
                    + "/" + getDataSheetPredictorFileFileName());
            //Copy the uploaded excel sheets to a temporary directory
            FileUtils.copyFile(getDataSheetPredictorFile(), predictorSheet);
            FileUtils.copyFile(getDataSheetResponseFile(), responseSheet);

            LOG.log(Level.INFO, "Predictor: {0}", getDataSheetPredictorFileFileName());
            LOG.log(Level.INFO, "Type: {0}", getPredictorType());
            LOG.log(Level.INFO, "Response: {0}", getDataSheetResponseFileFileName());
            LOG.log(Level.INFO, "Type: {0}", getResponseType());

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
        LOG.info("Action: upload data completed");//TODO; remove debug code

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
