/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Jonas
 */
public class Room {
    private int roomNumber;
    private String roomName;
    private int roomState;
    
    public Room (int roomNumber, String roomName, int roomState) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.roomState = roomState;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomState() {
        return roomState;
    }

    public void setClosed(int roomState) {
        this.roomState = roomState;
    }
    
    public String toString() {
        return "Rummets nummer: " + roomNumber + "\nRummets navn: " + roomName 
                + "\nRummets status: " + roomState;
    }
    
    
}
