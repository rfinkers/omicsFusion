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
        SQLiteConnection db = openDatbase(directory);

        SQLiteStatement st = db.prepare("CREATE TABLE user ()");
        st.step();
        st.dispose();
        //table user
        //table data
        //table methods
        //table results

        closeDatabase();
    }

    public void addUser(String directory, UserList userList) throws SQLiteException {
        SQLiteConnection db = openDatbase(directory);
        SQLiteStatement st = db.prepare("INSERT INTO user ()");
        st.bind(1, userList.getUserName());
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

    public void readSgeJobStatus(String directory){
        //if not all completed, check status from SGE.
    }

    


}
