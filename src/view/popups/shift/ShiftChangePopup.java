/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups.shift;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import view.buttons.PopupMenuButton;
import view.popups.PopupWindow;

/**
 *
 * @author Mads
 */
public class ShiftChangePopup extends PopupWindow {
    
    private PopupMenuButton changeShift;
    private Label lCurrentShiftDate, lCurrentShiftTime, lCurrentShiftRoom, lChangeToRoom, lChangeToDate, lChangeStartTime, lChangeEndTime;
//    private TextField tCurrentShiftDate, tCurrentShiftTime, tCurrentShiftRoom;
    
    public ShiftChangePopup(){
        
    }
    
     public void display(String title) {
        
        
        lCurrentShiftDate = new Label(""); 
        lCurrentShiftTime = new Label(""); 
        lCurrentShiftRoom = new Label(""); 
        lChangeToRoom = new Label("Vælg et rum"); 
        lChangeToDate = new Label("Vælg en dato");
        lChangeStartTime = new Label("Vælg et starttidspunkt"); 
        lChangeEndTime = new Label("Vælg et starttidspunkt"); 
        
//        tCurrentShiftDate = new TextField();
//        tCurrentShiftTime = new TextField();
//        tCurrentShiftRoom = new TextField();
        
        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.setPadding(new Insets(0,0,15,0));
        
        super.addToCenter(vBox);
        vBox.getChildren().addAll(  lCurrentShiftDate,
                                    lCurrentShiftTime,
                                    lCurrentShiftRoom,
                                    lChangeToRoom,
                                    lChangeToDate,
                                    lChangeStartTime,
                                    lChangeEndTime
                                    
                                 );
        
        changeShift = new PopupMenuButton("Ændre vagt");
        
        changeShift.setOnAction(e -> {
            
            //SAY WHAAAT
            
            
            
          });
}
}
