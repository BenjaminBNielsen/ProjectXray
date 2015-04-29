/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.TimeInvestment;

/**
 *
 * @author Jonas
 */
public class WeekendRow extends HBox {

    private String dayName;
    private ArrayList<TimeInvestment> shifts;
    private LabelTile labelTile;
    private ShiftTile shiftTile;
    

    public WeekendRow(String dayName, ArrayList<TimeInvestment> shifts){
        this.dayName = dayName;
        this.shifts = shifts;
        labelTile = new LabelTile(dayName);
        shiftTile = new ShiftTile(shifts, 450);
        
        for (int i = 0; i < shifts.size(); i++) {
            if (shifts.get(i).getStartTime().getDayOfWeek() == 6) {
                
            } else if (shifts.get(i).getStartTime().getDayOfWeek() == 7) {
                
            }
            
        }
        
        this.getChildren().addAll(labelTile, shiftTile);
        
        
    }
}
