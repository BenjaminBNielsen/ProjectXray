/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/* @author Benjamin */

public class Employee extends Person{
    private int id;
    private int phoneNumber;
    private String address;
    private String eMail;
    private Occupation occupation;

    public Employee(String firstName, String lastName, int cpr, int phoneNumber,
            String address, String eMail, Occupation occupation) {
        super(firstName, lastName);
        
        this.id = cpr;
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
    
    

}
