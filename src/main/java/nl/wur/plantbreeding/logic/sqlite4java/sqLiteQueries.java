/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.wur.plantbreeding.logic.sqlite4java;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import nl.wur.plantbreeding.omicsfusion.entities.UserList;

/**
 *
 * @author Richard Finkers
 */
public class sqLiteQueries extends sqLiteHelper {

    /**
     * Initialize a new SQLite database in the specified directory.
     * @param directory
     * @throws SQLiteException
     */
    public void initializeDatabase(String directory) throws SQLiteException {
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
                + "response_type VARCHAR(75), "
                + "predict_type VARCHAR(75))");
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

    public void addUser(String directory, UserList userList) throws SQLiteException {
        SQLiteConnection db = openDatabase(directory);
        SQLiteStatement st = db.prepare("INSERT INTO user "
                + "(user_name, email) values (?,?)");
        st.bind(1, userList.getUserName());
        st.bind(2, userList.getEmail());
        st.step();
        st.dispose();
        closeDatabase();
    }

    public void uploadData(String directory) {
        //add names and types to the database
        //check for annotations and upload
        //add all data to the database
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
