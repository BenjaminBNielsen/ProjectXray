/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package technicalServices.persistence;

import dbc.DatabaseConnection;
import exceptions.DatabaseException;
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
    
    RoomHandler() {
    }
    
    public static RoomHandler getInstance() {
        if (instance == null) {
            instance = new RoomHandler();
        }
        return instance;
    }
    
    public void addRooms(ObservableList<Room> rooms) 
        throws DatabaseException {
        try {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "insert into room(roomName, roomState, minOccupation, maxOccupation) values";
        //String sql = "insert into room() values(" + roomNumber + ",'"
        //        + roomName + "'," + roomState + ");";
        
        for (int i = 0; i < rooms.size(); i++) {
                Room tempRoom = rooms.get(i);
                sql += "('" + tempRoom.getRoomName();
                sql += "'," + tempRoom.getRoomState();
                sql += "," + tempRoom.getMinOccupation();
                sql += "," + tempRoom.getMaxOccupation();
                if (i == rooms.size() - 1) {
                    sql += ");";
                } else {
                    sql += "),\n";
                }
            }

        stmt.execute(sql);

        stmt.close();
        } catch(SQLException ex) {
            throw new DatabaseException("Der kunne ikke indsættes rum i databasen.");    
        }
    }
    
    public ArrayList<Room> getRooms() throws DatabaseException {
        ArrayList<Room> rooms = new ArrayList<>();
        try {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "select * from room;";

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String roomName = rs.getString("roomName");
            int roomState = rs.getInt("roomState");
            int minOccupation = rs.getInt("minOccupation");
            int maxOccupation = rs.getInt("maxOccupation");

            rooms.add(new Room(roomName, roomState,minOccupation,maxOccupation));
        }

        rs.close();
        stmt.close();

        return rooms;
        } catch(SQLException ex) {
            throw new DatabaseException("Der kunne ikke hentes nogle rum.");    
        }
    }
    
    public Room getRoom(String roomName) throws DatabaseException{
        try {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        Room room = null;
        
        String sql = "select * from room where roomName = '" + roomName + "'"; 

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
        } catch(SQLException ex) {
            throw new DatabaseException("Der kunne ikke hentes noget rum med det navn.");    
        }
    }
    
    public int getRoomsRows() throws DatabaseException {
        try {
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
        } catch(SQLException ex) {
            throw new DatabaseException("Der kunne ikke hentes antal af rum i databasen.");    
        }
    }
}