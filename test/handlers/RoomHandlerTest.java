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
    
    public RoomHandlerTest() {
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
//        RoomHandler expResult = null;
//        RoomHandler result = RoomHandler.getInstance();
//        assertEquals(expResult, result);
    }

    /**
     * Test of addRooms method, of class RoomHandler.
     */
    @Test
    public void testAddRooms() throws Exception {
        
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
        System.out.println("addRooms");
        ObservableList<Room> rooms = null;
        RoomHandler instance = null;
        instance.addRooms(rooms);
        
    }

    /**
     * Test of getRooms method, of class RoomHandler.
     */
    @Test
    public void testGetRooms() throws Exception/*FJERN EXCEPTION KAST DETTE HØRER IKKE TIL I EN TEST METODE!!!!!!!!!!!!!!!!!!!!
            !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1*/{
        
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
        System.out.println("getRooms");
        RoomHandler instance = null;
        ArrayList<Room> expResult = null;
        ArrayList<Room> result = instance.getRooms();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getRoom method, of class RoomHandler.
     */
    @Test
    public void testGetRoom() throws Exception {
        
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
        System.out.println("getRoom");
        String roomName = "";
        RoomHandler instance = null;
        Room expResult = null;
        Room result = instance.getRoom(roomName);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getRoomsRows method, of class RoomHandler.
     */
    @Test
    public void testGetRoomsRows() throws Exception {
        
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
        
        System.out.println("getRoomsRows");
        RoomHandler instance = null;
        int expResult = 0;
        int result = instance.getRoomsRows();
        assertEquals(expResult, result);
        
    }
    
}
