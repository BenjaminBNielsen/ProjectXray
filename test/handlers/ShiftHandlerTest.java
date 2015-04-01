/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import dbc.DatabaseConnection;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
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
public class ShiftHandlerTest {

    public ShiftHandlerTest() {
    }

    @Test
    public void testSingletonNoNull() {
        boolean isNullActual = false;
        boolean isNullExpected = false;
        String errorMessage = "";
        ShiftHandler sh = ShiftHandler.getInstance();

        if (sh == null) {
            errorMessage = "Det returnerede objekt var null";
            isNullActual = true;
        }

        assertEquals(errorMessage, isNullExpected, isNullActual);
    }

    @Test
    public void testAddShiftNoExceptions() {
        boolean hasExceptionsActual = false;
        boolean hasExceptionsExpected = false;
        String errorMessage = "";

        try {
            DatabaseConnection.getInstance().createConnection();
            //Ved exceptions sæt boolean til true og giv fejlbesked.
        } catch (FileNotFoundException ex) {
            errorMessage = "FileNotFoundException: I forbindelse med oprettelse "
                    + "af databaseforbindelse"
                    + " kunne filen med login oplysninger ikke findes.";
        } catch (SQLException ex) {
            errorMessage = "SQLException: I forbindelse med oprettelse af databaseforbindelse"
                    + " opstod der en sql fejl som gav følgende fejlbesked:\n"
                    + ex.getMessage();
        } catch (ClassNotFoundException ex) {
            errorMessage = "ClassNotFoundException: JDBC driveren kunne ikke "
                    + "findes, tjek at biblioteket er importeret.";
        }

        //Hent employee-testobjekt(se "DB/script 4 - test_insert.sql").
        Employee testEmployee = null;
        try {
            testEmployee = EmployeeHandler.getInstance().getEmployee(2147483647);
        } catch (SQLException ex) {
            hasExceptionsActual = true;
            errorMessage = "SQLException: Employee objektet til test kunne ikke hentes,"
                    + " husk at køre test scriptet.\n fejlbesked:\n"
                    + ex.getMessage();
        } catch (ClassNotFoundException ex) {
            hasExceptionsActual = true;
            errorMessage = "ClassNotFoundException: JDBC driveren kunne ikke "
                    + "findes, tjek at biblioteket er importeret.";
        }

        //Indsæt shift via addShift med samme id = 99999999.
        LocalDateTime testTime = new LocalDateTime(1500, 1, 1, 0, 0);
        Shift testShift = new Shift(99999998, Hours.hours(0), Minutes.minutes(0),
                testTime, testEmployee);
        try {
            ShiftHandler.getInstance().addShift(testShift);
        } catch (SQLException ex) {
            hasExceptionsActual = true;
            errorMessage = "SQLException: Shift objektet til test kunne ikke oprettes,"
                    + " fejlbesked:\n"
                    + ex.getMessage();

        } catch (ClassNotFoundException ex) {
            hasExceptionsActual = true;
            errorMessage = "ClassNotFoundException: JDBC driveren kunne ikke "
                    + "findes, tjek at biblioteket er importeret.";
        }

        //Luk forbindelsen.
        try {
            DatabaseConnection.getInstance().closeConnection();
        } catch (SQLException ex) {
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
        }

        assertEquals(errorMessage, hasExceptionsExpected, hasExceptionsActual);
    }

    @Test
    public void testGetShiftsNoExceptions() {
        boolean hasExceptionsActual = false;
        boolean hasExceptionsExpected = false;
        String errorMessage = "";

        try {
            DatabaseConnection.getInstance().createConnection();
        } catch (FileNotFoundException ex) {
            errorMessage = "FileNotFoundException: I forbindelse med oprettelse "
                    + "af databaseforbindelse"
                    + " kunne filen med login oplysninger ikke findes.";
        } catch (SQLException ex) {
            errorMessage = "SQLException: I forbindelse med oprettelse af databaseforbindelse"
                    + " opstod der en sql fejl som gav følgende fejlbesked:\n"
                    + ex.getMessage();
        } catch (ClassNotFoundException ex) {
            errorMessage = "ClassNotFoundException: JDBC driveren kunne ikke "
                    + "findes, tjek at biblioteket er importeret.";
        }

        try {
            ArrayList<Shift> shifts = ShiftHandler.getInstance().getShifts();
        } catch (SQLException ex) {
            hasExceptionsActual = true;
            errorMessage = "SQLException: I forbindelse med indhentning af shift-objekter"
                    + " opstod der en sql fejl som gav følgende fejlbesked:\n"
                    + ex.getMessage();
        } catch (ClassNotFoundException ex) {
            hasExceptionsActual = true;
            errorMessage = "ClassNotFoundException: JDBC driveren kunne ikke "
                    + "findes, tjek at biblioteket er importeret.";
        }

        //Luk forbindelsen.
        try {
            DatabaseConnection.getInstance().closeConnection();
        } catch (SQLException ex) {
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
        }

        assertEquals(errorMessage, hasExceptionsExpected, hasExceptionsActual);

    }

    @Test
    public void testAddShiftsNoExceptions() {
        boolean hasExceptionsActual = false;
        boolean hasExceptionsExpected = false;
        String errorMessage = "";

        try {
            DatabaseConnection.getInstance().createConnection();
            //Ved exceptions sæt boolean til true og giv fejlbesked.
        } catch (FileNotFoundException ex) {
            errorMessage = "FileNotFoundException: I forbindelse med oprettelse "
                    + "af databaseforbindelse"
                    + " kunne filen med login oplysninger ikke findes.";
        } catch (SQLException ex) {
            errorMessage = "SQLException: I forbindelse med oprettelse af databaseforbindelse"
                    + " opstod der en sql fejl som gav følgende fejlbesked:\n"
                    + ex.getMessage();
        } catch (ClassNotFoundException ex) {
            errorMessage = "ClassNotFoundException: JDBC driveren kunne ikke "
                    + "findes, tjek at biblioteket er importeret.";
        }

        //Hent employee-testobjekt(se "DB/script 4 - test_insert.sql").
        Employee testEmployee = null;
        try {
            testEmployee = EmployeeHandler.getInstance().getEmployee(2147483647);
        } catch (SQLException ex) {
            hasExceptionsActual = true;
            errorMessage = "SQLException: Employee objektet til test kunne ikke hentes,"
                    + " husk at køre test scriptet.\n fejlbesked:\n"
                    + ex.getMessage();
        } catch (ClassNotFoundException ex) {
            hasExceptionsActual = true;
            errorMessage = "ClassNotFoundException: JDBC driveren kunne ikke "
                    + "findes, tjek at biblioteket er importeret.";
        }

        //Indsæt shift via addShift med samme id = 99999999.
        LocalDateTime testTime = new LocalDateTime(1500, 1, 1, 0, 0);
        ArrayList<Shift> shifts = new ArrayList<>();
        
        Shift testShift1 = new Shift(99999996, Hours.hours(0), Minutes.minutes(0),
                testTime, testEmployee);
        shifts.add(testShift1);
        Shift testShift2 = new Shift(99999995, Hours.hours(0), Minutes.minutes(0),
                testTime, testEmployee);
        shifts.add(testShift2);
        try {
            ShiftHandler.getInstance().addShifts(shifts);
        } catch (SQLException ex) {
            hasExceptionsActual = true;
            errorMessage = "SQLException: Shift objektet til test kunne ikke oprettes,"
                    + " fejlbesked:\n"
                    + ex.getMessage();

        } catch (ClassNotFoundException ex) {
            hasExceptionsActual = true;
            errorMessage = "ClassNotFoundException: JDBC driveren kunne ikke "
                    + "findes, tjek at biblioteket er importeret.";
        }

        //Luk forbindelsen.
        try {
            DatabaseConnection.getInstance().closeConnection();
        } catch (SQLException ex) {
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
        }

        assertEquals(errorMessage, hasExceptionsExpected, hasExceptionsActual);

    }

    @Test
    public void testGetShiftNoExceptions() {
        boolean hasExceptionsActual = false;
        boolean hasExceptionsExpected = false;
        String errorMessage = "";

        try {
            DatabaseConnection.getInstance().createConnection();
            //Ved exceptions sæt boolean til true og giv fejlbesked.
        } catch (FileNotFoundException ex) {
            errorMessage = "FileNotFoundException: I forbindelse med oprettelse "
                    + "af databaseforbindelse"
                    + " kunne filen med login oplysninger ikke findes.";
        } catch (SQLException ex) {
            errorMessage = "SQLException: I forbindelse med oprettelse af databaseforbindelse"
                    + " opstod der en sql fejl som gav følgende fejlbesked:\n"
                    + ex.getMessage();
        } catch (ClassNotFoundException ex) {
            errorMessage = "ClassNotFoundException: JDBC driveren kunne ikke "
                    + "findes, tjek at biblioteket er importeret.";
        }

        try {
            //Hent Shiftobjekt med id 99999997.
            ShiftHandler.getInstance().getShift(99999997);
        } catch (SQLException ex) {
            hasExceptionsActual = true;
            errorMessage = "SQLException: I forbindelse med oprettelse af databaseforbindelse"
                    + " opstod der en sql fejl som gav følgende fejlbesked:\n"
                    + ex.getMessage();
        } catch (ClassNotFoundException ex) {
            hasExceptionsActual = true;
            errorMessage = "ClassNotFoundException: JDBC driveren kunne ikke "
                    + "findes, tjek at biblioteket er importeret.";
        }

        //Luk forbindelsen.
        try {
            DatabaseConnection.getInstance().closeConnection();
        } catch (SQLException ex) {
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
        }

        assertEquals(errorMessage, hasExceptionsExpected, hasExceptionsActual);
    }

}
