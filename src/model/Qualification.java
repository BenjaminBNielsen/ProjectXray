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

    public Qualification(String qName, boolean training) {
        this.qName = qName;
        this.training = training;
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
