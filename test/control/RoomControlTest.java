/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dbc.DatabaseConnection;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
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
public class RoomControlTest {
    
    public RoomControlTest() {
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

//    @Test
//    public void testGetRoomCount() throws Exception {
//    }

    @Test
    public void testAddRoomsInsertedCorrectly() {
        boolean hasFailedActual = false, hasFailedExpected = false;
        String errorMessage = "Der er ingen fejl. ";
        
        //DB forbindelse.
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
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc biblioteket";
            System.out.println(errorMessage);
        }
        
        //Nyt RoomControl objekt.
        RoomControl rc = null;
        try {
            rc = new RoomControl();
        } catch (SQLException ex) {
            errorMessage = "I forbindelse med opretning af nyt RoomControl objekt"
                    + " til udregning af forventet resultat"
                    + " er der forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);;
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc biblioteket";
            System.out.println(errorMessage);
        }
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        Room testRoom1 = new Room("test3", 1,1,1);
        Room testRoom2 = new Room("test4", 2,1,1);
        rooms.addAll(testRoom1, testRoom2);
        
        //Indsæt data i databasen.
        try {
            rc.addRooms(rooms);
        } catch (SQLException ex) {
            errorMessage = "I forbindelse med indsætning af rum elementer til "
                    + "udregning af forventet resultat er der sket en sql"
                    + "fejl i metoden med følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
            hasFailedActual = true;
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc biblioteket";
            System.out.println(errorMessage);
            hasFailedActual = true;
        }
        
        //Slet data i databasen.
        try {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        String sql = "delete from room where roomName in ('test3', 'test4');";
        stmt.execute(sql);
        stmt.close();
        } catch (SQLException ex) {
            errorMessage = "I forbindelse med sletning af de 2 rows i databasen"
                    + " til udregning af forventet resultat"
                    + " er der forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);;
        }
        
        try {
            //Lukker forbindelsen til databasen efter sig.
            DatabaseConnection.getInstance().closeConnection();
        } catch (SQLException ex) {
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
        }
        
        //Resultat.
        assertEquals(errorMessage, hasFailedExpected, hasFailedActual);
        System.out.println(errorMessage + "Forventet boolean værdi: " + hasFailedExpected 
                + ", faktisk boolean værdi: " +hasFailedActual + ".");
        
    }

    @Test
    public void testGetRoomsCorrectly() {
        
        boolean hasFailedActual = false, hasFailedExpected = false;
        String errorMessage = "Der er ingen fejl. ";
        //DB forbindelse.
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
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc biblioteket";
            System.out.println(errorMessage);
        }
        
        //Nyt RoomControl objekt.
        RoomControl rc = null;
        try {
            rc = new RoomControl();
        } catch (SQLException ex) {
            errorMessage = "I forbindelse med opretning af nyt RoomControl objekt"
                    + " til udregning af forventet resultat"
                    + " er der forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);;
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc biblioteket";
            System.out.println(errorMessage);
        }
        ArrayList<Room> rooms = new ArrayList<>();
        
        //Henter rum fra databasen.
        try {
            rooms = rc.getRooms();
        } catch (SQLException ex) {
            errorMessage = "I forbindelse med hentning af room objekter"
                    + " til udregning af forventet resultat"
                    + " er der forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
            System.out.println(errorMessage);
            hasFailedActual = true;
        } catch (ClassNotFoundException ex) {
            errorMessage = "Kunne ikke finde driveren, husk at importere jdbc biblioteket";
            System.out.println(errorMessage);
            hasFailedActual = true;
        }
        
        try {
            //Lukker forbindelsen til databasen efter sig.
            DatabaseConnection.getInstance().closeConnection();
        } catch (SQLException ex) {
            errorMessage = "Der er forekommet en sql fejl i metoden, denne gav følgende fejlbesked:\n"
                    + ex.getMessage();
        }
        
        //Resultat
        assertEquals(errorMessage, hasFailedExpected, hasFailedActual);
        System.out.println(errorMessage + "Forventet boolean værdi: " + hasFailedExpected 
                + ", faktisk boolean værdi: " +hasFailedActual + ".");
    }
    
}
