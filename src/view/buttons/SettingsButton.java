/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Benjamin
 */
public class SettingsButton extends Button{
    
    public SettingsButton(){
                        //knap med settings icon
        Image settingIcon = new Image("pictures/settings.png");
        this.setGraphic(new ImageView(settingIcon));
    }
    
}
