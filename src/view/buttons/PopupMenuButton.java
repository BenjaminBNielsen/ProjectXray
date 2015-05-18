/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.buttons;

import javafx.scene.control.*;

/**
 *
 * @author Benjamin
 */
public class PopupMenuButton extends Button{
    public static final double PREFERRED_HEIGHT = 50;
    public static final double PREFERRED_WIDTH = 150;
    
    public PopupMenuButton(String text){
        super(text);
        this.setPrefSize(PREFERRED_WIDTH,PREFERRED_HEIGHT);
        this.setStyle("-fx-font-family: Arial;"
                + "-fx-font-size: 14;");
        this.setFocusTraversable(false);
    }
    
    public PopupMenuButton(){
        super();
        this.setPrefSize(PREFERRED_WIDTH,PREFERRED_HEIGHT);
    }
}
