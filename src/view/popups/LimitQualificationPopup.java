/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups;

import java.util.ArrayList;
import model.Employee;
import model.LimitQualification;
import model.Room;

/**
 *
 * @author Jonas
 */
public class LimitQualificationPopup {
    private ArrayList<Room> rooms;
    private ArrayList<Employee> employees;
    
    public LimitQualificationPopup(ArrayList<Room> rooms, ArrayList<Employee> employees) {
        this.rooms = rooms;
        this.employees = employees;
    }
}
