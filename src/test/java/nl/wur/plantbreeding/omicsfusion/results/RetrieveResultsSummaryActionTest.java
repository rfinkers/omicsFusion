/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.wur.plantbreeding.omicsfusion.results;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Richard Finkers
 */
public class RetrieveResultsSummaryActionTest {

    public RetrieveResultsSummaryActionTest() {
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
     * Test of execute method, of class RetrieveResultsSummaryAction.
     */
    @Test
    public void testExecute() throws Exception {
        System.out.println("execute");
        RetrieveResultsSummaryAction instance = new RetrieveResultsSummaryAction();
        String expResult = "error";
        String result = instance.execute();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBackgroundColor method, of class RetrieveResultsSummaryAction.
     */
    @Test
    public void testGetBackgroundColor() {
        System.out.println("getBackgroundColor");
        float result_2 = 5.71F;
        float min = 0.0F;
        float max = 20.0F;
        RetrieveResultsSummaryAction instance = new RetrieveResultsSummaryAction();
        String expResult = "#c2cada";
        String result = instance.getBackgroundColor(result_2, min, max);
        assertEquals(expResult, result);
    }

}