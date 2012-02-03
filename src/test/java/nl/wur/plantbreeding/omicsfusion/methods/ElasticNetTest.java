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
public class ElasticNetTest {

    public ElasticNetTest() {
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
     * Test of initializeResultObjects method, of class ElasticNet.
     */
    @Test
    public void testInitializeResultObjects() {
        System.out.println("initializeResultObjects");
        ElasticNet instance = new ElasticNet();
        String expResult = "";
        String result = instance.initializeResultObjects();
        assertNotNull(result);
    }

    /**
     * Test of getRequiredLibraries method, of class ElasticNet.
     */
    @Test
    public void testGetRequiredLibraries() {
        System.out.println("getRequiredLibraries");
        ElasticNet instance = new ElasticNet();
        String expResult = "";
        String result = instance.getRequiredLibraries();
        assertNotNull(result);
    }

    /**
     * Test of getAnalysis method, of class ElasticNet.
     */
    @Test
    public void testGetAnalysis() {
        System.out.println("getAnalysis");
        ElasticNet instance = new ElasticNet();
        String expResult = "";
        String result = instance.getAnalysis("");
        assertNotNull(result);
    }

    /**
     * Test of combineResults method, of class ElasticNet.
     */
    @Test
    public void testCombineResults() {
        System.out.println("combineResults");
        ElasticNet instance = new ElasticNet();
        String expResult = "";
        String result = instance.combineResults();
        assertNotNull(result);    }

    /**
     * Test of writeResults method, of class ElasticNet.
     */
    @Test
    public void testWriteResults() {
        System.out.println("writeResults");
        ElasticNet instance = new ElasticNet();
        String expResult = "";
        String result = instance.writeResultsToDisk();
        assertNotNull(result);
    }
}
