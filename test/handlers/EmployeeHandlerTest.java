/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import technicalServices.persistence.EmployeeHandler;
import technicalServices.persistence.OccupationHandler;
import dbc.DatabaseConnection;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class EmployeeHandlerTest {

    public EmployeeHandlerTest() {
    }

    @Test
    public void testSingletonNoNull() {
        boolean isNullActual = false;
        boolean isNullExpected = false;
        String errorMessage = "";
        EmployeeHandler eh = EmployeeHandler.getInstance();

        if (eh == null) {
            errorMessage = "Det returnerede objekt var null";
            isNullActual = true;
        }

        assertEquals(errorMessage, isNullExpected, isNullActual);
    }

    @Test
    public void testAddEmployeeNoExceptions() {
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

        Occupation testOccupation = null;
        try {
            testOccupation = OccupationHandler.getInstance().getOccupation(2147483647);
        } catch (SQLException ex) {
            errorMessage = "SQLException: I forbindelse med indhentning af occupation-objekt"
                    + " opstod der en sql fejl som gav følgende fejlbesked:\n"
                    + ex.getMessage();
        }

        Employee testEmployee = new Employee("Harald", "Blåtand", 99999998,
                12345678, "Ringgade 41", "sejfyr@somemail.com", testOccupation);
        try {
            EmployeeHandler.getInstance().addEmployee(testEmployee);
        } catch (SQLException ex) {
            hasExceptionsActual = true;
            errorMessage = "SQLException: Employee objektet til test kunne ikke oprettes,"
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
    public void testGetEmployeesNoExceptions() {
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
            ArrayList<Employee> employees = EmployeeHandler.getInstance().getEmployees();
        } catch (SQLException ex) {
            hasExceptionsActual = true;
            errorMessage = "SQLException: I forbindelse med indhentning af employee-objekter"
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
    public void testAddEmployeesNoExceptions() {
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

        //Hent Occupation-testobjekt(se "DB/script 4 - test_insert.sql").
        Occupation testOccupation = null;
        try {
            testOccupation = OccupationHandler.getInstance().getOccupation(2147483647);
        } catch (SQLException ex) {
            hasExceptionsActual = true;
            errorMessage = "SQLException: Occupation-objektet til test kunne ikke hentes,"
                    + " husk at køre test scriptet.\n fejlbesked:\n"
                    + ex.getMessage();
        }

        //Indsæt employee objekter via addEmployees.
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        employees.add(new Employee("Harald", "Blåtand", 99999996,
                12345678, "Ringgade 41", "sejfyr@somemail.com", testOccupation));
        employees.add(new Employee("Harald", "Blåtand", 99999995,
                12345678, "Ringgade 41", "sejfyr@somemail.com", testOccupation));
        
        try {
            EmployeeHandler.getInstance().addEmployees(employees);
        } catch (SQLException ex) {
            hasExceptionsActual = true;
            errorMessage = "SQLException: Employee objektet til test kunne ikke oprettes,"
                    + " fejlbesked:\n"
                    + ex.getMessage();
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
     public void testGetEmployeeNoExceptions() {
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
     //Hent Employee-objekt med id 2147483647.
     EmployeeHandler.getInstance().getEmployee(2147483647);
     } catch (SQLException ex) {
     hasExceptionsActual = true;
     errorMessage = "SQLException: I forbindelse med indhentning af employee-objekt"
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
     }

     assertEquals(errorMessage, hasExceptionsExpected, hasExceptionsActual);
     }
}
