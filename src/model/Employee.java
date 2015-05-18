/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.RoomAssignmentCounter;
import java.util.ArrayList;

/* @author Benjamin */
public class Employee extends Person {

    private int id;
    private int phoneNumber;
    private String address;
    private String eMail;
    private Occupation occupation;
    private ArrayList<RoomAssignmentCounter> counters = new ArrayList<>();

    public Employee(String firstName, String lastName, int id, int phoneNumber,
            String address, String eMail, Occupation occupation) {
        super(id, firstName, lastName);

        this.id = id;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.eMail = eMail;
        this.occupation = occupation;
    }

    public int getId() {
        return id;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String geteMail() {
        return eMail;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public ArrayList<RoomAssignmentCounter> getCounters() {
        return counters;
    }

    public void setCounters(ArrayList<RoomAssignmentCounter> counters) {
        this.counters = counters;
    }
    
    public RoomAssignmentCounter getRoomAssignmentCounter(Room room){
        for (int i = 0; i < counters.size(); i++) {
            RoomAssignmentCounter counter = counters.get(i);
            if(counter.getRoom().getRoomName().equals(room.getRoomName())){
                return counter;
            }
        }
        
        return null;
    }
    
    public void addCounter(RoomAssignmentCounter rac){
        counters.add(rac);
    }

    public void increment(Room room) {
        for (RoomAssignmentCounter counter : counters) {
            if (counter.getRoom().getRoomName().equals(room.getRoomName())) {
                counter.increment();
            }
        }
    }

    @Override
    public String toString() {
        String str = id + ": " + super.getFirstName() + " " + super.getLastName();
        return str;
    }

}
