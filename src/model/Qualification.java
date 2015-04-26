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
public abstract class Qualification {

    private int id;
    private boolean training;
    private ArrayList<Employee> employees;
    private ArrayList<Room> rooms;
    String type;

    public Qualification(int id, boolean training, String type, ArrayList<Employee> employees,
            ArrayList<Room> rooms) {
        this.id = id;
        this.training = training;
        this.type = type;
        this.employees = employees;
        this.rooms = rooms;
    }

    public int getId() {
        return id;
    }

    public boolean isTraining() {
        return training;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTraining(boolean training) {
        this.training = training;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        String s = type;
        return s;
    }

}
