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
package nl.wur.plantbreeding.logic.util;

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
public class ValidationTest {

    public ValidationTest() {
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
     * Test of containsSpecialCharactersCheck method, of class Validation.
     */
    @Test
    public void testContainsSpecialCharactersCheck_String() {
        System.out.println("containsSpecialCharactersCheck");
        String validation = "#$%";
        boolean expResult = true;
        boolean result = Validation.containsSpecialCharactersCheck(validation);
        assertEquals(expResult, result);
    }

    /**
     * Test of containsSpecialCharactersCheck method, of class Validation.
     */
    @Test
    public void testContainsSpecialCharactersCheck_int() {
        System.out.println("containsSpecialCharactersCheck");
        int validation = 0;
        boolean expResult = false;
        boolean result = Validation.containsSpecialCharactersCheck(validation);
        assertEquals(expResult, result);
    }

    /**
     * Test of nullString method, of class Validation.
     */
    @Test
    public void testNullString() {
        System.out.println("nullString");
        String nullString = "#$%";
        String expResult = "#$%";
        String result = Validation.nullString(nullString);
        assertEquals(expResult, result);
    }

    /**
     * Test of floatContainsLetters method, of class Validation.
     */
    @Test
    public void testFloatContainsLetters() {
        System.out.println("floatContainsLetters");
        String floatValidation = "#$%";
        boolean expResult = false;
        boolean result = Validation.floatContainsLetters(floatValidation);
        assertEquals(expResult, result);
    }
}
