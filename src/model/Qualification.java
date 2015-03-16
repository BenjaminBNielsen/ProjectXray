/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Yousef
 */
public class Qualification {

    private String qName;
    private boolean training;
    private Employee employee;
    private Room room;

    public Qualification(String qName, boolean training) {
        this.qName = qName;
        this.training = training;
    }

    public Qualification(String qName, boolean training, Employee employee, Room room) {
        this.qName = qName;
        this.training = training;
        this.employee = employee;
        this.room = room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getqName() {
        return qName;
    }

    public boolean isTraining() {
        return training;
    }

    public void setqName(String qName) {
        this.qName = qName;
    }

    public void setTraining(boolean training) {
        this.training = training;
    }

}
