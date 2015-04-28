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
    private ArrayList<WeekendTile> weekendTiles;
    private WeekendTile weekendTile;

    public WeekendRow(String dayName, ArrayList<TimeInvestment> shifts){
        this.dayName = dayName;
        
        
    }
    
    public void addWeekendTiles(ArrayList<WeekendTile> weekendTiles) {
        this.weekendTiles = weekendTiles;
        for (int i = 0; i < weekendTiles.size(); i++) {
            weekendTile = weekendTiles.get(i);
            this.getChildren().add(weekendTile);
        }
    }
}
