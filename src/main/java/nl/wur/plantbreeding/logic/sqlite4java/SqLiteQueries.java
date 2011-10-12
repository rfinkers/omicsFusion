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
import java.util.Calendar;
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
        SQLiteStatement st = db.prepare("CREATE TABLE user ("
                + "user_name VARCHAR(75) PRIMARY KEY ASC, "
                + "email VARCHAR(75), affiliation VARCHAR(75), "
                + "country VARCHAR(75), "
                + "run_complete BOOLEAN, "
                + "date_created DATE, "
                + "last_update TIMESTAMP)");
        st.step();
        st.dispose();

        SQLiteStatement cf = db.prepare("CREATE TABLE file_names ("
                + "predictor_name VARCHAR(75), "
                + "response_name VARCHAR(75), "
                + "predict_response_name VARCHAR(75), "
                + "predictor_type VARCHAR(75), "
                + "response_type VARCHARll(75))");
        cf.step();
        cf.dispose();

        //table data
        SQLiteStatement cp = db.prepare("CREATE TABLE predictor ("
                + "variable_name VARCHAR(75), "
                + "genotype_name VARCHAR(75), "
                + "observation FLOAT(10,5))");
        cp.step();
        cp.dispose();

        SQLiteStatement cr = db.prepare("CREATE TABLE response  ("
                + "variable_name VARCHAR(75), "
                + "genotype_name VARCHAR(75), "
                + "observation FLOAT(10,5))");
        cr.step();
        cr.dispose();

        //table methods
        SQLiteStatement cm = db.prepare("CREATE TABLE methods ("
                + "method_name VARCHAR(75), "
                + "completed BOOLEAN)");
        cm.step();
        cm.dispose();

        //table results
        SQLiteStatement cs = db.prepare("CREATE TABLE results ("
                + "method_name VARCHAR(75), "
                + "value FLOAT(10,5))");
        cs.step();
        cs.dispose();

        closeDatabase();
    }

    /**
     * Add user details to the database.
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
        st.bind(5, Calendar.DATE);
        st.step();
        st.dispose();
        closeDatabase();
    }

    /**
     * Meta information concerning the data upload.
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
        SQLiteStatement st = db.prepare("INSERT INTO file_namers "
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

    public void addMethods(String directory) {
        //add list of methods to the database
    }

    public void addSgeId(String directory) {
        //update methdods with SGE run ID.
    }

    public void readSgeJobStatus(String directory) {
        //if not all completed, check status from SGE.
    }
}
