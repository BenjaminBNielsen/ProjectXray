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
    private boolean closed;
    
    public Room (int roomNumber, String roomName, boolean closed) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.closed = closed;
    }
}
