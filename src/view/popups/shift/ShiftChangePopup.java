/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups.shift;


import control.Xray;
import handlers.TimeInvestmentHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Room;
import model.TimeInvestment;
import org.joda.time.LocalDateTime;
import view.buttons.PopupMenuButton;
import view.popups.PopupWindow;

/**
 *
 * @author Mads
 */
public class ShiftChangePopup extends PopupWindow {
    
    private PopupMenuButton changeShift;
    private Label lCurrentShiftEmployee, lCurrentShiftDate, lCurrentShiftTime, lCurrentShiftRoom, lChangeToRoom, lChangeToDate, lChangeStartTime, lChangeEndTime;
    private ComboBox  cChangeRoom , cChangeDate;
    private TextField tChangeStartHour, tChangeStartMinute, tChangeEndHour, tChangeEndMinute, tChangeYear, tChangeMonth, tChangeDay;
    
    public ShiftChangePopup(){
        
    }
    
     public void display(String title, TimeInvestment shift) {
        
        lCurrentShiftEmployee = new Label("Navn; "+shift.getEmployee());
        lCurrentShiftDate = new Label("Vagtdato: "+shift.getStartTime()); 
        lCurrentShiftTime = new Label("Vagttidspunkt: "+shift.getHours()+":"+shift.getMinutes()); 
        lCurrentShiftRoom = new Label("Rum: "+shift.getRoom()); 
        lChangeToRoom = new Label("Vælg et rum"); 
        lChangeToDate = new Label("Indskriv en ny dato");
        lChangeStartTime = new Label("Vælg et nyt starttidspunkt"); 
        lChangeEndTime = new Label("Vælg et nyt starttidspunkt");
        
        cChangeRoom = new ComboBox();
        cChangeDate = new ComboBox();
        
        tChangeYear = new TextField("Indtast årstal");
        tChangeMonth = new TextField("Indtast måned");
        tChangeDay = new TextField("Indtast dag");
        
        tChangeStartHour = new TextField("HH");
        tChangeStartMinute = new TextField("MM");
        tChangeEndHour = new TextField("HH");
        tChangeEndMinute = new TextField("MM");
        
        
          try {
                            ArrayList<Room> rooms;
                            rooms = Xray.getInstance().getRoomControl().getRooms();
                            for (Room room : rooms) {
                                cChangeRoom.getItems().add(room);
                            }
                        } catch (SQLException ex) {
                            
                        } catch (ClassNotFoundException ex) {
                            
                        } 
        
        
        //ArrayList<LocalDateTime> ldts = new ArrayList<>();
          
        LocalDateTime ldt1 = new LocalDateTime(2015, 4, 20, 0, 0);
        LocalDateTime ldt2 = new LocalDateTime(2015, 4, 27, 0, 0);
        LocalDateTime ldt3 = new LocalDateTime(2015, 5, 4, 0, 0);
        cChangeDate.getItems().addAll(ldt1,ldt2, ldt3);
        
        
        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.setPadding(new Insets(0,0,15,0));
        
        super.addToCenter(vBox);
        vBox.getChildren().addAll(  lCurrentShiftEmployee,
                                    lCurrentShiftDate,
                                    lCurrentShiftTime,
                                    lCurrentShiftRoom,
                                    lChangeToRoom, cChangeRoom,
                                    lChangeToDate, cChangeDate,
                                    lChangeStartTime, tChangeStartHour,tChangeStartMinute,
                                    lChangeEndTime, tChangeEndHour, tChangeEndMinute
                                    
                                 );
        
        changeShift = new PopupMenuButton("Ændre vagt");
        
        changeShift.setOnAction(e -> {
            
            
           //TimeInvestmentHandler.getInstance().updateTimeInvestment(shift);
            
            
            
          });
}
}