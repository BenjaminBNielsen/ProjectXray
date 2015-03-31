/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dbc.DatabaseConnection;
import handlers.EmployeeHandler;
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
        } catch (ClassNotFoundException ex) {
            System.out.println("Kunne ikke finde driveren, husk at importere jdbc biblioteket");
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
    public void testAddShiftInsertedCorrectly() {
        int expectedResult = 0;
        int actualResult = 0;
        ShiftControl sc = new ShiftControl();

        //Opret forbindelse.
        try {
            DatabaseConnection.getInstance().createConnection();
        } catch (FileNotFoundException ex) {
            System.out.println("Der kunne ikke oprettes forbindelse til databasen"
                    + " fordi at filen \"xraydb.text\" ikke blev fundet,"
                    + " sørg for at denne ligger i rodmappen");
        } catch (SQLException ex) {
            System.out.println("I forbindelse med oprettelse af forbindelse til"
                    + "databasen kom forekom der en sql fejl med denne fejlbesked:\n"
                    + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Kunne ikke finde driveren, husk at importere jdbc biblioteket");
        }

        //Hent employee-testobjekt(se "DB/script 4 - test_insert.sql").
        Employee testEmployee = null;
        try {
            testEmployee = EmployeeHandler.getInstance().getEmployee(2147483647);
        } catch (SQLException ex) {
            Logger.getLogger(ShiftControlTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShiftControlTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Indsæt shift via addShifts med samme id som det forventede resultat.
        ArrayList<Shift> shifts = new ArrayList<>();
        LocalDateTime testTime = new LocalDateTime(1500,1,1,0,0);
        shifts.add(new Shift(expectedResult, Hours.hours(0), Minutes.minutes(0), testTime, testEmployee));
        sc.addShifts(shifts);
        
        //Hent det indsatte shift-objekt.
        
        
        //Tjek at det indsatte objekts id er det samme som det hentede,
        //det kan fx forekomme at den hentedes id er null, hvis objektet ikke
        //blev indsat korrekt
        
//        try {
//            sc.addShifts(shifts);
//        } catch (SQLException ex) {
//            String errorMessage = "I forbindelse med indsætning af shift objektet til test"
//                    + "er der forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
//                    + ex.getMessage();
//            System.out.println(errorMessage);
//        } catch (ClassNotFoundException ex) {
//            System.out.println("Kunne ikke finde driveren, husk at importere jdbc biblioteket");
//        }

        //Test at det oprettede element nu er det sidste i databasen, og at det har
        //den forventede id.
        try {
            //Etabler actualResult
            shifts = sc.getShifts();
        } catch (SQLException ex) {
            String errorMessage = "I forbindelse med hentelse af det nyligt tilføjede objekt"
                    + "er der forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
        } catch (ClassNotFoundException ex) {
            System.out.println("Kunne ikke finde driveren, husk at importere jdbc biblioteket");
        }

        actualResult = shifts.get(shifts.size() - 1).getId();

        assertEquals("addShift oprettede ikke et Shift-objekt som det skulle", expectedResult, actualResult);
    }

}
