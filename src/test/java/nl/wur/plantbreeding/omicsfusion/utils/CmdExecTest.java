/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.wur.plantbreeding.omicsfusion.utils;

import org.junit.Ignore;
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
public class CmdExecTest {

    public CmdExecTest() {
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
     * Test of ExecuteQSubCmd method, of class CmdExec.
     */
    @Test
    //@Ignore
    public void testExecuteQSubCmd() throws Exception {
        System.out.println("ExecuteQSubCmd");
        String executionDir = "/tmp/33d9371e5cbaa2c8f03586999069/";
        String method = "lasso";
        int expResult = 0;
        int result = CmdExec.ExecuteQSubCmd(executionDir, method);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CheckJobStatus method, of class CmdExec.
     */
    @Test
    public void testCheckJobStatus() throws Exception {
        System.out.println("CheckJobStatus");
        int jobId = 10;
        boolean expResult = true;
        boolean result = CmdExec.CheckJobStatus(jobId);
        assertEquals(expResult, result);
    }

}