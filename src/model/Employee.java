/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/* @author Benjamin */

public class Employee extends Person{
    private int cpr;
    private int phoneNumber;
    private String address;
    private String eMail;
    private Occupation occupation;

    public Employee(String firstName, String lastName, int cpr, int phoneNumber,
            String address, String eMail, Occupation occupation) {
        super(firstName, lastName);
        
        this.cpr = cpr;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.eMail = eMail;
        this.occupation = occupation;
    }

}
