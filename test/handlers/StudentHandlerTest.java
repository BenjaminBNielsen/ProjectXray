/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import technicalServices.persistence.StudentHandler;
import dbc.DatabaseConnection;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Student;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Benjamin
 */
public class StudentHandlerTest {

    public StudentHandlerTest() {
    }

    @Test
    public void testSingletonNoNull() {
        boolean isNullActual = false;
        boolean isNullExpected = false;
        String errorMessage = "";
        StudentHandler sh = StudentHandler.getInstance();

        if (sh == null) {
            errorMessage = "Det returnerede objekt var null";
            isNullActual = true;
        }

        assertEquals(errorMessage, isNullExpected, isNullActual);
    }

    @Test
    public void testAddStudentNoExceptions() {
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

        Student testStudent = new Student(99999994, "Jones", "Bones", 1);
        try {
            StudentHandler.getInstance().addStudent(testStudent);
        } catch (SQLException ex) {
            hasExceptionsActual = true;
            errorMessage = "SQLException: Student objektet til test kunne ikke oprettes,"
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
        }

        assertEquals(errorMessage, hasExceptionsExpected, hasExceptionsActual);
    }

    @Test
    public void testGetStudentsNoExceptions() {
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
            ArrayList<Student> students = StudentHandler.getInstance().getStudents();
        } catch (SQLException ex) {
            hasExceptionsActual = true;
            errorMessage = "SQLException: I forbindelse med indhentning af student-objekter"
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
    public void testAddStudentsNoExceptions() {
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

        //Indsæt employee objekter via addEmployees.
        ObservableList<Student> students = FXCollections.observableArrayList();
        students.add(new Student(99999993, "Jones", "Bones", 1));
        students.add(new Student(99999992, "Jones", "Bones", 1));

        try {
            StudentHandler.getInstance().addStudents(students);
        } catch (SQLException ex) {
            hasExceptionsActual = true;
            errorMessage = "SQLException: Student objektet til test kunne ikke oprettes,"
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
    public void testGetStudentNoExceptions() {
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
            //Hent Student-objekt med id 2147483646.
            StudentHandler.getInstance().getStudent(2147483646);
        } catch (SQLException ex) {
            hasExceptionsActual = true;
            errorMessage = "SQLException: I forbindelse med indhentning af Student-objekt"
                    + " opstod der en sql fejl som gav følgende fejlbesked:\n"
                    + ex.getMessage();
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
