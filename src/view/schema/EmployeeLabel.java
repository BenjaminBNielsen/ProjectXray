/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.Employee;
import model.TimeInvestment;
import view.popups.shift.ShiftChangePopup;

/**
 *
 * @author Benjamin
 */
public class EmployeeLabel extends Button{
 
    private TimeInvestment shift;
    
    public EmployeeLabel(TimeInvestment shift){
        this.shift = shift;
        //Gør knappen gennemsigtig så man kun ser teksten.
        //Gør desuden teksten 18px stor og af typen Arial.
        this.setStyle("-fx-focus-color: transparent;"
                + "-fx-faint-focus-color: transparent;"
                + "-fx-background-color: transparent;"
                + "-fx-font-family: Arial;"
                + "-fx-font-size: 18px;");
        this.setTextAlignment(TextAlignment.CENTER);
        this.setWrapText(true);
        String btnContent = shift.getEmployee().getFirstName().toUpperCase() +
                " " + shift.getEmployee().getLastName().toUpperCase().charAt(0);
        
        this.setText(btnContent);
        this.setOnAction(e -> {
            ShiftChangePopup scp = new ShiftChangePopup();
            scp.display("Foretag ændring på vagt", shift);
        });
    }

    public TimeInvestment getShift() {
        return shift;
    }

    public void setShift(TimeInvestment shift) {
        this.shift = shift;
    }
    
}
