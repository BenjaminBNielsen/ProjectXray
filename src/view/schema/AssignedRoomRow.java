/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import control.Xray;
import java.util.ArrayList;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import model.Room;
import model.TimeInvestment;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import view.Frontpage;

/**
 *
 * @author Jonas
 */
public class AssignedRoomRow extends TilePane{
    private Room room;
    private LabelTile roomLabel;
    
    public AssignedRoomRow(Room room, ArrayList<TimeInvestment> shifts, LocalDateTime startTime) {
        this.room = room;
        this.setOrientation(Orientation.HORIZONTAL);
        double tileWitdh = Xray.getInstance().getComputedTileWitdh() - ((Frontpage.STANDARD_PADDING * 2) / 3);
        System.out.println(tileWitdh);
        System.out.println(tileWitdh * 6);
        
        roomLabel = new LabelTile(room.getRoomName(), tileWitdh);
        
        //Tilføj rumnavn til venstre.
        this.getChildren().add(roomLabel);
        
        //Tilføj vagter til dagene på ugen. i0 = mandag, i6 = søndag.
        for (int i = 0; i < 5; i++) {
            ShiftTile shiftTile = new ShiftTile(Xray.getInstance().getShiftsOnDate(startTime.plusDays(i), shifts), tileWitdh);
            this.getChildren().add(shiftTile);
        }
        
    }
    
}
