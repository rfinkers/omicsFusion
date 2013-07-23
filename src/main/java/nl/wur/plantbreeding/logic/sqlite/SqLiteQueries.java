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
package nl.wur.plantbreeding.logic.sqlite;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import nl.wur.plantbreeding.omicsfusion.datatypes.DataPointDataType;
import nl.wur.plantbreeding.omicsfusion.datatypes.SummaryResults;
import nl.wur.plantbreeding.omicsfusion.datatypes.XYScatterDataType;
import nl.wur.plantbreeding.omicsfusion.entities.UserList;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 *
 * @author Richard Finkers
 */
public class SqLiteQueries extends SqLiteHelper {

    /**
     * Initialize a new SQLite database in the specified directory.
     *
     * @param directory
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void initializeDatabase(String directory)
            throws SQLException, ClassNotFoundException {
        //Make sure that the directory exists.
        if (!new File(directory).exists()) {
            new File(directory).mkdir();
        }

        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();

        System.out.println("Initialize SQLite database");

        try {
            //table user
            statement.executeUpdate("CREATE TABLE user ("
                    + "user_name VARCHAR(75) PRIMARY KEY ASC, "
                    + "email VARCHAR(75), affiliation VARCHAR(75), "
                    + "country VARCHAR(75), "
                    + "run_complete BOOLEAN, "
                    + "date_created DATE, "
                    + "last_update TIMESTAMP)");
            //table file names.
            statement.executeUpdate("CREATE TABLE file_names ("
                    + "predictor_name VARCHAR(75), "
                    + "response_name VARCHAR(75), "
                    + "predict_response_name VARCHAR(75), "
                    + "predictor_type VARCHAR(75), "
                    + "response_type VARCHAR(75))");
            //table data
            statement.executeUpdate("CREATE TABLE predictor ("
                    + "variableID INTEGER(4), "
                    + "variable_name VARCHAR(75), "
                    + "genotype_name VARCHAR(75), "
                    + "observation FLOAT(10,5))");
            //table response
            statement.executeUpdate("CREATE TABLE response  ("
                    + "variableID INTEGER(4), "
                    + "variable_name VARCHAR(75), "
                    + "genotype_name VARCHAR(75), "
                    + "observation FLOAT(10,5))");
            //table methods
            statement.executeUpdate("CREATE TABLE methods ("
                    + "method_name VARCHAR(75), "
                    + "sge_id INTEGER(7),"
                    + "completed BOOLEAN,"
                    + "completed_time TIMESTAMP)");
            //table ontologies
            statement.executeUpdate("CREATE TABLE ontology ("
                    + "variable_name VARCHAR(75), "
                    + "ontology_id VARCHAR(75))");
            //table results
            statement.executeUpdate("CREATE TABLE results ("
                    + "predictor TEXT, "
                    + "response TEXT, "
                    + "method_name TEXT, "
                    + "value REAL, "
                    + "sd REAL, "
                    + "rank REAL)");
            //table predictors
            statement.executeUpdate("CREATE TABLE responseVariables ("
                    + "counter INTEGER, "
                    + "response TEXT)");
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                closeDatabase(db);
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }

    /**
     * Add user details to the database.
     *
     * @param directory Name of the directory.
     * @param userList User data.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void addUser(String directory, UserList userList)
            throws SQLException, ClassNotFoundException {

        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();

        System.out.println("Insert data in user table.");
        try {
            String sql = "INSERT INTO user "
                    + "(user_name, email,affiliation,country,date_created) "
                    + "values "
                    + "('" + userList.getUserName() + "','"
                    + userList.getEmail() + "','"
                    + userList.getAffiliation() + "','"
                    + userList.getCountry() + "','"
                    + Calendar.DAY_OF_MONTH + "-" + Calendar.MONTH
                    + "-" + Calendar.YEAR + "')";
            statement.execute(sql);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                closeDatabase(db);
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
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
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void uploadDataNameAndType(String directory, String predictorName,
            String predictorType, String responseName, String responseType,
            String predictResponseName)
            throws SQLException, ClassNotFoundException {

        //add names and types to the database
        //TODO: take from reading the excel sheet.
        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();

        try {
            statement.executeUpdate("INSERT INTO file_names "
                    + "(predictor_name, response_name, Predict_response_name, "
                    + "predictor_type, response_type) values "
                    + "('" + predictorName + "','"
                    + responseName + "','"
                    + predictResponseName + "','"
                    + responseType + "','"
                    + predictorType + "')");

        } finally {
            closeDatabase(db);
        }
    }

    /**
     *
     * @param rdp
     * @param pdp
     * @param responseVariables
     * @param directory
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void loadExcelData(List<DataPointDataType> rdp,
            List<DataPointDataType> pdp,
            List<String> responseVariables,
            String directory) throws SQLException, ClassNotFoundException {
        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();

        for (DataPointDataType dataPointDataType : pdp) {
            statement.addBatch("INSERT INTO predictor "
                    + "(genotype_name, variable_name, observation) "
                    + "values ('" + dataPointDataType.getGenotypeName().trim()
                    + "','" + dataPointDataType.getTraitName().trim()
                    + "','" + dataPointDataType.getObservation() + "')");
        }
        db.setAutoCommit(false);
        int[] predictorStatus = statement.executeBatch();
        db.commit();

        System.out.println("Status : " + predictorStatus[0]);
        statement.clearBatch();

        for (DataPointDataType dataPointDataType : rdp) {
            statement.addBatch("INSERT INTO response "
                    + "(genotype_name, variable_name, observation) "
                    + "values ('" + dataPointDataType.getGenotypeName().trim()
                    + "','" + dataPointDataType.getTraitName().trim()
                    + "','" + dataPointDataType.getObservation() + "')");
        }
        db.setAutoCommit(false);
        int[] responseStatus = statement.executeBatch();
        db.commit();
        System.out.println("Status : " + responseStatus[0]);
        statement.clearBatch();

        System.out.println("resp lenght: " + responseVariables.size()
                + " value: " + responseVariables.get(0));
        int i = 1;
        db.setAutoCommit(true);
        for (String responseVariable : responseVariables) {
            System.out.println(i + " - Variable: " + responseVariable);
            statement.executeUpdate("INSERT INTO responseVariables "
                    + "(counter, response) values "
                    + "(" + i + ",'" + responseVariable + "')");
            i++;
        }
        //int[] variableStatus = statement.executeBatch();
        //statement.clearBatch();

        closeDatabase(db);

    }

    public void addMethods(String directory, List<String> methods)
            throws SQLException, ClassNotFoundException {
        //add list of methods to the database

        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();

        try {
            for (String method : methods) {
                statement.executeUpdate("INSERT INTO methods "
                        + "(method_name) values ('" + method + "')");
            }
        } finally {
            closeDatabase(db);
        }
    }

    public void addSgeId(String directory,
            HashMap<String, Integer> jobIds)
            throws SQLException, ClassNotFoundException {
        //update methdods with SGE run ID.
        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();

        try {
            Iterator<String> iterator = jobIds.keySet().iterator();
            while (iterator.hasNext()) {
                String method = iterator.next();
                if (!Constants.SESSIONINFO.equals(method)) {
                    System.out.println("Method: " + method
                            + " ID: " + jobIds.get(method));
                    statement.executeUpdate("UPDATE methods "
                            + "SET sge_id =  " + jobIds.get(method) + " "
                            + "WHERE method_name = '" + method + "'");
                }
            }
        } finally {
            closeDatabase(db);

        }
    }

    /**
     * Read the SGE job status.
     *
     * @param directory
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void readSgeJobStatus(String directory)
            throws SQLException, ClassNotFoundException {
        //if not all completed, check status from SGE.
        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();

        try {
            statement.executeUpdate("INSERT INTO predictor "
                    + "(genotype_name, variable_name, observation) "
                    + "values (?,?,?)");

        } finally {
            closeDatabase(db);
        }
    }

    /**
     * Reads the results summaries from the database.
     *
     * @param directory
     * @param responseVariable Name of the response variable
     * @return A list containing the stored results.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<SummaryResults> readSummaryResults(
            String directory, String responseVariable)
            throws SQLException, ClassNotFoundException {
        ArrayList<SummaryResults> results = new ArrayList<SummaryResults>();
        SummaryResults summaryResults = null;
        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM results "
                    + "WHERE response='" + responseVariable.trim() + "' "
                    + "ORDER BY predictor, response, method_name");
            while (resultSet.next()) {
                summaryResults = new SummaryResults(
                        resultSet.getString("predictor"),
                        resultSet.getString("response"),
                        resultSet.getString("method_name"),
                        resultSet.getDouble("value"),
                        resultSet.getDouble("sd"),
                        resultSet.getDouble("rank"));
                results.add(summaryResults);
            }
        } finally {
            closeDatabase(db);
        }
        return results;
    }

    /**
     * Get the name of file containing the response sheet.
     *
     * @param directory
     * @return Name of the response sheet.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public String getResponseSheetName(String directory)
            throws SQLException, ClassNotFoundException {
        String result = "";
        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();

        try {
//            statement.executeUpdate("SELECT response_name "
//                    + "FROM file_names ");
//            while (stm.step()) {
//                result = stm.columnString(0);
//            }
        } finally {
            closeDatabase(db);
        }
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
            throws SQLException, ClassNotFoundException {
        String result = "";
        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();

        try {
//            statement.executeUpdate("SELECT predictor_name "
//                    + "FROM file_names ");
//            while (stm.step()) {
//                result = stm.columnString(0);
//            }
        } finally {
            closeDatabase(db);
        }
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
    public List<XYScatterDataType> getObservationsForPredictorAndResponse(
            String directory, String preditor, String response)
            throws SQLException, ClassNotFoundException {

        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();
        List<XYScatterDataType> resultList;

        try {
            ResultSet resultSet = statement.executeQuery("SELECT "
                    //+ "response.genotype_name, "
                    + "response.observation AS resp, "
                    + "predictor.observation AS pred "
                    + "FROM predictor, response "
                    + "WHERE response.genotype_name = predictor.genotype_name "
                    + "AND predictor.variable_name LIKE "
                    + "'%" + preditor.trim() + "' "
                    + "ORDER BY pred, resp ");
//            //FIXME: LIKE is temp fix for spaces at the beginning of the name in db.
            int i = 0;

            //TODO: initiate initial capacity
            resultList = new ArrayList<XYScatterDataType>();

            XYScatterDataType data;
            while (resultSet.next()) {
                data = new XYScatterDataType();
                //TODO: add genotypename to the query.
                //data.setGenotypeName(resultSet.getString("genotype"));
                data.setResponseValue(resultSet.getDouble("resp"));
                data.setResponseVariable(response);
                data.setPredictorValue(resultSet.getDouble("pred"));
                data.setPredictorVariable(preditor);

                resultList.add(data);

                i++;
                System.out.println("Counter: " + i
                        + " Response: " + Double.toString(resultSet.getDouble("resp"))
                        + " Predictor: " + Double.toString(resultSet.getDouble("pred")));
            }

        } finally {
            closeDatabase(db);
        }

        return resultList;
    }

    public ArrayList<String> getResponseNames(String directory)
            throws SQLException, ClassNotFoundException {
        ArrayList<String> result = new ArrayList<String>();
        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();

        try {
            ResultSet rs = statement.executeQuery("SELECT response "
                    + "FROM responseVariables ");
            while (rs.next()) {
                result.add(rs.getString("response"));
            }
        } finally {
            closeDatabase(db);
        }
        return result;
    }
}
