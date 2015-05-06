/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import exceptions.DatabaseException;
import technicalServices.persistence.RoomHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import javafx.collections.ObservableList;
import model.Room;

/**
 *
 * @author Jonas
 */
public class RoomControl {
    
    public RoomControl() throws DatabaseException {
    }
    
//    public static RoomControl getInstance() throws SQLException, ClassNotFoundException {
//        if (instance == null) {
//            instance = new RoomControl();
//        }
//        return instance;
//    }

    public int getRoomCount() throws DatabaseException {
        return RoomHandler.getInstance().getRoomsRows();
    }
    
    public void addRooms(ObservableList<Room> rooms) throws DatabaseException {
        RoomHandler.getInstance().addRooms(rooms);
    }
    
    public ArrayList<Room> getRooms() throws DatabaseException {
        return RoomHandler.getInstance().getRooms();
    }
    
}
