/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups.shift;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import view.buttons.SettingsButton;

/**
 *
 * @author Benjamin
 */
public class ShiftPanel extends HBox {

    //Buttons:
    private Button dayShift, eveningShift, nightShift;
    private SettingsButton settingsButton;

    //private Shift shift;
    
    //SettingsPopup
    ShiftPanelConfig configPanel;
    
    public ShiftPanel(String dayName, /*date*/ String startTime) {
        configPanel = new ShiftPanelConfig();
        initButtons();
        setup();
    }

    /*
     public shift getShift(){
     return shift
     }
    
     public void setShift(Shift shift){
     this.shift = shift;
     }
     */
    private void initButtons() {
        settingsButton = new SettingsButton();
        settingsButton.setDisable(true);
        
        settingsButton.setOnAction(e -> {

            configPanel.display("Foretag ændring");
            
        });

        dayShift = new Button("Dagvagt");
        dayShift.setOnAction(e -> {
            //shift = new Shift(x,y,z);

            settingsButton.setDisable(false);
        });
        eveningShift = new Button("Aftenvagt");
        eveningShift.setOnAction(e -> {
            //shift = new Shift(x,y,z);

            settingsButton.setDisable(false);
        });
        nightShift = new Button("Nattevagt");
        nightShift.setOnAction(e -> {
            //shift = new Shift(x,y,z);

            settingsButton.setDisable(false);

        });

        configPanel.getChangeButton().setOnAction(e -> {

        });
    }

    private void setup() {
        super.getChildren().addAll(dayShift, eveningShift, nightShift, settingsButton);
    }
}
