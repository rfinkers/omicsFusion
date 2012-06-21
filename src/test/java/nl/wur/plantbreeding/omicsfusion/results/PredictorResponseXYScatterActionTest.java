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
package nl.wur.plantbreeding.omicsfusion.results;

import javax.servlet.http.HttpServletRequest;
import org.jfree.chart.JFreeChart;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Richard Finkers
 */
public class PredictorResponseXYScatterActionTest {

    public PredictorResponseXYScatterActionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of execute method, of class PredictorResponseXYScatterAction.
     */
    @Test
    @Ignore
    public void testExecute() throws Exception {
        System.out.println("execute");
        PredictorResponseXYScatterAction instance = new PredictorResponseXYScatterAction();
        String expResult = "success";
        String result = instance.execute();
        assertEquals(expResult, result);
    }

    /**
     * Test of getChart method, of class PredictorResponseXYScatterAction.
     */
    @Test
    @Ignore
    public void testGetChart() {
        System.out.println("getChart");
        PredictorResponseXYScatterAction instance = new PredictorResponseXYScatterAction();
        JFreeChart expResult = null;
        JFreeChart result = instance.getChart();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setServletRequest method, of class
     * PredictorResponseXYScatterAction.
     */
    @Test
    @Ignore
    public void testSetServletRequest() {
        System.out.println("setServletRequest");
        HttpServletRequest request = null;
        PredictorResponseXYScatterAction instance = new PredictorResponseXYScatterAction();
        instance.setServletRequest(request);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}