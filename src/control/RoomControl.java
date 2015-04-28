/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import handlers.RoomHandler;
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
    
    public RoomControl() throws SQLException, ClassNotFoundException {
    }

    public int getRoomCount() throws SQLException, ClassNotFoundException {
        return RoomHandler.getInstance().getRoomsRows();
    }
    
    public void addRooms(ObservableList<Room> rooms) throws SQLException, ClassNotFoundException {
        RoomHandler.getInstance().addRooms(rooms);
    }
    
    public ArrayList<Room> getRooms() throws SQLException, ClassNotFoundException {
        return RoomHandler.getInstance().getRooms();
    }
    
}
