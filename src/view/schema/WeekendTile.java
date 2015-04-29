/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

/**
 *
 * @author Jonas
 */
public class WeekendTile extends BorderPane {

    private String tileName;
    private Label titleLabel;
    private int witdh, height;

    public WeekendTile(String tileName, int witdh, int height) {
        this.tileName = tileName;
        titleLabel = new Label(tileName);
        this.setPrefSize(this.witdh, this.height);
        titleLabel.setAlignment(Pos.TOP_CENTER);
        this.getChildren().add(titleLabel);
    }

}
