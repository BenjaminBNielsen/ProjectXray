/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Yousef
 */
public class LimitQualification extends Qualification {
    private int limit;

    public LimitQualification(int id, boolean training, String type,
            ArrayList<Employee> employees, ArrayList<Room> rooms, int limit) {
        super(id, training, type,employees,rooms);
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    

}
