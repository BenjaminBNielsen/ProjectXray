/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/* @author Benjamin */
public class Student extends Person {

    private int id;
    private int module;

    public Student(int id, String firstName, String lastName, int module) {
        super(id, firstName, lastName);

        this.id = id;
        this.module = module;
    }

    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }

    public String toString(){
        return super.getFirstName() + ": " + super.getId();
    }
    
}
