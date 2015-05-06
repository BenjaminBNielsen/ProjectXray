/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import technicalServices.persistence.RoomHandler;
import dbc.DatabaseConnection;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import model.Room;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jonas
 */
public class RoomHandlerTest {

    String errorMessage;

    public RoomHandlerTest() {
        errorMessage = "Der er ingen fejl.";
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class RoomHandler.
     */
    @Test
    public void testGetInstance() {

        boolean instanceNullActual = false;
        boolean instanceNullExpected = false;
        RoomHandler result = RoomHandler.getInstance();

        if (result == null) {
            errorMessage = "Der blev returneret en null værdi";
            instanceNullActual = true;
        }
        System.out.println("Første test: " + errorMessage);
        assertEquals(errorMessage, instanceNullExpected, instanceNullActual);

    }

    /**
     * Test of addRooms method, of class RoomHandler.
     */
    @Test
    public void testAddRooms() {
        boolean testExceptionsActual = false;
        boolean testExceptionsExpected = false;

        try {
            DatabaseConnection.getInstance().createConnection();
        } catch (FileNotFoundException ex) {
            errorMessage = "Filen som der læses fra kunne ikke findes";
            System.out.println(errorMessage);
        } catch (SQLException ex) {
            errorMessage = "Da der skulle oprettes forbindelse til databasen forekom der en sql fejl:\n";
            System.out.println(errorMessage + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc biblioteket";
            System.out.println(errorMessage);
        }

        Room testRoom = new Room("testRoom", 1);
        Room testRoom2 = new Room("testRoom2", 2);

        Statement stmt = null;
        try {
            stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        } catch (SQLException ex) {
            errorMessage = "Da der skulle oprettes forbindelse til databasen forekom der en sql fejl:\n";
            System.out.println(errorMessage + ex.getMessage());
        }

        try {
            String sql = "insert into room(roomName, roomState) values";

            ArrayList<Room> rooms = new ArrayList<>();
            rooms.add(testRoom);
            rooms.add(testRoom2);

            for (int i = 0; i < rooms.size(); i++) {
                Room tempRoom = rooms.get(i);
                sql += "('" + tempRoom.getRoomName();
                sql += "'," + tempRoom.getRoomState();
                if (i == rooms.size() - 1) {
                    sql += ");";
                } else {
                    sql += "),\n";
                }
            }

            stmt.execute(sql);
            stmt.close();
        } catch (SQLException ex) {
            errorMessage = "Der er sket en fejl da der skulle tilføjes Room objekter til databasen:\n";
            System.out.println(errorMessage + ex.getMessage());
        }

        try {
            DatabaseConnection.getInstance().closeConnection();
        } catch (SQLException ex) {
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
        }

        System.out.println("Anden test: " + errorMessage);
        assertEquals(testExceptionsExpected, testExceptionsActual);
    }

    /**
     * Test of getRooms method, of class RoomHandler.
     */
    @Test

    public void testGetRooms() {
        boolean testExceptionsActual = false;
        boolean testExceptionsExpected = false;

        try {
            DatabaseConnection.getInstance().createConnection();
        } catch (FileNotFoundException ex) {
            errorMessage = "Filen som der læses fra kunne ikke findes";
            System.out.println(errorMessage);
        } catch (SQLException ex) {
            errorMessage = "Da der skulle oprettes forbindelse til databasen forekom der en sql fejl:\n";
            System.out.println(errorMessage + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc biblioteket";
            System.out.println(errorMessage);
        }

        RoomHandler rh = new RoomHandler();
        ArrayList<Room> testGetRooms = new ArrayList<>();

        try {
            testGetRooms = rh.getRooms();
        } catch (SQLException ex) {
            errorMessage = "I forbindelse med hentning af room objekter"
                    + " til udregning af forventet resultat"
                    + " er der forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
            testExceptionsActual = true;
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc biblioteket";
            System.out.println(errorMessage);
            testExceptionsActual = true;
        }

        try {
            DatabaseConnection.getInstance().closeConnection();
        } catch (SQLException ex) {
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
        }

        System.out.println("Tredje test: " + errorMessage);
        assertEquals(testExceptionsExpected, testExceptionsActual);

    }

    /**
     * Test of getRoom method, of class RoomHandler.
     */
//    @Test
//    public void testGetRoom() {
//
//        boolean testExceptionsActual = false;
//        boolean testExceptionsExpected = false;
//
//        try {
//            DatabaseConnection.getInstance().createConnection();
//        } catch (FileNotFoundException ex) {
//            errorMessage = "Filen som der læses fra kunne ikke findes";
//            System.out.println(errorMessage);
//        } catch (SQLException ex) {
//            errorMessage = "Da der skulle oprettes forbindelse til databasen forekom der en sql fejl:\n";
//            System.out.println(errorMessage + ex.getMessage());
//        } catch (ClassNotFoundException ex) {
//            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc biblioteket";
//            System.out.println(errorMessage);
//        }
//
//        RoomHandler rh = new RoomHandler();
//        Room testGetRoom = null;
//        
//        try {
//            testGetRoom = rh.getRoom("test1");
//        } catch (SQLException ex) {
//            errorMessage = "I forbindelse med hentning af room objekter"
//                    + " til udregning af forventet resultat"
//                    + " er der forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
//                    + ex.getMessage();
//            System.out.println(errorMessage);
//            testExceptionsActual = true;
//        } catch (ClassNotFoundException ex) {
//            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc biblioteket";
//            System.out.println(errorMessage);
//            testExceptionsActual = true;
//        }
//        
//        try {
//            DatabaseConnection.getInstance().closeConnection();
//        } catch (SQLException ex) {
//            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
//                    + ex.getMessage();
//            System.out.println(errorMessage);
//        }
//        
//        System.out.println("Fjerde test: " + errorMessage);
//        assertEquals(testExceptionsExpected, testExceptionsActual);
//
//    }
    /**
     * Test of getRoomsRows method, of class RoomHandler.
     */
    @Test
    public void testGetRoomsRows() {
        boolean testExceptionsActual = false;
        boolean testExceptionsExpected = false;

        try {
            DatabaseConnection.getInstance().createConnection();
        } catch (FileNotFoundException ex) {
            errorMessage = "Filen som der læses fra kunne ikke findes";
            System.out.println(errorMessage);
        } catch (SQLException ex) {
            errorMessage = "Da der skulle oprettes forbindelse til databasen forekom der en sql fejl:\n";
            System.out.println(errorMessage + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc biblioteket";
            System.out.println(errorMessage);
        }

        RoomHandler rh = new RoomHandler();
        int testGetRoomsRows = 0;

        try {
            testGetRoomsRows = rh.getRoomsRows();
        } catch (SQLException ex) {
            errorMessage = "I forbindelse med hentning af antallet af rum der i alt er i databasen"
                    + " til udregning af forventet resultat"
                    + " er der forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
            testExceptionsActual = true;
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc biblioteket";
            System.out.println(errorMessage);
            testExceptionsActual = true;
        }

        try {
            DatabaseConnection.getInstance().closeConnection();
        } catch (SQLException ex) {
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
        }

        System.out.println("Tredje test: " + errorMessage);
        System.out.println(testGetRoomsRows);
        assertEquals(testExceptionsExpected, testExceptionsActual);

    }

}
