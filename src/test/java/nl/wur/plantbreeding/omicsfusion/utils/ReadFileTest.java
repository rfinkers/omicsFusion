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

package nl.wur.plantbreeding.omicsfusion.utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Richard Finkers
 */
public class ReadFileTest {

    public ReadFileTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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
     * Test of ReadSheetFileNames method, of class ReadFile.
     */
    @Test
    @Ignore
    public void testReadSheetFileNames() throws Exception {
        System.out.println("ReadSheetFileNames");
        String fileName = "";
        ReadFile instance = new ReadFile();
        String[] expResult = null;
        String[] result = instance.ReadSheetFileNames(fileName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ReadResponseName method, of class ReadFile.
     */
    @Test
    @Ignore
    public void testReadResponseName() throws Exception {
        System.out.println("ReadResponseName");
        String filename = "/home/finke002/omicsFusion/7aeaf644cef493d146768d0d5b06/analysis.txt";
        ReadFile instance = new ReadFile();
        String expResult = "BLUE_gm_FT2008";
        String result = instance.ReadResponseName(filename);
        assertEquals(expResult, result);
    }

}