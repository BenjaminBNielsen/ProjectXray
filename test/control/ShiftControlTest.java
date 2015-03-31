/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dbc.DatabaseConnection;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @Test
    public void testGetShiftsNoNullsNoException() {
        boolean hasFailedActual = false, hasFailedExpected = false;

        try {
            DatabaseConnection.getInstance().createConnection();
        } catch (FileNotFoundException ex) {
            System.out.println("Filen som der læses fra kunne ikke findes");
        } catch (SQLException ex) {
            System.out.println("Da der skulle oprettes forbindelse til databasen forekom der en sql fejl:\n"
                    + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Kunne ikke finde driveren, husk at importere jdbc biblioteket");
        }

        ShiftControl sc = new ShiftControl();

        //Giver en passende fejlbesked.
        String errorMessage = "";
        try {
            ArrayList<Shift> shifts = sc.getShifts();
            if (shifts == null) {
                hasFailedActual = true;
                errorMessage = "Kunne ikke hente vagterne ud fra databasen.";
            }

            //Løb arraylisten igennem og find eventuelle nulls.
            for (int i = 0; i < shifts.size(); i++) {
                //Hvis ikke der allerede er forekommet en fejl skal der ledes efter nulls i shift arraylisten.
                if (shifts.get(i) == null && hasFailedActual == false) {
                    hasFailedActual = true;
                    errorMessage = "Der er forekommet mindst én null i metoden.";
                }

            }
        } catch (SQLException ex) {
            hasFailedActual = true;
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
        }

        try {
            //Lukker forbindelsen til databasen efter sig.
            DatabaseConnection.getInstance().closeConnection();
        } catch (SQLException ex) {
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
        }

        assertEquals(errorMessage, hasFailedExpected, hasFailedActual);
    }

    @Test
    public void testAddShiftsInsertedCorrectly() {
        int expectedResult = 999999999;
        int actualResult = 0;

        
    }

}
