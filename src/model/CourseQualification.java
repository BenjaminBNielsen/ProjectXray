/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Jonas
 */
public class CourseQualification extends Qualification {
    private int limit;
    private LocalDateTime localDateStart, localDateEnd;
    
    public CourseQualification(int id, String type, ArrayList<Employee> employees, ArrayList<Room> rooms, int limit, LocalDateTime localDateStart, LocalDateTime localDateEnd) {
        super(id, type, employees, rooms);
        this.limit = limit;
        this.localDateStart = localDateStart;
        this.localDateEnd = localDateEnd;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public LocalDateTime getLocalDateStart() {
        return localDateStart;
    }

    public void setLocalDateStart(LocalDateTime localDateStart) {
        this.localDateStart = localDateStart;
    }

    public LocalDateTime getLocalDateEnd() {
        return localDateEnd;
    }

    public void setLocalDateEnd(LocalDateTime localDateEnd) {
        this.localDateEnd = localDateEnd;
    }
    
}
