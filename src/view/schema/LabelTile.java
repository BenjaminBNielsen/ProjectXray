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
public class LabelTile extends HBox{
    private Label label;
    private int witdh;
    
    public LabelTile(String text){
        label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        this.getChildren().add(label);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-border-color: black;");
    }
    
    public LabelTile(String text, double witdh) {
        label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        this.getChildren().add(label);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-border-color: black;");
        this.setPrefSize(witdh, 100);
    }
}
