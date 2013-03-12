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

import java.io.File;
import java.util.Date;
import nl.wur.plantbreeding.omicsfusion.entities.UserList;
import org.junit.*;
import static org.junit.Assert.fail;

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
     */
    @Test
    @Ignore
    public void testAddSgeId() {
        System.out.println("addSgeId");
        String directory = "";
        SqLiteQueries instance = new SqLiteQueries();
        //instance.addSgeId(directory);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
}
