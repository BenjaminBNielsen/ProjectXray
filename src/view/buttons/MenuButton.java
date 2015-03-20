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
public class MenuButton extends Button{
    
    public MenuButton(String text){
        super(text);
        this.setPrefSize(150,50);
    }
    
    public MenuButton(){
        super();
        this.setPrefSize(150,50);
    }
}
