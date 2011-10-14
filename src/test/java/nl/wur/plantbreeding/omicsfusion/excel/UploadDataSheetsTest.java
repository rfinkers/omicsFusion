/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package nl.wur.plantbreeding.omicsfusion.excel;

import com.almworks.sqlite4java.SQLiteException;
import java.io.File;
import org.junit.*;

/**
 *
 * @author finke002
 */
public class UploadDataSheetsTest {

    public UploadDataSheetsTest() {
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
     * Test of uploadExcelSheets method, of class UploadDataSheets.
     */
    @Test
    @Ignore
    public void testUploadExcelSheets() throws SQLiteException {
        System.out.println("uploadExcelSheets");
        File responseExcelFile = new File("/home/finke002/NetBeansProjects/omicsFusion2/src/main/resources/CE_Flesh.xls");
        String responseType = "response";
        File predictorExcelFile = new File("/home/finke002/NetBeansProjects/omicsFusion2/src/main/resources/CE_Met.xls");
        String predictorType = "predict";
        UploadDataSheets.uploadExcelSheets(responseExcelFile, responseType, predictorExcelFile, predictorType, "/tmp");
    }
}
