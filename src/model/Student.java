/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/* @author Benjamin */
public class Student extends Person {

    private int module;

    public Student(String firstName, String lastName, int module) {
        super(firstName, lastName);

        this.module = module;
    }

    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }

    
}
