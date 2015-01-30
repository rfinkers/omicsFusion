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

import static org.junit.Assert.assertNotNull;
import org.junit.*;

/**
 *
 * @author finke002
 */
public class UnivariateTest {

    public UnivariateTest() {
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
     * Test of getRequiredLibraries method, of class Univariate.
     */
    @Test
    public void testGetRequiredLibraries() {
        System.out.println("getRequiredLibraries");
        Univariate instance = new Univariate();
        String expResult = "";
        String result = instance.getRequiredLibraries();
        assertNotNull(result);
    }

    /**
     * Test of getAnalysis method, of class Univariate.
     */
    @Test
    public void testGetAnalysis() {
        System.out.println("getAnalysis");
        Univariate instance = new Univariate();
        String expResult = "";
        String result = instance.getAnalysis("");
        assertNotNull(result);
    }

    /**
     * Test of writeResults method, of class Univariate.
     */
    @Test
    public void testWriteResults() {
        System.out.println("writeResults");
        Univariate instance = new Univariate();
        String expResult = "";
        String result = instance.writeResultsToDisk();
        assertNotNull(result);
    }

    /**
     * Test of combineResults method, of class Univariate.
     */
    @Test
    public void testCombineResults() {
        System.out.println("combineResults");
        Univariate instance = new Univariate();
        String expResult = "";
        String result = instance.combineResults();
        assertNotNull(result);
    }

    /**
     * Test of getTrainingSets method, of class Univariate.
     */
    @Test
    public void testGetTrainingSets() {
        System.out.println("getTrainingSets");
        Univariate instance = new Univariate();
        String expResult = "";
        String result = instance.getTrainingSets("");
        assertNotNull(result);
    }

    /**
     * Test of getRowMeansAndSD method, of class Univariate.
     */
    @Test
    public void testGetRowMeansAndSD() {
        System.out.println("getRowMeansAndSD");
        Univariate instance = new Univariate();
        String expResult = "";
        String result = instance.getRowMeansAndSD();
        assertNotNull(result);
    }

    /**
     * Test of initializeResultObjects method, of class Univariate.
     */
    @Test
    public void testInitializeResultObjects() {
        System.out.println("initializeResultObjects");
        Univariate instance = new Univariate();
        String expResult = "";
        String result = instance.initializeResultObjects();
        assertNotNull(result);
    }
}
