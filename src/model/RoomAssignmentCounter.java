/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/* @author Benjamin */

public class RoomAssignmentCounter {
    private Room room;
    private int count;

    public RoomAssignmentCounter(Room room, int count) {
        this.room = room;
        this.count = count;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment() {
        count++;
    }
    
}
