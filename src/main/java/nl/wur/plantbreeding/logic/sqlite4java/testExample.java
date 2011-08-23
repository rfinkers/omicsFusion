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

import com.almworks.sqlite4java.SQLite;
import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import java.io.File;
import java.util.logging.Logger;

/**
 * Some example code for the sqlite4java library.
 * @author Richard Finkers
 */
public class testExample {

    /** SQLite db */
    private SQLiteConnection db;
    /** dummy variable*/
    private int minimumQuantity = 1;
    /** Logger */
    private static final Logger LOG = Logger.getLogger(testExample.class.getName());

    public void testCode() throws SQLiteException {
        //SQLite.setLibraryPath("/usr/lib/");

        db = new SQLiteConnection(new File("/tmp/database"));


        db.open(true);

        SQLiteStatement st = db.prepare("SELECT order_id FROM orders WHERE quantity >= ?");
        try {
            st.bind(1, minimumQuantity);
            while (st.step()) {
                //orders.add(st.columnLong(0));
            }
        }
        finally {
            st.dispose();
        }

        db.dispose();
    }
}
