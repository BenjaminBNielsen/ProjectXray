/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import java.util.ArrayList;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import model.Room;
import model.TimeInvestment;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Jonas
 */
public class ScheduleListItem extends TilePane{
    private Room room;
    private RoomLabel roomLabel;
    
    public ScheduleListItem(Room room, ArrayList<TimeInvestment> shifts, LocalDateTime startTime) {
        this.setStyle("-fx-border-color: red;");
        this.setPrefTileHeight(100);
        
        this.room = room;
        this.setOrientation(Orientation.HORIZONTAL);
        
        roomLabel = new RoomLabel(room.getRoomName());
        
        //Tilføj rumnavn til venstre.
        this.getChildren().add(roomLabel);
        
        //Tilføj vagter til dagene på ugen. i0 = mandag, i6 = søndag.
        for (int i = 0; i < 7; i++) {
            ShiftTile shiftTile = new ShiftTile(getShiftsOnDate(startTime.plusDays(i), shifts), 155);
            this.getChildren().add(shiftTile);
        }
        
        //Løb alle tiles igennem for at give dem en sort border.
        for (int i = 0; i < this.getChildren().size(); i++) {
            this.getChildren().get(i).setStyle("-fx-border-color: black;");
            
        }
        
    }
    
    private ArrayList<TimeInvestment> getShiftsOnDate(LocalDateTime date, ArrayList<TimeInvestment> shifts){
        ArrayList<TimeInvestment> shiftsOnDate = new ArrayList<>();
        
        for (int i = 0; i < shifts.size(); i++) {
            if(shifts.get(i).getStartTime().equals(date)){
                shiftsOnDate.add(shifts.get(i));
            }
        }
        
        return shiftsOnDate;
    }
}
