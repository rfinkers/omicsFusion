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
package nl.wur.plantbreeding.logic.sqlite;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import nl.wur.plantbreeding.omicsfusion.datatypes.SummaryResults;
import nl.wur.plantbreeding.omicsfusion.entities.UserList;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author finke002
 */
public class SqLiteQueriesTest {

    private static String directory = "/tmp/";

    public SqLiteQueriesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        File tempFile = new File("/tmp/omicsFusion.db");
        tempFile.delete();
        SqLiteQueries instance = new SqLiteQueries();
        instance.initializeDatabase(directory);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addUser method, of class sqLiteQueries.
     */
    @Test
    @Ignore("throws unexpected error. TODO")
    public void testAddUser() throws Exception {
        System.out.println("addUser");
        UserList userList = new UserList(1, "finke002", "test@test.nl",
                new Date(), new Date());
        SqLiteQueries instance = new SqLiteQueries();
        instance.addUser(directory, userList);
    }

    /**
     * Test of uploadData method, of class sqLiteQueries.
     */
    @Test
    @Ignore
    public void testUploadData() {
        System.out.println("uploadData");
        SqLiteQueries instance = new SqLiteQueries();
        //instance.uploadData(directory);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMethods method, of class sqLiteQueries.
     */
    @Test
    @Ignore
    public void testAddMethods() {
        System.out.println("addMethods");
        String directory = "";
        SqLiteQueries instance = new SqLiteQueries();
        //instance.addMethods(directory);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSgeId method, of class sqLiteQueries.
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void testAddSgeId() throws SQLException, ClassNotFoundException {
        System.out.println("addSgeId");
        SqLiteQueries instance = new SqLiteQueries();

        HashMap<String, Integer> jobIds = new HashMap<String, Integer>();
        List<String> methods = new ArrayList<String>();

        jobIds.put(Constants.SPLS, 1);
        methods.add(Constants.SPLS);

        instance.addMethods(directory, methods);
        instance.addSgeId(directory, jobIds);

    }

    /**
     * Test of readSgeJobStatus method, of class sqLiteQueries.
     */
    @Test
    @Ignore
    public void testReadSgeJobStatus() {
        System.out.println("readSgeJobStatus");
        String directory = "";
        SqLiteQueries instance = new SqLiteQueries();
        //instance.readSgeJobStatus(directory);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readSummaryResults method, of class SqLiteQueries.
     */
    @Test
    @Ignore
    public void testReadSummaryResults() throws Exception {
        System.out.println("readSummaryResults");
        String directory = "/Users/richardfinkers/NetBeansProjects/ofResults/test/";
        String responseVariable = "t1";
        SqLiteQueries instance = new SqLiteQueries();
        ArrayList<SummaryResults> expResult = null;
        ArrayList<SummaryResults> result = instance.readSummaryResults(directory, responseVariable);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
