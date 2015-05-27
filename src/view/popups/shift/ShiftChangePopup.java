/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups.shift;

import control.Xray;
import exceptions.DatabaseException;
import technicalServices.persistence.TimeInvestmentHandler;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
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
import view.Frontpage;
import view.buttons.PopupMenuButton;
import view.popups.ExceptionPopup;
import view.popups.PopupWindow;

/**
 *
 * @author Mads
 */
public class ShiftChangePopup extends PopupWindow {

    private PopupMenuButton changeShift;
    private Label lCurrentShiftEmployee, lCurrentShiftDate, lCurrentShiftTime, lCurrentShiftRoom, lChangeShift, lChangeToDate, lChangeStartTime, lChangeEndTime, lShiftChanged;
    private ComboBox cChangeRoom, cChangeYear, cChangeMonth, cChangeDay, cChangeStartHour, cChangeStartMinute, cChangeEndHour, cChangeEndMinute;
//    private TextField tChangeStartHour, tChangeStartMinute, tChangeEndHour, tChangeEndMinute, tChangeYear, tChangeMonth, tChangeDay;
    private ExceptionPopup exceptionPopup = new ExceptionPopup();
    private boolean hasFailed;

    public ShiftChangePopup() {

    }

    public void display(String title, TimeInvestment shift) {

        lCurrentShiftEmployee = new Label("Navn: " + shift.getEmployee().getFirstName() + " " + shift.getEmployee().getLastName());
        lCurrentShiftDate = new Label("Vagtdato: " + shift.getStartTime().toLocalDate().getDayOfMonth() + "-" + shift.getStartTime().toLocalDate().getMonthOfYear() + "-" + shift.getStartTime().toLocalDate().getYear());
        lCurrentShiftTime = new Label("Vagttidspunkt: " + shift.getStartTime().toLocalTime().getHourOfDay() + ":" + shift.getStartTime().toLocalTime().getMinuteOfHour());
        lCurrentShiftRoom = new Label("Rum: " + shift.getRoom().getRoomName());

        lChangeShift = new Label("");
        //lChangeToRoom = new Label("Vælg et nyt rum");
        lChangeToDate = new Label("Vælg en ny dato");
        lChangeStartTime = new Label("Vælg et nyt starttidspunkt");
        lChangeEndTime = new Label("Vælg et nyt sluttidspunkt");
        lShiftChanged = new Label("Vagten er nu ændret");
        lShiftChanged.setVisible(false);

        cChangeRoom = new ComboBox();
        cChangeYear = new ComboBox();
        cChangeMonth = new ComboBox();
        cChangeDay = new ComboBox();
        cChangeStartHour = new ComboBox();
        cChangeStartMinute = new ComboBox();
        cChangeEndHour = new ComboBox();
        cChangeEndMinute = new ComboBox();

//        tChangeYear = new TextField("Indtast årstal");
//        tChangeMonth = new TextField("Indtast måned");
//        tChangeDay = new TextField("Indtast dag");
//        tChangeStartHour = new TextField("Timeantal");
//        tChangeStartMinute = new TextField("Minutantal");
//        tChangeEndHour = new TextField("Timeantal");
//        tChangeEndMinute = new TextField("Minutantal");
        try {
            cChangeRoom.setPrefWidth(120);
            ArrayList<Room> rooms;
            rooms = Xray.getInstance().getRoomControl().getRooms();
            for (Room room : rooms) {
                cChangeRoom.getItems().add(room);
            }
            cChangeRoom.setPrefWidth(400);
            cChangeRoom.setValue("Vælg et nyt rum på listen");

        } catch (DatabaseException ex) {

        }
        
        for (int i = 1; i <= 31; i++) {
            cChangeDay.getItems().add(i);
        }
        cChangeDay.setValue("Vælg en dag");
        
        for (int i = 1; i <= 12; i++) {
            cChangeMonth.getItems().add(i);
        }
        cChangeMonth.setValue("Vælg en måned");
        
         for (int i = 2015; i <= 2025 ; i++) {
            cChangeYear.getItems().add(i);
        }
        cChangeYear.setValue("Vælg et år");
        
         for (int i = 0; i <= 23; i++) {
            cChangeStartHour.getItems().add(i);
        }
        cChangeStartHour.setValue("Vælg starttime");
        
         for (int i = 0; i <= 59; i = i+5) {
            cChangeStartMinute.getItems().add(i);
        }
        cChangeStartMinute.setValue("Vælg startminut");
        
        for (int i = 0; i <= 23; i++) {
            cChangeEndHour.getItems().add(i);
        }
        cChangeEndHour.setValue("Vælg sluttime");
        
         for (int i = 00; i <= 59; i = i +5) {
            cChangeEndMinute.getItems().add(i);
        }
        cChangeEndMinute.setValue("Vælg slutminut");
        
        

        for (int i = 1; i <= 31; i++) {
            cChangeDay.getItems().add(i);
        }
        cChangeDay.setPrefWidth(400);
        cChangeDay.setValue("Vælg en dag");

        for (int i = 1; i <= 12; i++) {
            cChangeMonth.getItems().add(i);
        }
        cChangeMonth.setPrefWidth(400);
        cChangeMonth.setValue("Vælg en måned");

        for (int i = 2015; i <= 2025; i++) {
            cChangeYear.getItems().add(i);
        }
        cChangeYear.setPrefWidth(400);
        cChangeYear.setValue("Vælg et år");

        for (int i = 0; i <= 23; i++) {
            cChangeStartHour.getItems().add(i);
        }
        cChangeStartHour.setPrefWidth(400);
        cChangeStartHour.setValue("Vælg starttime");

        for (int i = 0; i <= 59; i = i + 5) {
            cChangeStartMinute.getItems().add(i);
        }
        cChangeStartMinute.setPrefWidth(400);
        cChangeStartMinute.setValue("Vælg startminut");

        for (int i = 0; i <= 23; i++) {
            cChangeEndHour.getItems().add(i);
        }
        cChangeEndHour.setPrefWidth(400);
        cChangeEndHour.setValue("Vælg sluttime");

        for (int i = 00; i <= 59; i = i + 5) {
            cChangeEndMinute.getItems().add(i);
        }
        cChangeEndMinute.setPrefWidth(400);
        cChangeEndMinute.getSelectionModel().select(1);

        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.setPadding(new Insets(0, 0, 15, 0));

        /*vBox.getChildren().addAll(lCurrentShiftEmployee,
         lCurrentShiftDate,
         lCurrentShiftTime,
         lCurrentShiftRoom,
         lChangeShift,
         cChangeRoom,
         lChangeToDate, tChangeDay, tChangeMonth, tChangeYear,
         lChangeStartTime, tChangeStartHour, tChangeStartMinute,
         lChangeEndTime, tChangeEndHour, tChangeEndMinute,
         lShiftChanged*/
        vBox.getChildren().addAll(lCurrentShiftEmployee,
                lCurrentShiftDate,
                lCurrentShiftTime,
                lCurrentShiftRoom,
                lChangeShift,
                cChangeRoom,
                lChangeToDate, cChangeDay, cChangeMonth, cChangeYear,
                lChangeStartTime, cChangeStartHour, cChangeStartMinute,
                lChangeEndTime, cChangeEndHour, cChangeEndMinute,
                lShiftChanged
        );
        changeShift = new PopupMenuButton("Ændre vagt");

        changeShift.setOnAction(e -> {

//            if (cChangeRoom.getSelectionModel().isEmpty()) {
//                exceptionPopup.display("Der skal vælges et rum");
//            } else if (Integer.parseInt(tChangeDay.getText()) > 31) {
//                exceptionPopup.display("Dagstallet skal være 31 eller under");
//            } else if (Integer.parseInt(tChangeMonth.getText()) > 12) {
//                exceptionPopup.display("Måneden skal være 12 eller under");
//            } else if (Integer.parseInt(tChangeStartHour.getText()) >= 24 || Integer.parseInt(tChangeEndHour.getText()) >= 24) {
//                exceptionPopup.display("Timeantallet skal være under 24");
//            } else if (Integer.parseInt(tChangeEndMinute.getText()) >= 60 || Integer.parseInt(tChangeStartMinute.getText()) >= 60) {
//                exceptionPopup.display("Minutantal skal være under 60");
//            } else {
//                Room changedRoom = (Room) cChangeRoom.getSelectionModel().getSelectedItem();
//                shift.setRoom(changedRoom);
//                int changedHours = (cChangeEndHour.getSelectionModel().getSelectedIndex()- cChangeStartHour.getSelectionModel().getSelectedIndex());
//                Hours changedHour = Hours.hours(changedHours);
//                shift.setHours(changedHour);
//                
//                int changedMinutes = (cChangeEndMinute.getSelectionModel().getSelectedIndex() - cChangeStartMinute.getSelectionModel().getSelectedIndex());
//                Minutes changedMinute = Minutes.minutes(changedMinutes);
//                shift.setMinutes(changedMinute);
//                int yearChanged = Integer.parseInt(cChangeYear.getValue().toString());
//                int monthChanged = Integer.parseInt(cChangeMonth.getValue().toString());
//                int dayChanged = Integer.parseInt(cChangeDay.getValue().toString());
//                int hourChanged = Integer.parseInt(cChangeStartHour.getValue().toString());
//                int minuteChanged = Integer.parseInt(cChangeStartMinute.getValue().toString());
//                LocalDateTime changedDate = new LocalDateTime(yearChanged, monthChanged, dayChanged, hourChanged, minuteChanged);
//                shift.setStartTime(changedDate);
            hasFailed = false;
            if (!hasFailed) {
                try {
                    Room changedRoom = (Room) cChangeRoom.getSelectionModel().getSelectedItem();
                    shift.setRoom(changedRoom);

                } catch (Exception ex) {
                    hasFailed = true;
                    exceptionPopup.display("Der skal vælges et nyt rum");
                }
            }
            if (!hasFailed) {
                try {
                    int changedHours = (Integer.parseInt(cChangeEndHour.getValue().toString())) - (Integer.parseInt(cChangeStartHour.getValue().toString()));
                    Hours changedHour = Hours.hours(changedHours);
                    shift.setHours(changedHour);

                } catch (NumberFormatException ex) {
                    hasFailed = true;
                    exceptionPopup.display("Der skal vælges timer");
                }
            }
            if (!hasFailed) {
                try {
                    int changedMinutes = (Integer.parseInt(cChangeEndMinute.getValue().toString())) - (Integer.parseInt(cChangeStartMinute.getValue().toString()));
                    Minutes changedMinute = Minutes.minutes(changedMinutes);
                    shift.setMinutes(changedMinute);

                } catch (NumberFormatException ex) {
                    hasFailed = true;
                    exceptionPopup.display("Der skal vælges minutter.");
                }
            }
            if (!hasFailed) {
                try {
                    int yearChanged = Integer.parseInt(cChangeYear.getValue().toString());
                    int monthChanged = Integer.parseInt(cChangeMonth.getValue().toString());
                    int dayChanged = Integer.parseInt(cChangeDay.getValue().toString());
                    int hourChanged = Integer.parseInt(cChangeStartHour.getValue().toString());
                    int minuteChanged = Integer.parseInt(cChangeStartMinute.getValue().toString());
                    LocalDateTime changedDate = new LocalDateTime(yearChanged, monthChanged, dayChanged, hourChanged, minuteChanged);
                    shift.setStartTime(changedDate);
                } catch (NumberFormatException ex) {
                    hasFailed = true;
                    exceptionPopup.display("Datoen skal vælges");
                }
            }

            if (!hasFailed) {
                try {
                    TimeInvestmentHandler.getInstance().updateTimeInvestment(shift);
                    lShiftChanged.setVisible(true);
                    Frontpage.getLastCreatedFrontpage().updateSchedule();

                } catch (DatabaseException ex) {
                    Logger.getLogger(ShiftChangePopup.class.getName()).log(Level.SEVERE, null, ex);
                }
                super.getStage().close();
            }

        });

        super.getBottomHBox().getChildren().add(0, changeShift);

        super.addToCenter(vBox);

        super.display(title);

    }
}
