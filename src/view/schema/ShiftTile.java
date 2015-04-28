/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import java.util.ArrayList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import model.Employee;
import model.TimeInvestment;

/**
 *
 * @author Benjamin
 */
public class ShiftTile extends BorderPane{
    
    ArrayList<TimeInvestment> shifts;
    VBox bottomVbox = new VBox(5);
    
    public ShiftTile(ArrayList<TimeInvestment> shifts, double width){
        this.shifts = shifts;
        this.setPrefSize(width, 100);
        bottomVbox.setAlignment(Pos.TOP_CENTER);
        this.setCenter(bottomVbox);
                
        initLabels();
    }

    private void initLabels() {
        for (int i = 0; i < shifts.size(); i++) {
            bottomVbox.getChildren().add(new EmployeeLabel(shifts.get(i)));
        }
    }

    public ArrayList<TimeInvestment> getShifts() {
        return shifts;
    }

    public void setShifts(ArrayList<TimeInvestment> shifts) {
        this.shifts = shifts;
    }

    public VBox getBottomVbox() {
        return bottomVbox;
    }

    public void setBottomVbox(VBox bottomVbox) {
        this.bottomVbox = bottomVbox;
    }

}
