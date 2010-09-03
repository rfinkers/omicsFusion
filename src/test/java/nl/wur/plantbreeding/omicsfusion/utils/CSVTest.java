/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.utils;

import java.util.ArrayList;
import nl.wur.plantbreeding.omicsfusion.datatypes.CsvSummaryDataType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Richard Finkers
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
     * @throws Exception 
     */
    @Test
    //@Ignore//FIXME: we do not want to ignore this
    public void testReadSummaryCsv() throws Exception {
        System.out.println("readSummaryCsv");
        //String fileName = "/home/finke002/Desktop/d89339e9c510a1e4e13ce46cc02b/SPLS_coef_Sum_1.csv";//Work
        String fileName = "/home/finke002/Desktop/e125586fcf9ba1b02a33093a2c17ex/SPLS_coef_Sum.csv";//Home
        ArrayList<CsvSummaryDataType> readSummaryCsv = CSV.readSummaryCsv(fileName);
        for (CsvSummaryDataType csvSummaryDataType : readSummaryCsv) {
            System.out.println("Data: " + csvSummaryDataType.toString());
        }
        // TODO review the generated test code and remove the default call to fail.

    }
}
