/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Room;
import model.TimeInvestment;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Jonas
 */
public class ScheduleListItem extends HBox{
    private Room room;
    private Label roomLabel;
    
    public ScheduleListItem(Room room, ArrayList<TimeInvestment> shifts, LocalDateTime startTime) {
        this.room = room;
        roomLabel = new Label(room.getRoomName());
        
        //Tilføj rumnavn til venstre.
        this.getChildren().add(roomLabel);
        
        //Tilføj mandagens vagter.
        this.getChildren().add(new ShiftTile(getShiftsOnDate(startTime, shifts)));
        
        //Tilføj tirsdagens vagter.
        this.getChildren().add(new ShiftTile(getShiftsOnDate(startTime.plusDays(1), shifts)));
        
        //Onsdag:
        this.getChildren().add(new ShiftTile(getShiftsOnDate(startTime.plusDays(2), shifts)));
        
        //Torsdag:
        this.getChildren().add(new ShiftTile(getShiftsOnDate(startTime.plusDays(3), shifts)));
        
        //Fredag:
        this.getChildren().add(new ShiftTile(getShiftsOnDate(startTime.plusDays(4), shifts)));
        
        //Lørdag:
        this.getChildren().add(new ShiftTile(getShiftsOnDate(startTime.plusDays(5), shifts)));
        
        //Søndag:
        this.getChildren().add(new ShiftTile(getShiftsOnDate(startTime.plusDays(6), shifts)));
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
