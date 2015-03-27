/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.Minutes;


/**
 *
 * @author Jonas
 */
public class Shift {
    private int id;
    private Minutes minutes;
    private Hours hours;
    private LocalDate localDate;
    
    public Shift(int id, Hours hours, Minutes minutes, LocalDate localDate) {
        this.id = id;
        this.hours = hours;
        this.minutes = minutes;
        this.localDate = localDate;
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

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

}
