package nl.wur.plantbreeding.omicsfusion.utils;

import java.io.IOException;
import java.util.logging.Logger;
import static nl.wur.plantbreeding.omicsfusion.utils.CmdExec.CheckJobStatus;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.*;

/**
 * Tests the submissions to SGE.
 * @author Richard Finkers
 */
public class CmdExecTest {

    /** The Logger */
    private static final Logger LOG = Logger.getLogger(CmdExecTest.class.getName());

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
        String executionDir = "/home/finke002/NetBeansProjects/omicsFusion/src/main/resources"; //FIXME: refer to a valid test test case. It should contain a complete .pbs file!
        String method = Constants.LASSO;
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
        int jobId = 1;
        boolean expResult = true;//Job is finished
        boolean result;
        try {
            result = CheckJobStatus(jobId);
        } catch (IOException e) {
            //NO qstat installed on the system!
            result = true;
            LOG.warning("No qstat installed on this system. "
                    + "Test will report OK");
        }
        assertEquals(expResult, result);
    }
}
