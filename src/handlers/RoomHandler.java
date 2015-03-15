/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package handlers;

import databaseConnection.DatabaseConnection;
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
    
    public static RoomHandler getInstance() {
        if (instance == null) {
            instance = new RoomHandler();
        }
        return instance;
    }
    
    public void addRoom(int roomNumber, String roomName, boolean closed) 
        throws SQLException, ClassNotFoundException {
        
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "insert into room() values(" + roomNumber + ",'"
                + roomName + "'," + closed + ");";

        stmt.execute(sql);

        stmt.close();
    }
    
    public ArrayList<Room> getRooms() throws SQLException, ClassNotFoundException {
        ArrayList<Room> rooms = new ArrayList<>();

        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "select * from room where room.roomNumber = room.roomNumber"
                + " and room.roomName = room.roomName;";

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int roomNumber = rs.getInt("roomNumber");
            String roomName = ("roomName");
            boolean closed = rs.getBoolean("closed");

            rooms.add(new Room(roomNumber, roomName, closed));
        }

        rs.close();
        stmt.close();

        return rooms;
    }
}