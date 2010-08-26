package nl.wur.plantbreeding.omicsfusion.utils;

import org.junit.Ignore;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the submissions to SGE.
 * @author Richard Finkers
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
     * @throws Exception
     */
    @Test
    @Ignore
    public void testExecuteQSubCmd() throws Exception {
        System.out.println("ExecuteQSubCmd");
        String executionDir = "/home/finke002/NetBeansProjects/omicsFusion/src/main/resources"; //FIXME: refer to a valid test test case.
        String method = "lasso";
        int expResult = 0;
        int result = CmdExec.ExecuteQSubCmd(executionDir, method);
        assertNotSame(expResult, result);
    }

    /**
     * Test of CheckJobStatus method, of class CmdExec.
     * @throws Exception 
     */
    @Test
    public void testCheckJobStatus() throws Exception {
        System.out.println("CheckJobStatus");
        int jobId = 4550;
        boolean expResult = true;//Job is finished
        boolean result = CmdExec.CheckJobStatus(jobId);
        assertEquals(expResult, result);
    }
}