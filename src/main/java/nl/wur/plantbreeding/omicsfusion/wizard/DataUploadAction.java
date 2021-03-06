/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.logic.sqlite.SqLiteQueries;
import nl.wur.plantbreeding.omicsfusion.email.ExceptionEmail;
import nl.wur.plantbreeding.omicsfusion.excel.DataSheetValidationException;
import static nl.wur.plantbreeding.omicsfusion.excel.UploadDataSheets.uploadExcelSheets;
import nl.wur.plantbreeding.omicsfusion.excel.ValidateDataSheets;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;
import nl.wur.plantbreeding.omicsfusion.utils.ServletUtils;
import nl.wur.plantbreeding.omicsfusion.utils.WriteFile;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Action which handles upload of the data sheets and validates the if they are
 * conform the requried standards. Data will be stored in a database?
 *
 * @author Richard Finkers
 * @version 1.0
 */
@ParentPackage(value = "dataUpload")
@InterceptorRef("jsonValidationWorkflowStack")
@Results({
    @Result(location = "/submit/uploadExcel.jsp", name = "input"),
    @Result(location = "/submit/methodSelection.jsp", name = "success"),
    @Result(location = "/submit/uploadExcel.jsp", name = "error")
})
public class DataUploadAction extends DataUploadValidationForm
        implements ServletRequestAware {

    private static final long serialVersionUID = 170610L;
    /**
     * The logger.
     */
    private static final Logger LOG
            = Logger.getLogger(DataUploadAction.class.getName());
    /**
     * the request.
     */
    private HttpServletRequest request;

    @Override
    public String execute() throws Exception {
        try {

            LOG.info("Execute upload");

            //Check if the sheet is excel or csv. Set the name accordingly
            File responseSheet = new File(ServletUtils.getResultsDir(request)
                    + "/" + getDataSheetResponseFileFileName());
            File predictorSheet = new File(ServletUtils.getResultsDir(request)
                    + "/" + getDataSheetPredictorFileFileName());
            //Copy the uploaded excel sheets to a temporary directory
            FileUtils.copyFile(getDataSheetPredictorFile(), predictorSheet);
            FileUtils.copyFile(getDataSheetResponseFile(), responseSheet);
            File predictResponseSheet = null;
            if (getDataSheetPredictResponseFile() != null) {
                predictResponseSheet = new File(ServletUtils.getResultsDir(request)
                        + "/" + getDataSheetPredictResponseFileFileName());
                FileUtils.copyFile(getDataSheetPredictResponseFile(),
                        predictResponseSheet);
            }

            //prepare a file with the names of the input sheets.
            //Write the names to the database.
            writeNamesToDB();

            //validate the correctness of the format of the excelsheet.
            ValidateDataSheets.validateExcelSheets(responseSheet,
                    predictorSheet);
            try {
                uploadExcelSheets(responseSheet, getResponseType(),
                        predictorSheet, getPredictorType(),
                        ServletUtils.getResultsDir(request));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                addActionError(e.getMessage());
                LOG.info("Excel to Database");
                return INPUT;
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
        } catch (SQLException | ClassNotFoundException e) {
            addActionError(e.getMessage());
            LOG.severe("Exception caught");
            ExceptionEmail.SendExceptionEmail(e);
            return INPUT;
        }
        LOG.info("Action: upload data completed");

        HashMap<String, String> sheets = new HashMap<>(3);
        sheets.put("predictor", getDataSheetPredictorFileFileName());
        sheets.put("response", getDataSheetResponseFileFileName());
        if (getDataSheetPredictResponseFileFileName() != null) {
            sheets.put("predictResponse",
                    getDataSheetPredictResponseFileFileName());
        }

        request.getSession().setAttribute(Constants.DataUpload, sheets);

        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Write file names to the database.
     *
     * @throws SQLiteException
     */
    private void writeNamesToDB() throws SQLException, ClassNotFoundException {
        SqLiteQueries sql = new SqLiteQueries();
        sql.uploadDataNameAndType(ServletUtils.getResultsDir(request),
                getDataSheetPredictorFileFileName(),
                getResponseType(),
                getDataSheetResponseFileFileName(),
                getPredictorType(),
                getDataSheetPredictResponseFileFileName());
    }
}
