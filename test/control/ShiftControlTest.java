/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dbc.DatabaseConnection;
import handlers.EmployeeHandler;
import handlers.ShiftHandler;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.Occupation;
import model.Shift;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.junit.Test;
import static org.junit.Assert.*;

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
        //Giver en passende fejlbesked.
        String errorMessage = "";

        try {
            DatabaseConnection.getInstance().createConnection();
        } catch (FileNotFoundException ex) {
            errorMessage = "Filen som der læses fra kunne ikke findes";
            System.out.println(errorMessage);
        } catch (SQLException ex) {
            errorMessage = "Da der skulle oprettes forbindelse til databasen forekom der en sql fejl:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc "
                    + "biblioteket";
            System.out.println(errorMessage);
        }

        ShiftControl sc = new ShiftControl();

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

            System.out.println(errorMessage);
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc "
                    + "biblioteket";
            System.out.println(errorMessage);
        }

        try {
            //Lukker forbindelsen til databasen efter sig.
            DatabaseConnection.getInstance().closeConnection();
        } catch (SQLException ex) {
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();

            System.out.println(errorMessage);
        }

        assertEquals(errorMessage, hasFailedExpected, hasFailedActual);
    }

    @Test
    public void testAddShiftInsertedCorrectly() {
        int expectedResult = 99999999;
        int actualResult = 0;
        String errorMessage = "";
        ShiftControl sc = new ShiftControl();

        //Opret forbindelse.
        try {
            DatabaseConnection.getInstance().createConnection();
        } catch (FileNotFoundException ex) {
            errorMessage = "Der kunne ikke oprettes forbindelse til databasen"
                    + " fordi at filen \"xraydb.text\" ikke blev fundet,"
                    + " sørg for at denne ligger i rodmappen";

            System.out.println(errorMessage);
        } catch (SQLException ex) {
            errorMessage = "I forbindelse med oprettelse af forbindelse til"
                    + "databasen kom forekom der en sql fejl med denne fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc "
                    + "biblioteket";
            System.out.println(errorMessage);
        }

        //Hent employee-testobjekt(se "DB/script 4 - test_insert.sql").
        Employee testEmployee = null;
        try {
            testEmployee = EmployeeHandler.getInstance().getEmployee(2147483647);
        } catch (SQLException ex) {
            errorMessage = "Employee objektet til test kunne ikke hentes,"
                    + " husk at køre test scriptet.\n fejlbesked:\n"
                    + ex.getMessage();

            System.out.println(errorMessage);
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc "
                    + "biblioteket";
            System.out.println(errorMessage);
        }

        //Indsæt shift via addShifts med samme id som det forventede resultat.
        ArrayList<Shift> shifts = new ArrayList<>();
        LocalDateTime testTime = new LocalDateTime(1500, 1, 1, 0, 0);
        shifts.add(new Shift(expectedResult, Hours.hours(0), Minutes.minutes(0), testTime, testEmployee));
        try {
            sc.addShifts(shifts);
        } catch (SQLException ex) {
            errorMessage = "Shift objektet til test kunne ikke oprettes,"
                    + " fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc "
                    + "biblioteket";
            System.out.println(errorMessage);
        }

        //Hent det indsatte shift-objekt.
        Shift actualShift = null;
        try {
            actualShift = ShiftHandler.getInstance().getShift(expectedResult);
        } catch (SQLException ex) {
            errorMessage = "Shift objektet til test kunne ikke hentes,"
                    + " fejlbesked:\n"
                    + ex.getMessage();

            System.out.println(errorMessage);
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc "
                    + "biblioteket";
            System.out.println(errorMessage);
        }

        //Luk forbindelsen til databasen.
        try {
            DatabaseConnection.getInstance().closeConnection();
        } catch (SQLException ex) {
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
        }

        //Tjek at det indsatte objekts id er det samme som det hentede,
        //det kan fx forekomme at den hentedes id er null, hvis objektet ikke
        //blev indsat korrekt
        actualResult = actualShift.getId();

        assertEquals("addShift oprettede ikke et Shift-objekt som det skulle",
                expectedResult, actualResult);
    }

}
