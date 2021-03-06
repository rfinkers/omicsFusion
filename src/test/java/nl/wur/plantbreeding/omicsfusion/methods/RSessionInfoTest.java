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
package nl.wur.plantbreeding.omicsfusion.methods;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author finke002
 */
public class RSessionInfoTest {

    public RSessionInfoTest() {
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
     * Test of getRequiredLibraries method, of class RSessionInfo.
     */
    @Test
    public void testGetRequiredLibraries() {
        System.out.println("getRequiredLibraries");
        RSessionInfo instance = new RSessionInfo();
        String expResult = "";
        String result = instance.getRequiredLibraries();
        assertNotNull(result);
    }

    /**
     * Test of getAnalysis method, of class RSessionInfo.
     */
    @Test
    public void testGetAnalysis() {
        System.out.println("getAnalysis");
        RSessionInfo instance = new RSessionInfo();
        String expResult = "";
        String result = instance.getAnalysis("test");
        assertNotNull(result);
    }

    /**
     * Test of writeResults method, of class RSessionInfo.
     */
    @Test
    public void testWriteResults() {
        System.out.println("writeResults");
        RSessionInfo instance = new RSessionInfo();
        String expResult = "";
        String result = instance.writeResultsToDisk();
        assertNotNull(result);
    }

    /**
     * Test of preProcessMatrix method, of class RSessionInfo.
     */
    @Test
    public void testPreProcessMatrix() {
        System.out.println("preProcessMatrix");
        RSessionInfo instance = new RSessionInfo();
        String expResult = "";
        String result = instance.preProcessMatrix("");
        assertNotNull(result);
    }

    /**
     * Test of combineResults method, of class RSessionInfo.
     */
    @Test
    public void testCombineResults() {
        System.out.println("combineResults");
        RSessionInfo instance = new RSessionInfo();
        String expResult = "";
        String result = instance.combineResults();
        assertNotNull(result);
    }

    /**
     * Test of getTrainingSets method, of class RSessionInfo.
     */
    @Test
    public void testGetTrainingSets() {
        System.out.println("getTrainingSets");
        RSessionInfo instance = new RSessionInfo();
        String expResult = "";
        String result = instance.getTrainingSets("");
        assertNotNull(result);
    }

    /**
     * Test of getRowMeansAndSD method, of class RSessionInfo.
     */
    @Test
    public void testGetRowMeansAndSD() {
        System.out.println("getRowMeansAndSD");
        RSessionInfo instance = new RSessionInfo();
        String expResult = "";
        String result = instance.getRowMeansAndSD();
        assertNotNull(result);
    }

    /**
     * Test of initializeResultObjects method, of class RSessionInfo.
     */
    @Test
    public void testInitializeResultObjects() {
        System.out.println("initializeResultObjects");
        RSessionInfo instance = new RSessionInfo();
        String expResult = "";
        String result = instance.initializeResultObjects();
        assertNotNull(result);;
    }
}
