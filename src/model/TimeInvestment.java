/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;

/**
 *
 * @author Benjamin
 */
public class TimeInvestment {

    private int id;
    private Hours hours;
    private Minutes minutes;
    private LocalDateTime startTime;
    private Employee employee;
    private Room room;

    public TimeInvestment(Hours hours, Minutes minutes, LocalDateTime startTime, Employee employee, Room room) {
        this.hours = hours;
        this.minutes = minutes;
        this.startTime = startTime;
        this.employee = employee;
        this.room = room;
    }

    public TimeInvestment(int id, Hours hours, Minutes minutes, LocalDateTime startTime, Employee employee, Room room) {
        this.id = id;
        this.hours = hours;
        this.minutes = minutes;
        this.startTime = startTime;
        this.employee = employee;
        this.room = room;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public Minutes getMinutes() {
        return minutes;
    }

    public void setMinutes(Minutes minutes) {
        this.minutes = minutes;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    } 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        if (room != null) {
            return employee.getFirstName() + " Skal være i {" + room.getRoomName() + "} " + startTime.toString("YYYY-dd-MM: HH:mm");
        } else {
            return employee.getFirstName() + " Skal være i { null } " + startTime.toString("YYYY-dd-MM: HH:mm");
        }
    }

}
