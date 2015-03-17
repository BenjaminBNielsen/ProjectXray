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

    private QualificationType type;
    private boolean training;
    private Employee employee;
    private Room room;

    public Qualification(QualificationType type, boolean training) {
        this.type = type;
        this.training = training;
    }

    public Qualification(QualificationType type, boolean training, Employee employee, Room room) {
        this.type = type;
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

    public QualificationType getType() {
        return type;
    }

    public boolean isTraining() {
        return training;
    }

    public void setqName(QualificationType type) {
        this.type = type;
    }

    public void setTraining(boolean training) {
        this.training = training;
    }

}
