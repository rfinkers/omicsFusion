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
package nl.wur.plantbreeding.logic.sqlite4java;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import java.io.File;
import java.util.*;
import nl.wur.plantbreeding.omicsfusion.datatypes.DataPointDataType;
import nl.wur.plantbreeding.omicsfusion.datatypes.SummaryResults;
import nl.wur.plantbreeding.omicsfusion.datatypes.XYScatterDataType;
import nl.wur.plantbreeding.omicsfusion.entities.UserList;

/**
 *
 * @author Richard Finkers
 */
public class SqLiteQueries extends SqLiteHelper {

    /**
     * Initialize a new SQLite database in the specified directory.
     *
     * @param directory
     * @throws SQLiteException
     */
    public void initializeDatabase(String directory) throws SQLiteException {
        //Make sure that the directory exists.
        if (!new File(directory).exists()) {
            new File(directory).mkdir();
        }

        SQLiteConnection db = openDatabase(directory);

        //table user
        SQLiteStatement st = null;
        try {
            st = db.prepare("CREATE TABLE user ("
                    + "user_name VARCHAR(75) PRIMARY KEY ASC, "
                    + "email VARCHAR(75), affiliation VARCHAR(75), "
                    + "country VARCHAR(75), "
                    + "run_complete BOOLEAN, "
                    + "date_created DATE, "
                    + "last_update TIMESTAMP)");
            st.step();
        }
        finally {
            st.dispose();
        }

        SQLiteStatement cf = null;
        try {
            cf = db.prepare("CREATE TABLE file_names ("
                    + "predictor_name VARCHAR(75), "
                    + "response_name VARCHAR(75), "
                    + "predict_response_name VARCHAR(75), "
                    + "predictor_type VARCHAR(75), "
                    + "response_type VARCHAR(75))");
            cf.step();
        }
        finally {
            st.dispose();
        }

        //table data

        SQLiteStatement cp = db.prepare("CREATE TABLE predictor ("
                + "variable_name VARCHAR(75), "
                + "genotype_name VARCHAR(75), "
                + "observation FLOAT(10,5))");
        try {
            cp.step();
        }
        finally {
            cp.dispose();
        }

        SQLiteStatement cr = db.prepare("CREATE TABLE response  ("
                + "variable_name VARCHAR(75), "
                + "genotype_name VARCHAR(75), "
                + "observation FLOAT(10,5))");
        try {
            cr.step();
        }
        finally {
            cr.dispose();
        }

        //table methods
        SQLiteStatement cm = db.prepare("CREATE TABLE methods ("
                + "method_name VARCHAR(75), "
                + "sge_id INTEGER(7),"
                + "completed BOOLEAN,"
                + "completed_time TIMESTAMP)");
        try {
            cm.step();
        }
        finally {
            cm.dispose();
        }

        //table ontologies
        SQLiteStatement cn = db.prepare("CREATE TABLE ontology ("
                + "variable_name VARCHAR(75), "
                + "ontology_id VARCHAR(75))");
        try {
            cn.step();
        }
        finally {
            cn.dispose();
        }

        //table results
        SQLiteStatement cs = db.prepare("CREATE TABLE results ("
                + "predictor TEXT, "
                + "response TEXT, "
                + "method_name TEXT, "
                + "value REAL, "
                + "sd REAL, "
                + "rank REAL)");
        cs.step();
        cs.dispose();

        //table predictors
        SQLiteStatement ct = db.prepare("CREATE TABLE responseVariables ("
                + "counter INTEGER, "
                + "response TEXT)");
        try {
            ct.step();
        }
        finally {
            ct.dispose();
        }

        closeDatabase();
    }

    /**
     * Add user details to the database.
     *
     * @param directory Name of the directory.
     * @param userList User data.
     * @throws SQLiteException Error.
     */
    public void addUser(String directory, UserList userList)
            throws SQLiteException {
        SQLiteConnection db = openDatabase(directory);
        SQLiteStatement st = db.prepare("INSERT INTO user "
                + "(user_name, email,affiliation,country,date_created) values "
                + "(?,?,?,?,?)");
        st.bind(1, userList.getUserName());
        st.bind(2, userList.getEmail());
        st.bind(3, userList.getAffiliation());
        st.bind(4, userList.getCountry());
        st.bind(5, Calendar.DAY_OF_MONTH + "-" + Calendar.MONTH
                + "-" + Calendar.YEAR);
        st.step();
        st.dispose();
        closeDatabase();
    }

    /**
     * Meta information concerning the data upload.
     *
     * @param directory Name of the directory.
     * @param predictorName Name of the predictor.
     * @param predictorType Type of the predictor.
     * @param responseName Name of the response.
     * @param responseType Type of the response.
     * @param predictResponseName Name of the predict response file.
     * @throws SQLiteException SQL exception.
     */
    public void uploadDataNameAndType(String directory, String predictorName,
            String predictorType, String responseName, String responseType,
            String predictResponseName) throws SQLiteException {
        SQLiteConnection db = openDatabase(directory);
        //add names and types to the database
        //TODO: take from reading the excel sheet.
        SQLiteStatement st = db.prepare("INSERT INTO file_names "
                + "(predictor_name, response_name, Predict_response_name, "
                + "predictor_type, response_type) values (?,?,?,?,?)");
        st.bind(1, predictorName);
        st.bind(2, responseName);
        st.bind(3, predictResponseName);
        st.bind(4, responseType);
        st.bind(5, predictorType);
        st.step();
        st.dispose();
        //check for annotations and upload

        //add all data to the database
        closeDatabase();
    }

    /**
     *
     * @param rdp
     * @param pdp
     * @param responseVariables
     * @param directory
     * @throws SQLiteException
     */
    public void loadExcelData(List<DataPointDataType> rdp,
            List<DataPointDataType> pdp,
            List<String> responseVariables,
            String directory) throws SQLiteException {
        SQLiteConnection db = openDatabase(directory);

        db.exec("BEGIN");
        for (DataPointDataType dataPointDataType : pdp) {
            SQLiteStatement pred = db.prepare("INSERT INTO predictor "
                    + "(genotype_name, variable_name, observation) "
                    + "values (?,?,?)");
            try {
                pred.bind(1, dataPointDataType.getGenotypeName());
                pred.bind(2, dataPointDataType.getTraitName());
                pred.bind(3, dataPointDataType.getObservation());
                pred.step();
            }
            finally {
                pred.dispose();
            }
        }
        db.exec("COMMIT");

        db.exec("BEGIN");
        for (DataPointDataType dataPointDataType : rdp) {
            SQLiteStatement resp = db.prepare("INSERT INTO response "
                    + "(genotype_name, variable_name, observation) "
                    + "values (?,?,?)");
            try {
                resp.bind(1, dataPointDataType.getGenotypeName());
                resp.bind(2, dataPointDataType.getTraitName());
                resp.bind(3, dataPointDataType.getObservation());
                resp.step();
            }
            finally {
                resp.dispose();
            }

        }
        db.exec("COMMIT");

        db.exec("BEGIN");
        int i = 1;
        for (String responseVariable : responseVariables) {
            SQLiteStatement resp = db.prepare("INSERT INTO responseVariables "
                    + "(counter, response) VALUES (?,?)");
            try {
                resp.bind(1, i);
                resp.bind(2, responseVariable);
                resp.step();
            }
            finally {
                resp.dispose();
            }
            i++;
        }
        db.exec("COMMIT");

        closeDatabase();

    }

    public void addMethods(String directory, List<String> methods)
            throws SQLiteException {
        //add list of methods to the database
        SQLiteConnection db = openDatabase(directory);

        for (String method : methods) {
            SQLiteStatement stm = db.prepare("INSERT INTO methods "
                    + "(method_name) values (?)");
            try {
                stm.bind(1, method);
                stm.step();
            }
            finally {
                stm.dispose();
            }
        }
        closeDatabase();
    }

    public void addSgeId(String directory,
            HashMap<String, Integer> jobIds) throws SQLiteException {
        //update methdods with SGE run ID.
        SQLiteConnection db = openDatabase(directory);

        Iterator<String> iterator = jobIds.keySet().iterator();
        while (iterator.hasNext()) {
            SQLiteStatement stm = db.prepare("UPDATE methods "
                    + "SET sge_id = ? "
                    + "WHERE method_name = ? ");
            try {
                String method = iterator.next();
                if (!"sessionInfo".equals(method)) {
                    stm.bind(1, jobIds.get(method));
                    stm.bind(2, method);
                    stm.step();
                }
            }
            finally {
                stm.dispose();
            }
        }
        closeDatabase();
    }

    /**
     * Read the SGE job status.
     *
     * @param directory
     * @throws SQLiteException
     */
    public void readSgeJobStatus(String directory) throws SQLiteException {
        //if not all completed, check status from SGE.
        SQLiteConnection db = openDatabase(directory);
        SQLiteStatement stm = db.prepare("INSERT INTO predictor "
                + "(genotype_name, variable_name, observation) "
                + "values (?,?,?)");
        stm.step();
        stm.dispose();
        closeDatabase();
    }

    /**
     * Reads the results summaries from the database.
     *
     * @param directory
     * @param responseVariable Name of the response variable
     * @return A list containing the stored results.
     * @throws SQLiteException
     */
    public ArrayList<SummaryResults> readSummaryResults(
            String directory, String responseVariable)
            throws SQLiteException {
        ArrayList<SummaryResults> results = new ArrayList<SummaryResults>();
        SummaryResults summaryResults = null;
        SQLiteConnection db = openDatabase(directory);
        SQLiteStatement stm = db.prepare("SELECT * FROM results "
                //TODO: implement use of actual responseVariable!
                //+ "WHERE response='" + responseVariable.trim() + "' "
                + "ORDER BY predictor, method_name");
        while (stm.step()) {
            stm.columnDouble(3);
            summaryResults = new SummaryResults(stm.columnString(0),
                    stm.columnString(1), stm.columnString(2),
                    stm.columnDouble(3), stm.columnDouble(4),
                    stm.columnDouble(5));
            results.add(summaryResults);
        }
        stm.dispose();
        closeDatabase();
        return results;
    }

    /**
     * Get the name of file containing the response sheet.
     *
     * @param directory
     * @return Name of the response sheet.
     * @throws SQLiteException
     */
    public String getResponseSheetName(String directory)
            throws SQLiteException {
        String result = "";
        SQLiteConnection db = openDatabase(directory);
        SQLiteStatement stm = db.prepare("SELECT response_name "
                + "FROM file_names ");
        while (stm.step()) {
            result = stm.columnString(0);
        }
        stm.dispose();
        closeDatabase();
        return result;
    }

    /**
     * Get the name of file containing the predictor sheet.
     *
     * @param directory
     * @return The name of the predictor sheet.
     * @throws SQLiteException
     */
    public String getPredictorSheetName(String directory)
            throws SQLiteException {
        String result = "";
        SQLiteConnection db = openDatabase(directory);
        SQLiteStatement stm = db.prepare("SELECT predictor_name "
                + "FROM file_names ");
        while (stm.step()) {
            result = stm.columnString(0);
        }
        stm.dispose();
        closeDatabase();
        return result;
    }

    /**
     * Obtain a list with observations for the selected response and predictor.
     *
     * @param directory Directory containing the results.
     * @param preditor Predictor variable.
     * @param response Response variable.
     * @return A list of observations
     * @throws SQLiteException
     */
    public ArrayList<XYScatterDataType> getObservationsForPredictorAndResponse(
            String directory, String preditor, String response)
            throws SQLiteException {
        ArrayList<XYScatterDataType> resultList =
                new ArrayList<XYScatterDataType>();
        XYScatterDataType dataPoint = null;
        SQLiteConnection db = openDatabase(directory);
        SQLiteStatement stm = db.prepare("SELECT response.genotype_name, "
                + "response.observation, predictor.observation "
                + "FROM predictor, response "
                + "WHERE response.genotype_name = predictor.genotype_name "
                + "AND predictor.variable_name LIKE '%" + preditor + "'");
        //FIXME: LIKE is temp fix for spaces at the beginning of the name in db.
        while (stm.step()) {
            dataPoint = new XYScatterDataType();
            dataPoint.setGenotypeName(stm.columnString(0));
            dataPoint.setPredictorVariable(preditor);
            dataPoint.setPredictorValue(stm.columnDouble(2));
            dataPoint.setResponseVariable(response);
            dataPoint.setResponseValue(stm.columnDouble(1));
            resultList.add(dataPoint);
        }
        stm.dispose();
        closeDatabase();
        return resultList;
    }

    public ArrayList<String> getResponseNames(String directory) throws SQLiteException {
        ArrayList<String> result = new ArrayList<String>();
        SQLiteConnection db = openDatabase(directory);
        SQLiteStatement stm = db.prepare("SELECT response "
                + "FROM responseVariables ");
        while (stm.step()) {
            result.add(stm.columnString(0));
        }
        stm.dispose();
        closeDatabase();
        return result;
    }
}
