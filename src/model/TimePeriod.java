/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import org.joda.time.LocalDateTime;

/* @author Benjamin */

public class TimePeriod {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int min;
    private int max;
    private Room room;
    private Employee employee;

    public TimePeriod(LocalDateTime startTime, LocalDateTime endTime, int min, int max, Room room, Employee employee) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.min = min;
        this.max = max;
        this.room = room;
        this.employee = employee;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    
    
    
}
