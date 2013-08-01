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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
                    + "predictorID VARCHAR(7), "
                    + "predictor_name VARCHAR(75), "
                    + "genotype_name VARCHAR(75), "
                    + "genotypeID INTEGER(4), "
                    + "observation FLOAT(10,5), "
                    + "PRIMARY KEY (predictorID, genotypeID))");
            //table response
            statement.executeUpdate("CREATE TABLE response  ("
                    + "traitID VARCHAR(7), "
                    + "trait_name VARCHAR(75), "
                    + "genotype_name VARCHAR(75), "
                    + "genotypeID INTEGER(4), "
                    + "observation FLOAT(10,5), "
                    + "PRIMARY KEY (traitID, genotypeID))");
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
                    + "observationID VARCHAR(7), "
                    + "traitID VARCHAR(7), "
                    + "method_name TEXT, "
                    + "value REAL, "
                    + "sd REAL, "
                    + "rank REAL,"
                    + "PRIMARY KEY (traitID, observationID,method_name))");
            //table predictors
            statement.executeUpdate("CREATE TABLE responseVariables ("
                    + "traitID VARCHAR(7), "
                    + "response TEXT,"
                    + "PRIMARY KEY (traitID))");
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
            HashMap<String, String> responseVariables,
            String directory) throws SQLException, ClassNotFoundException {
        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();

        for (DataPointDataType dataPointDataType : pdp) {
            statement.addBatch("INSERT INTO predictor "
                    + "(genotypeID, genotype_name, predictorID, predictor_name, observation) "
                    + "values ('" + dataPointDataType.getGenotypeID()
                    + "','" + dataPointDataType.getGenotypeName().trim()
                    + "','" + dataPointDataType.getObservationID().trim()
                    + "','" + dataPointDataType.getObservationName().trim()
                    + "','" + dataPointDataType.getObservation() + "')");
        }
        db.setAutoCommit(false);
        int[] predictorStatus = statement.executeBatch();
        db.commit();

        System.out.println("Status : " + predictorStatus[0]);
        statement.clearBatch();

        for (DataPointDataType dataPointDataType : rdp) {
            statement.addBatch("INSERT INTO response "
                    + "(genotypeID, genotype_name, traitID, trait_name, observation) "
                    + "values ('" + dataPointDataType.getGenotypeID()
                    + "','" + dataPointDataType.getGenotypeName().trim()
                    + "','" + dataPointDataType.getObservationID().trim()
                    + "','" + dataPointDataType.getObservationName().trim()
                    + "','" + dataPointDataType.getObservation() + "')");
        }
        db.setAutoCommit(false);
        int[] responseStatus = statement.executeBatch();
        db.commit();
        System.out.println("Status : " + responseStatus[0]);
        statement.clearBatch();

        System.out.println("resp lenght: " + responseVariables.size()
                + " value: " + responseVariables.get(0));

        db.setAutoCommit(true);
        for (Map.Entry pairs : responseVariables.entrySet()) {
            System.out.println(pairs.getKey() + " - Variable: " + pairs.getValue());
            statement.executeUpdate("INSERT INTO responseVariables "
                    + "(traitID, response) values "
                    + "('" + pairs.getKey() + "','" + pairs.getValue() + "')"
                    );
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
        ArrayList<SummaryResults> results = new ArrayList<>();
        SummaryResults summaryResults = null;
        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();

        java.util.Date date = new java.util.Date();
        System.out.println("Timestamp: " + new Timestamp(date.getTime()));

        //TODO: query is extremely slow. Optimze!
        try {
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT "
                    + "observationID, r.traitID AS responseID, response, "
                    + "predictor_name, method_name, value, sd, rank "
                    + "FROM results r "
                    + "INNER JOIN responseVariables t ON r.traitID=t.traitID "
                    + "INNER JOIN predictor p ON r.observationID = p.predictorID "
                    + "WHERE t.traitID='" + responseVariable.trim() + "' "
                    + "ORDER BY observationID, r.traitID, method_name");
            java.util.Date date2 = new java.util.Date();
            System.out.println("Timestamp: " + new Timestamp(date2.getTime()));
            System.out.println("Response Variable; " + responseVariable);
            while (resultSet.next()) {
                summaryResults = new SummaryResults(
                        resultSet.getString("responseID"),//Results
                        resultSet.getString("response"),//ResponseVariables
                        resultSet.getString("observationID"),//Results
                        resultSet.getString("predictor_name"),//Predictor
                        resultSet.getString("method_name"),//Results
                        resultSet.getDouble("value"),//Results
                        resultSet.getDouble("sd"),//Results
                        resultSet.getDouble("rank"));//Results
                results.add(summaryResults);
            }
            java.util.Date date3 = new java.util.Date();
            System.out.println("Timestamp: " + new Timestamp(date3.getTime()));
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
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
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
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<XYScatterDataType> getObservationsForPredictorAndResponse(
            String directory, String preditor, String response)
            throws SQLException, ClassNotFoundException {

        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();
        List<XYScatterDataType> resultList;

        try {
            ResultSet resultSet = statement.executeQuery("SELECT "
                    + "r.genotype_name AS genotype, "
                    + "r.observation AS resp, "
                    + "trait_name, "
                    + "p.observation AS pred, "
                    + "predictor_name "
                    + "FROM predictor p, response r "
                    + "WHERE r.genotypeID = p.genotypeID "
                    + "AND p.predictorID = "
                    //TODO: predictorID should start with p... Build a check for this?
                    + "'" + preditor.trim() + "' "
                    + "ORDER BY pred, resp ");

            //TODO: initiate initial capacity
            resultList = new ArrayList<>();

            XYScatterDataType data;
            while (resultSet.next()) {
                data = new XYScatterDataType();
                data.setResponseValue(resultSet.getDouble("resp"));
                data.setResponseVariable(resultSet.getString("trait_name"));
                data.setPredictorValue(resultSet.getDouble("pred"));
                data.setPredictorVariable(resultSet.getString("predictor_name"));
                data.setGenotypeName(resultSet.getString("genotype"));
                resultList.add(data);
            }
        } finally {
            closeDatabase(db);
        }

        return resultList;
    }

    public HashMap<String, String> getResponseNames(String directory)
            throws SQLException, ClassNotFoundException {
        HashMap<String, String> result = new HashMap<>();
        Connection db = openDatabase(directory);
        Statement statement = prepareStatement();

        try {
            ResultSet rs = statement.executeQuery("SELECT * "
                    + "FROM responseVariables ");
            while (rs.next()) {
                result.put(rs.getString("traitID"), rs.getString("response"));
            }
        } finally {
            closeDatabase(db);
        }
        return result;
    }
}
