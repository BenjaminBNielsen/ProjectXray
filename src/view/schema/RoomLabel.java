/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Benjamin
 */
public class RoomLabel extends HBox{
    private Label roomLabel;
    
    public RoomLabel(String text){
        roomLabel = new Label(text);
        roomLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        this.getChildren().add(roomLabel);
        this.setAlignment(Pos.CENTER);
    }
}
