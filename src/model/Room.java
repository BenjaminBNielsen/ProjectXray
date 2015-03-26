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
    private String roomName;
    private int roomState;
    
    public Room (String roomName, int roomState) {
        this.roomName = roomName;
        this.roomState = roomState;
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
    
    private String getRoomStateString() {
        String roomStateString = "";
        if (roomState == 1) {
            roomStateString = "Åbent";
        } else if (roomState == 2) {
            roomStateString = "Lukket";
        } else if (roomState == 3) {
            roomStateString = "Service";
        }
        return roomStateString;
    }   
    
    public String toString() {
        return roomName +  "\n" + getRoomStateString();
    }
}
