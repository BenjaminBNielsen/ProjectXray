/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import java.util.ArrayList;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Employee;
import model.TimeInvestment;

/**
 *
 * @author Benjamin
 */
public class ShiftTile extends BorderPane{
    
    ArrayList<TimeInvestment> shifts;
    VBox bottomVBox = new VBox(5);
    
    public ShiftTile(ArrayList<TimeInvestment> shifts){
        this.shifts = shifts;
        this.setBottom(bottomVBox);
        
        initLabels();
        this.setMinSize(60, 50);
        this.setStyle("-fx-border-color: black;");
    }

    private void initLabels() {
        for (int i = 0; i < shifts.size(); i++) {
            Employee currentShiftEmployee = shifts.get(i).getEmployee();
            bottomVBox.getChildren().add(new EmployeeLabel(currentShiftEmployee));
        }
    }

    public ArrayList<TimeInvestment> getShifts() {
        return shifts;
    }

    public void setShifts(ArrayList<TimeInvestment> shifts) {
        this.shifts = shifts;
    }

    public VBox getBottomVBox() {
        return bottomVBox;
    }

    public void setBottomVBox(VBox bottomVBox) {
        this.bottomVBox = bottomVBox;
    }
}
