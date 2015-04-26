/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package handlers;

import dbc.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import model.Room;
/**
 *
 * @author Jonas
 */
public class RoomHandler {
    
    private static RoomHandler instance;
    private ArrayList<Room> rooms;
    
    RoomHandler() {
         rooms = new ArrayList<>();
    }
    
    public static RoomHandler getInstance() {
        if (instance == null) {
            instance = new RoomHandler();
        }
        return instance;
    }
    
    public void addRooms(ObservableList<Room> rooms) 
        throws SQLException, ClassNotFoundException {
        
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "insert into room(roomName, roomState) values";
        //String sql = "insert into room() values(" + roomNumber + ",'"
        //        + roomName + "'," + roomState + ");";
        
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
    }
    
    public ArrayList<Room> getRooms() throws SQLException, ClassNotFoundException {

        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "select * from room;";

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String roomName = ("roomName");
            int roomState = rs.getInt("roomState");
            int minOccupation = rs.getInt("minOccupation");
            int maxOccupation = rs.getInt("maxOccupation");

            rooms.add(new Room(roomName, roomState,minOccupation,maxOccupation));
        }

        rs.close();
        stmt.close();

        return rooms;
    }
    
    public Room getRoom(String roomName) throws SQLException, ClassNotFoundException{
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        Room room = null;
        
        String sql = "Select * from qualification where roomName = " + roomName;

        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            int roomState = rs.getInt("roomState");
            int minOccupation = rs.getInt("minOccupation");
            int maxOccupation = rs.getInt("maxOccupation");
            
            room = new Room(roomName, roomState,minOccupation,maxOccupation); 
        }

        rs.close();
        stmt.close();
        
        return room;
    }
    
    public int getRoomsRows() throws SQLException, ClassNotFoundException {
        
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        
        String sql = "select count(*) as rowNumber from room;";
        
        ResultSet rs = stmt.executeQuery(sql);
        int roomCount = 0;
        
        if (rs.next()) {
            roomCount = rs.getInt("rowNumber");
        }
        
        rs.close();
        stmt.close();
        
        return roomCount;
    }
}
