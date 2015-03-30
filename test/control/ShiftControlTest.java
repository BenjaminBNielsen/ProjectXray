/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Shift;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Benjamin
 */
public class ShiftControlTest {

    public ShiftControlTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetShiftsNoNullsNoException() {
        boolean hasFailedActual = false, hasFailedExpected = false;

        ShiftControl sc = new ShiftControl();

        //Giver en passende fejlbesked.
        String errorMessage = "";
        try {
            ArrayList<Shift> shifts = sc.getShifts();

            //Løb arraylisten igennem og find eventuelle nulls.
            for (int i = 0; i < shifts.size(); i++) {
                if (shifts.get(i) == null) {
                    hasFailedActual = true;
                    errorMessage = "Der er forekommet mindst én null i metoden.";
                }

            }
        } catch (SQLException ex) {
            hasFailedActual = true;
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
        }

        assertEquals(errorMessage, hasFailedExpected, hasFailedActual);
    }
    
    @Test
    public void testAddShiftsInsertedCorrectly(){
        int expectedResult = 999999999;
        int actualResult = 0;
        
        
    }

    @Test
    public void testGetShifts() throws Exception {
    }

}
