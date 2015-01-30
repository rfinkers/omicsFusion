/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.results;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

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
    @Ignore //how to test when having a request on the object?
    public void testExecute() throws Exception {
        System.out.println("execute");
        RetrieveResultsSummaryAction instance = new RetrieveResultsSummaryAction();
        String expResult = "success";
        String result = instance.execute();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBackgroundColor method, of class RetrieveResultsSummaryAction.
     */
    @Test
    public void testGetBackgroundColor() {
        System.out.println("getBackgroundColor");
        Double result_2 = 5.71;
        Double min = 0.0;
        Double max = 20.0;
        RetrieveResultsSummaryAction instance = new RetrieveResultsSummaryAction();
        String expResult = "highlight5";
        String result = instance.getBackgroundColor(result_2, min, max, false);
        assertEquals(expResult, result);
    }
}
