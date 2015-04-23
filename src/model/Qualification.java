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

    private int id;
    private boolean training;

    public Qualification(int id, boolean training) {
        this.id = id;
        this.training = training;
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
    
    @Override
    public String toString() {
        String s = "røntgen";
    return s;
    }
       

}
