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

import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author finke002
 */
public class AnalysisTest {

    public AnalysisTest() {
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
     * Test of loadPredictResponseDataSheet method, of class Analysis.
     */
    @Test
    public void testLoadPredictResponseDataSheet() {
        System.out.println("loadPredictResponseDataSheet");
        HashMap<String, String> excelSheets = new HashMap<String, String>(2);
        excelSheets.put("response", "test");
        excelSheets.put("predictor", "test");
        Analysis instance = new Analysis();

        String result = instance.loadPredictResponseDataSheet(excelSheets);
        assertNotNull(result);
    }

    /**
     * Test of preProcessMatrix method, of class Analysis.
     */
    @Test
    public void testPreProcessMatrix() {
        System.out.println("preProcessMatrix");
        Analysis instance = new Analysis();

        String result = instance.preProcessMatrix("");
        assertNotNull(result);
    }

    /**
     * Test of getTrainingSets method, of class Analysis.
     */
    @Test
    public void testGetTrainingSets() {
        System.out.println("getTrainingSets");
        Analysis instance = new Analysis();

        String result = instance.getTrainingSets("");
        assertNotNull(result);
    }

    /**
     * Test of getRequiredLibraries method, of class Analysis.
     */
    @Test
    public void testGetRequiredLibraries() {
        System.out.println("getRequiredLibraries");
        Analysis instance = new Analysis();

        String result = instance.getRequiredLibraries();
        assertNotNull(result);
    }

    /**
     * Test of initializeResultObjects method, of class Analysis.
     */
    @Test
    public void testInitializeResultObjects_0args() {
        System.out.println("initializeResultObjects");
        Analysis instance = new Analysis();

        String result = instance.initializeResultObjects();
        assertNotNull(result);
    }

    /**
     * Test of initializeResultObjects method, of class Analysis.
     */
    @Test
    public void testInitializeResultObjects_String() {
        System.out.println("initializeResultObjects");
        String analysisMethod = "";
        Analysis instance = new Analysis();

        String result = instance.initializeResultObjects(analysisMethod);
        assertNotNull(result);
    }

    /**
     * Test of getAnalysis method, of class Analysis.
     */
    @Test
    public void testGetAnalysis_0args() {
        System.out.println("getAnalysis");
        Analysis instance = new Analysis();

        String result = instance.getAnalysis("test");
        assertNotNull(result);
    }

    /**
     * Test of getAnalysis method, of class Analysis.
     */
    @Test
    public void testGetAnalysis_String() {
        System.out.println("getAnalysis");
        String analysisMethod = "";
        String responseVariable = "";
        Analysis instance = new Analysis();

        String result = instance.getAnalysis(analysisMethod, responseVariable);
        assertNotNull(result);
    }

    /**
     * Test of combineResults method, of class Analysis.
     */
    @Test
    public void testCombineResults() {
        System.out.println("combineResults");
        Analysis instance = new Analysis();

        String result = instance.combineResults();
        assertNotNull(result);
    }

    /**
     * Test of getRowMeansAndSD method, of class Analysis.
     */
    @Test
    public void testGetRowMeansAndSD() {
        System.out.println("getRowMeansAndSD");
        Analysis instance = new Analysis();

        String result = instance.getRowMeansAndSD();
        assertNotNull(result);
    }

    /**
     * Test of writeResults method, of class Analysis.
     */
    @Test
    public void testWriteResults() {
        System.out.println("writeResults");
        Analysis instance = new Analysis();

        String result = instance.writeResultsToDisk();
        assertNotNull(result);
    }

    /**
     * Test of predictResponseFromNew method, of class Analysis.
     */
    @Test
    public void testPredictResponseFromNew() {
        System.out.println("predictResponseFromNew");
        String analysisMethod = "";
        Analysis instance = new Analysis();

        String result = instance.predictResponseFromNew(analysisMethod);
        assertNotNull(result);
    }

    /**
     * Test of getAnalysisScript method, of class Analysis.
     */
    @Test
    @Ignore
    public void testGetAnalysisScript() {
        System.out.println("getAnalysisScript");
        HashMap<String, String> excelSheets = new HashMap<String, String>(2);
        excelSheets.put("response", "test");
        excelSheets.put("predictor", "test");
        Analysis instance = new Analysis();

        String result = instance.getAnalysisScript("test");
        assertNotNull(result);
    }
}
