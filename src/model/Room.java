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
    private int minOccupation;
    private int maxOccupation;
    private int count;
    
    public Room (String roomName, int roomState, int minOccupation, int maxOccupation) {
        this.roomName = roomName;
        this.roomState = roomState;
        this.minOccupation = minOccupation;
        this.maxOccupation = maxOccupation;
        count = 0;
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

    public int getMinOccupation() {
        return minOccupation;
    }

    public void setMinOccupation(int minOccupation) {
        this.minOccupation = minOccupation;
    }

    public int getMaxOccupation() {
        return maxOccupation;
    }

    public void setMaxOccupation(int maxOccupation) {
        this.maxOccupation = maxOccupation;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public void increment(){
        count++;
    }
    
    @Override
    public String toString() {
        return roomName;
    }
}
