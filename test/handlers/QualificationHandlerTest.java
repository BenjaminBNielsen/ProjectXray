/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import technicalServices.persistence.QualificationHandler;
import dbc.DatabaseConnection;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.LimitQualification;
import model.Room;
import model.RoomQualification;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mads
 */
public class QualificationHandlerTest {

    public QualificationHandlerTest() {
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
     * Test of getRoomQualifications method, of class QualificationHandler.
     */
    @Test
    public void testGetRoomQualifications(){
        System.out.println("henter rumkvalifikationer");
        
        try {
            DatabaseConnection.getInstance().createConnection();
        } catch (FileNotFoundException ex) {
            System.out.println("earneworn");
        } catch (SQLException ex) {
            System.out.println("aernewænræe");
        } catch (ClassNotFoundException ex) {
            System.out.println("weranwernweapønrp");
        }
        int expectedSize = 2;
        ArrayList<RoomQualification> result = QualificationHandler.getInstance().getRoomQualifications();
        int actualSize = result.size();
        
        assertEquals(expectedSize, actualSize);

    }

}
