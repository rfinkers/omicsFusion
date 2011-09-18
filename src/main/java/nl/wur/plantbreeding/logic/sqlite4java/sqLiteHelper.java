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

    public SQLiteConnection openDatbase(String directory) throws SQLiteException {
        db = new SQLiteConnection(new File("/tmp/database"));
        db.open(true);
        return db;
    }

    public void closeDatabase(){
        db.dispose();
    }
}
