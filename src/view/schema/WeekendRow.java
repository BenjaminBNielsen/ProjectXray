/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import control.Xray;
import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.TimeInvestment;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Jonas
 */
public class WeekendRow extends HBox {

    private String dayName;
    private ArrayList<TimeInvestment> shifts;
    private LabelTile labelTile;
    private ShiftTile shiftTile;
    private LocalDateTime date;
    

    public WeekendRow(String dayName, LocalDateTime date, ArrayList<TimeInvestment> shifts){
        this.dayName = dayName;
        this.date = date;
        this.shifts = shifts;
        
        shifts = Xray.getInstance().getShiftsOnDate(date, shifts);
        
        labelTile = new LabelTile(dayName);
        shiftTile = new ShiftTile(shifts, 450);
        
        
        
        
        
        this.getChildren().addAll(labelTile, shiftTile);
        
        
    }
}
