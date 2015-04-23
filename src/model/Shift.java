/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Jonas
 */
public class Shift {

    private int id;
    private Minutes minutes;
    private Hours hours;
    private LocalDateTime localDateTime;
    private Employee employee;

    public Shift(int id, Hours hours, Minutes minutes, LocalDateTime localDateTime, Employee employee) {
        this.employee = employee;
        this.id = id;
        this.hours = hours;
        this.minutes = minutes;
        this.localDateTime = localDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Minutes getMinutes() {
        return minutes;
    }

    public void setMinutes(Minutes minutes) {
        this.minutes = minutes;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    

    @Override
    public String toString() {
        LocalDateTime endTime = localDateTime.plusHours(hours.getHours());
        endTime = endTime.plusMinutes(minutes.getMinutes());
        //Sørger for at strengen printes i det rigtige format.
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-dd-MM: HH:mm");

        return employee.getFirstName() + " " + employee.getLastName() + "'s vagt starter "
                + localDateTime.toString(fmt) + " og slutter " + endTime.toString(fmt);
    }
}
