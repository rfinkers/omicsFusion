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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 *
 * @author Richard Finkers
 */
public class SqLiteHelper {

    /**
     * SQLite db
     */
    private Connection db;

    /**
     * Open an sqlite database. The database will be created if it does not
     * exist.
     *
     * @param directory directory and name of the database to be opened.
     * @return Connection tot the database.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    protected Connection openDatabase(String directory)
            throws SQLException, ClassNotFoundException {

        if (directory == null) {
            directory = "/tmp";
        }
        System.out.println("Directory: " + directory);

        // READ_UNCOMMITTED mode works only in shared_cache mode.
        Properties prop = new Properties();
        prop.setProperty("shared_cache", "true");

        Class.forName("org.sqlite.JDBC");
        //TODO: slashes for windows
        db = DriverManager.getConnection("jdbc:sqlite:"
                + directory + "/" + Constants.OMICSFUSION_DB, prop);

        db.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

        //db.prepareStatement("ATTACH DATABASE omicsFusion");
        return db;
    }

    /**
     * Prepare database statement.
     *
     * @return the database statement.
     * @throws java.sql.SQLException error creating the statement.
     */
    protected Statement prepareStatement() throws SQLException {
        Statement statement = db.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
        return statement;
    }

    /**
     * Close the current database connection.
     *
     * @param connection
     * @throws SQLException
     */
    protected void closeDatabase(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
