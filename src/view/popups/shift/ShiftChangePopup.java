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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Room;
import model.TimeInvestment;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import view.buttons.PopupMenuButton;
import view.popups.ExceptionPopup;
import view.popups.PopupWindow;

/**
 *
 * @author Mads
 */
public class ShiftChangePopup extends PopupWindow {

    private PopupMenuButton changeShift;
    private Label lCurrentShiftEmployee, lCurrentShiftDate, lCurrentShiftTime, lCurrentShiftRoom, lChangeShift, lChangeToRoom, lChangeToDate, lChangeStartTime, lChangeEndTime;
    private ComboBox cChangeRoom;
    private TextField tChangeStartHour, tChangeStartMinute, tChangeEndHour, tChangeEndMinute, tChangeYear, tChangeMonth, tChangeDay;
    private ExceptionPopup exceptionPopup = new ExceptionPopup();

    public ShiftChangePopup() {

    }

    public void display(String title, TimeInvestment shift) {

        lCurrentShiftEmployee = new Label("Navn: " + shift.getEmployee().getFirstName() + " " + shift.getEmployee().getLastName());
        lCurrentShiftDate = new Label("Vagtdato: " + shift.getStartTime().toLocalDate());
        lCurrentShiftTime = new Label("Vagttidspunkt: " + shift.getStartTime().toLocalTime());
        lCurrentShiftRoom = new Label("Rum: " + shift.getRoom().getRoomName());
        
        
        lChangeShift = new Label("");
        lChangeToRoom = new Label("Vælg et nyt rum");
        lChangeToDate = new Label("Indskriv en ny dato");
        lChangeStartTime = new Label("Vælg et nyt starttidspunkt");
        lChangeEndTime = new Label("Vælg et nyt starttidspunkt");

        cChangeRoom = new ComboBox();

        tChangeYear = new TextField("Indtast årstal");
        tChangeMonth = new TextField("Indtast måned");
        tChangeDay = new TextField("Indtast dag");
        tChangeStartHour = new TextField("HH");
        tChangeStartMinute = new TextField("MM");
        tChangeEndHour = new TextField("HH");
        tChangeEndMinute = new TextField("MM");

        try {
            cChangeRoom.setPrefWidth(120);
            ArrayList<Room> rooms;
            rooms = Xray.getInstance().getRoomControl().getRooms();
            for (Room room : rooms) {
                cChangeRoom.getItems().add(room.getRoomName());
            }
            cChangeRoom.setPrefWidth(200);
           
            cChangeRoom.setValue("CT A");

        } catch (SQLException ex) {

        } catch (ClassNotFoundException ex) {

        }

        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.setPadding(new Insets(0, 0, 15, 0));
        

        vBox.getChildren().addAll(lCurrentShiftEmployee,
                lCurrentShiftDate,
                lCurrentShiftTime,
                lCurrentShiftRoom,
                lChangeShift,
                lChangeToRoom, cChangeRoom,
                lChangeToDate, tChangeDay, tChangeMonth, tChangeYear,
                lChangeStartTime, tChangeStartHour, tChangeStartMinute,
                lChangeEndTime, tChangeEndHour, tChangeEndMinute
        );

        changeShift = new PopupMenuButton("Ændre vagt");

        changeShift.setOnAction(e -> {

            Room changedRoom = (Room) cChangeRoom.getValue();

            shift.setRoom(changedRoom);

            try {
                int changedHours = Integer.parseInt(tChangeEndHour.getText()) - Integer.parseInt(tChangeStartHour.getText());
                if (Integer.parseInt(tChangeStartHour.getText()) < 24 && Integer.parseInt(tChangeEndHour.getText()) < 24) {
                    Hours changedHour = Hours.hours(changedHours);
                    shift.setHours(changedHour);
                } else {
                    exceptionPopup.display("Timeantallet skal være under 24");
                }
            } catch (NumberFormatException ex) {
                exceptionPopup.display("Timer skal skrives med tal.");
            }
            try {
                int changedMinutes = Integer.parseInt(tChangeEndMinute.getText()) - Integer.parseInt(tChangeStartMinute.getText());
                if (Integer.parseInt(tChangeEndMinute.getText()) < 60 && Integer.parseInt(tChangeStartMinute.getText()) < 60) {
                    Minutes changedMinute = Minutes.minutes(changedMinutes);
                    shift.setMinutes(changedMinute);
                } else {
                    exceptionPopup.display("Minutantal skal være under 60");
                }
            } catch (NumberFormatException ex) {
                exceptionPopup.display("Minutter skal skrives med tal.");
            }
            try {

                int yearChanged = Integer.parseInt(tChangeYear.getText());
                int monthChanged = Integer.parseInt(tChangeMonth.getText());
                int dayChanged = Integer.parseInt(tChangeDay.getText());
                int hourChanged = Integer.parseInt(tChangeStartHour.getText());
                int minuteChanged = Integer.parseInt(tChangeStartMinute.getText());
                if (monthChanged <= 12 && dayChanged <= 31) {
                    LocalDateTime changedDate = new LocalDateTime(yearChanged, monthChanged, dayChanged, hourChanged, minuteChanged);
                    shift.setStartTime(changedDate);
                } else {
                    exceptionPopup.display("Datoen skal rettes, dagstal skal være 31 eller under, og måneden 12 eller under.");
                }

                LocalDateTime changedDate = new LocalDateTime(yearChanged, monthChanged, dayChanged, hourChanged, minuteChanged);
                shift.setStartTime(changedDate);
            } catch (NumberFormatException ex) {
                exceptionPopup.display("Datoen skal skrives ind med tal.");
            }

            try {
                TimeInvestmentHandler.getInstance().updateTimeInvestment(shift);
            } catch (SQLException ex) {
                Logger.getLogger(ShiftChangePopup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ShiftChangePopup.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        super.addToBottomHBox(changeShift);
        
        super.addToCenter(vBox);
        
        super.display(title);
    }
}
