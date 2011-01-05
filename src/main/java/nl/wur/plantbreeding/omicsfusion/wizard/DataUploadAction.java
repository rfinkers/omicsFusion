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

            LOG.info("Execute upload");
            //String resultsDirectory = System.getProperty("java.io.tmpdir");
            String resultsDirectory = request.getSession().getServletContext().getInitParameter("resultsDirectory");
            if (!(resultsDirectory.endsWith("/") || resultsDirectory.endsWith("\\"))) {
                resultsDirectory += System.getProperty("file.separator");
            }

            File responseSheet = new File(resultsDirectory + request.getSession().getId()
                    + "/" + getDataSheetResponseFileFileName());
            File predictorSheet = new File(resultsDirectory + request.getSession().getId()
                    + "/" + getDataSheetPredictorFileFileName());
            //Copy the uploaded excel sheets to a temporary directory
            FileUtils.copyFile(getDataSheetPredictorFile(), predictorSheet);
            FileUtils.copyFile(getDataSheetResponseFile(), responseSheet);
            File predictResponseSheet = null;
            if (getDataSheetPredictResponseFile() != null) {
                predictResponseSheet = new File(resultsDirectory + request.getSession().getId()
                        + "/" + getDataSheetPredictResponseFileFileName());
                FileUtils.copyFile(getDataSheetPredictResponseFile(), predictResponseSheet);
            }

            LOG.log(Level.INFO, "Predictor: {0}", getDataSheetPredictorFileFileName());
            LOG.log(Level.INFO, "Type: {0}", getPredictorType());
            LOG.log(Level.INFO, "Response: {0}", getDataSheetResponseFileFileName());
            LOG.log(Level.INFO, "Type: {0}", getResponseType());
            //File to predict the response for
            LOG.log(Level.INFO, "Test: {0}", getDataSheetPredictResponseFileFileName());

            //validate the correctness of the format of the excelsheet.
            if (!responseSheet.getName().contains("csv") && !predictorSheet.getName().contains("csv")) {
                //FIXME: one of the files is csv. No validation possible
                ValidateDataSheets.validateExcelSheets(responseSheet, predictorSheet);
            }

            if (predictResponseSheet != null) {
                ValidateDataSheets.validatePredictResponseSheet();
            }

            //TODO: should we prepare the list with selectionboxes here depending of the type of sheets?
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            addActionError(e.getMessage());
            return INPUT;
        } catch (DataSheetValidationException e) {
            addActionError(e.getMessage());
            e.printStackTrace();
            return INPUT;
        } catch (FileNotFoundException e) {
            addActionError(e.getMessage());
            return INPUT;
        } catch (IOException e) {
            addActionError(e.getMessage());
            return INPUT;
        } catch (Exception e) {
            addActionError(e.getMessage());
            LOG.severe("Exception caught");
            return INPUT;
        }
        LOG.info("Action: upload data completed");

        HashMap<String, String> sheets = new HashMap<String, String>(3);
        sheets.put("predictor", getDataSheetPredictorFileFileName());
        sheets.put("response", getDataSheetResponseFileFileName());
        if (getDataSheetPredictResponseFileFileName() != null) {
            sheets.put("predictResponse", getDataSheetPredictResponseFileFileName());
        }

        request.getSession().setAttribute("sheets", sheets);

        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
