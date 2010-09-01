/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.wur.plantbreeding.omicsfusion.utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author finke002
 */
public class CSVTest {

    public CSVTest() {
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
     * Test of readSummaryCsv method, of class CSV.
     */
    @Test
    //@Ignore//FIXME: we do not want to ignore this
    public void testReadSummaryCsv() throws Exception {
        System.out.println("readSummaryCsv");
        String fileName = "/home/finke002/Desktop/e125586fcf9ba1b02a33093a2c17/SPLS_coef_Sum_1.csv";
        CSV instance = new CSV();
        instance.readSummaryCsv(fileName);
        // TODO review the generated test code and remove the default call to fail.
        
    }

}