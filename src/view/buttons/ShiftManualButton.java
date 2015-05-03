/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.buttons;

import javafx.scene.control.Button;
import view.popups.shift.ShiftManualPopup;

/**
 *
 * @author Yousef
 */
public class ShiftManualButton extends Button{
    public static final double PREFERRED_HEIGHT = 50;
    public static final double PREFERRED_WIDTH = 50;
    
    public ShiftManualButton(String text){
        super(text);
        this.setPrefSize(PREFERRED_WIDTH,PREFERRED_HEIGHT);
    }
    
    public ShiftManualButton(){
        super();
        this.setPrefSize(PREFERRED_WIDTH,PREFERRED_HEIGHT);
    }
    
    public void setOnActionCode(){
        ShiftManualPopup shiftManualPopup = new ShiftManualPopup();
        shiftManualPopup.display("Opret manuel vagt");
    }
}
