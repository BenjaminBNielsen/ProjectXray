/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Employee;
import model.TimeInvestment;

/**
 *
 * @author Benjamin
 */
public class EmployeeLabel extends Button{
 
    private TimeInvestment shift;
    
    public EmployeeLabel(TimeInvestment shift){
        this.shift = shift;
        this.setText(shift.getEmployee().getFirstName() + " " + shift.getEmployee().getLastName());
        this.setOnAction(e -> {
            //new changeShiftPopup("title");
        });
    }

    public TimeInvestment getShift() {
        return shift;
    }

    public void setShift(TimeInvestment shift) {
        this.shift = shift;
    }
    
}
