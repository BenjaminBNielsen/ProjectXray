/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Room;

/**
 *
 * @author Benjamin
 */
public class RoomTile extends HBox{
    private RoomLabel labelRoom;
    private Room room;
    private Label label;
    
    public RoomTile(String text, Room room){
        this.room = room;
        labelRoom = new RoomLabel(text.toUpperCase(), room);
        labelRoom.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        labelRoom.setWrapText(true);
        this.setStyle("-fx-border-color: black;"
                + "-fx-border-width: 0.5px;"); 
        
        this.getChildren().add(labelRoom);
        this.setAlignment(Pos.CENTER);
        
    }

    RoomTile(String text) {
        label = new Label(text.toUpperCase());
        label.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        label.setWrapText(true);
        this.setStyle("-fx-border-color: black;"
                + "-fx-border-width: 0.5px;"); 
        
        this.getChildren().add(label);
        this.setAlignment(Pos.CENTER);
    }
}
