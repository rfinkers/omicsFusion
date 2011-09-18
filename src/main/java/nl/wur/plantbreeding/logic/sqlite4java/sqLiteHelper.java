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
import java.io.File;

/**
 *
 * @author Richard Finkers
 */
public class sqLiteHelper {

    /** SQLite db */
    private SQLiteConnection db;

    /**
     * Open an sqlite database. The database will be created if it does not 
     * exist.
     * @param directory directory and name of the database to be opened.
     * @return Connection tot the database.
     * @throws SQLiteException 
     */
    protected SQLiteConnection openDatabase(String directory)
            throws SQLiteException {
        if (directory == null) {
            directory = "/tmp/database";
        }
        db = new SQLiteConnection(new File(directory));
        db.open(true);
        return db;
    }

    /**
     * Close the current database connection.
     */
    protected void closeDatabase() {
        if (db.isOpen()) {
            db.dispose();
        }
    }
}
