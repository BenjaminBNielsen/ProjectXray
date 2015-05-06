/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dbc.DatabaseConnection;
import technicalServices.persistence.EmployeeHandler;
import technicalServices.persistence.StudentHandler;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Employee;
import model.Occupation;
import model.Student;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Benjamin
 */
public class PersonControlTest {

    public PersonControlTest() {
    }

    @Test
    public void testAddStudentsInsertedCorrectly() {
        int expectedResult = 99999997;
        int actualResult = 0;
        String errorMessage = "";
        PersonControl pc = new PersonControl();

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

        //Indsæt student via addStudents med samme id som det forventede resultat.
        ObservableList<Student> students = FXCollections.observableArrayList();
        students.add(new Student(expectedResult, "testKarl", "testKarl", 1));
        try {
            pc.addStudents(students);
        } catch (SQLException ex) {
            errorMessage = "Eployee-objektet til test kunne ikke oprettes,"
                    + " fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc "
                    + "biblioteket";
            System.out.println(errorMessage);
        }

        //Hent det indsatte Student-objekt.
        Student actualStudent = null;
        try {
            actualStudent = StudentHandler.getInstance().getStudent(expectedResult);
        } catch (SQLException ex) {
            errorMessage = "Student-objektet til test kunne ikke hentes,"
                    + " fejlbesked:\n"
                    + ex.getMessage();

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
        actualResult = actualStudent.getId();

        assertEquals("addStudents oprettede ikke et Student-objekt som det skulle",
                expectedResult, actualResult);
    }

    @Test
    public void testGetEmployeesNoNullsNoExceptions() {
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

        PersonControl pc = new PersonControl();

        try {
            ArrayList<Employee> employees = pc.getEmployees();
            if (employees == null) {
                hasFailedActual = true;
                errorMessage = "Kunne ikke hente medarbejderne ud fra databasen.";
            }

            //Løb arraylisten igennem og find eventuelle nulls.
            for (int i = 0; i < employees.size(); i++) {
                //Hvis ikke der allerede er forekommet en fejl skal der ledes efter nulls i shift arraylisten.
                if (employees.get(i) == null && hasFailedActual == false) {
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
    public void testGetStudentsNoNullsNoExceptions() {
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

        PersonControl pc = new PersonControl();

        try {
            ArrayList<Student> students = pc.getStudents();
            if (students == null) {
                hasFailedActual = true;
                errorMessage = "Kunne ikke hente studenterne ud fra databasen.";
            }

            //Løb arraylisten igennem og find eventuelle nulls.
            for (int i = 0; i < students.size(); i++) {
                //Hvis ikke der allerede er forekommet en fejl skal der ledes efter nulls i shift arraylisten.
                if (students.get(i) == null && hasFailedActual == false) {
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
    public void testAddEmployeesInsertedCorrectly() {
        int expectedResult = 99999999;
        int actualResult = 0;
        String errorMessage = "";
        PersonControl pc = new PersonControl();

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

        //Indsæt employee via addEmployees med samme id som det forventede resultat.
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        Occupation testOccupation = new Occupation(2147483647, "test-stilling");
        employees.add(new Employee("testKarl", "testKartl", expectedResult, 44444444,
                "eewareraew", "eware@earaew", testOccupation));
        try {
            pc.addEmployees(employees);
        } catch (SQLException ex) {
            errorMessage = "Eployee-objektet til test kunne ikke oprettes,"
                    + " fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc "
                    + "biblioteket";
            System.out.println(errorMessage);
        }

        //Hent det indsatte Employee-objekt.
        Employee actualEmployee = null;
        try {
            actualEmployee = EmployeeHandler.getInstance().getEmployee(expectedResult);
        } catch (SQLException ex) {
            errorMessage = "Employee-objektet til test kunne ikke hentes,"
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
        actualResult = actualEmployee.getId();

        assertEquals("addEmployees oprettede ikke et Employee-objekt som det skulle",
                expectedResult, actualResult);
    }

    @Test
    public void testGetOccupationsNoNullsNoExceptions() {
        boolean hasFailedActual = false, hasFailedExpected = false;
        //Giver en passende fejlbesked.
        String errorMessage = "";

        try {
            DatabaseConnection.getInstance().createConnection();
        } catch (FileNotFoundException ex) {
            errorMessage = "Filen som der læses fra kunne ikke findes";
        } catch (SQLException ex) {
            errorMessage = "Da der skulle oprettes forbindelse til databasen forekom der en sql fejl:\n"
                    + ex.getMessage();
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc "
                    + "biblioteket";
        }

        PersonControl pc = new PersonControl();

        try {
            ObservableList<Occupation> occupations = pc.getOccupations();
            if (occupations == null) {
                hasFailedActual = true;
                errorMessage = "Kunne ikke hente vagterne ud fra databasen.";
            }

            //Løb arraylisten igennem og find eventuelle nulls.
            for (int i = 0; i < occupations.size(); i++) {
                //Hvis ikke der allerede er forekommet en fejl skal der ledes efter nulls i shift arraylisten.
                if (occupations.get(i) == null && hasFailedActual == false) {
                    hasFailedActual = true;
                    errorMessage = "Der er forekommet mindst én null i metoden.";
                }

            }
        } catch (SQLException ex) {
            hasFailedActual = true;
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc "
                    + "biblioteket";
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

}
