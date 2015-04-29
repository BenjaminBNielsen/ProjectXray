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
import view.Frontpage;

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
        double tileWitdh = Xray.getInstance().getComputedTileWitdh();
        int padding = Frontpage.STANDARD_PADDING;
        
        shifts = Xray.getInstance().getShiftsOnDate(date, shifts);
        
        labelTile = new LabelTile(dayName, tileWitdh - (padding));
        shiftTile = new ShiftTile(shifts, (tileWitdh*5) - (padding*5));
        System.out.println(tileWitdh - (padding*2));
        System.out.println((tileWitdh*5) - (padding*2));
        System.out.println(tileWitdh - (padding*2) + (tileWitdh*5) - (padding*2));
        
        
        
        
        this.getChildren().addAll(labelTile, shiftTile);
        
        
    }
}
