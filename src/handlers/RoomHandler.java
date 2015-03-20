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
import model.Room;
/**
 *
 * @author Jonas
 */
public class RoomHandler {
    
    private static RoomHandler instance;
    private ArrayList<Room> rooms;
    
    private RoomHandler() {
         rooms = new ArrayList<>();
    }
    
    public static RoomHandler getInstance() {
        if (instance == null) {
            instance = new RoomHandler();
        }
        return instance;
    }
    
    public void addRoom(int roomNumber, String roomName, int roomState) 
        throws SQLException, ClassNotFoundException {
        
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "insert into room() values(" + roomNumber + ",'"
                + roomName + "'," + roomState + ");";

        stmt.execute(sql);

        stmt.close();
    }
    
    public ArrayList<Room> getRooms() throws SQLException, ClassNotFoundException {

        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "select * from room;";

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int roomNumber = rs.getInt("roomNumber");
            String roomName = ("roomName");
            int roomState = rs.getInt("roomState");

            rooms.add(new Room(roomNumber, roomName, roomState));
        }

        rs.close();
        stmt.close();

        return rooms;
    }
    
    public Room getRoom(int roomNumber) throws SQLException, ClassNotFoundException{
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        Room room = null;
        
        String sql = "Select * from qualification where roomnumber = " + roomNumber;

        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            String roomName = ("roomName");
            int roomState = rs.getInt("roomState");

            room = new Room(roomNumber, roomName, roomState); 
        }

        rs.close();
        stmt.close();
        
        return room;
    }
    
    public int getRoomsRows() throws SQLException, ClassNotFoundException {
        
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        
        String sql = "select count(*) from room as rowNumber;";
        
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
