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
public class RoomQualification extends Qualification {

    public RoomQualification(int id, String type,
            ArrayList<Employee> employees, ArrayList<Room> rooms) {
        super(id, type, employees, rooms);
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}
