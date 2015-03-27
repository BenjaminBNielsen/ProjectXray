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
 * @author Jonas
 */
public class Shift {
    private int id;
    private Minutes minutes;
    private Hours hours;
    private LocalDateTime localDateTime;
    
    public Shift(int id, Hours hours, Minutes minutes, LocalDateTime localDateTime) {
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

    public LocalDateTime getLocalDate() {
        return localDateTime;
    }

    public void setLocalDate(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

}
