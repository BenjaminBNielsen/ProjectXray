/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups;

import view.popups.PopupWindow;
import view.popups.ExceptionPopup;
import control.*;
import java.sql.*;
import java.util.logging.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import model.Room;
import view.buttons.AddButton;
import view.buttons.PopupMenuButton;
import view.buttons.SettingsButton;

public class RoomPopup extends PopupWindow {

    private PopupMenuButton addRoom;
    private TextField tFRoomName, tFRoomMinOccupation, tFRoomMaxOccupation;
    private int roomStateInsert, roomMinInsert, roomMaxInsert;
    private Label lRoomName, lRoomState, lRoomMinInsert, lRoomMaxInsert;
    private String roomNameInsert;
    private SettingsButton settingsButton;
    private AddButton addButton;
    private ComboBox cBRoomState;
    private boolean exceptionFejl;

    @Override
    public void display(String title) {
        ExceptionPopup exceptionPopup = new ExceptionPopup(); //Til exceptionhandling.

        tFRoomName = new TextField();
        tFRoomMinOccupation = new TextField();
        tFRoomMaxOccupation = new TextField();

        ObservableList<String> cBOptions
                = FXCollections.observableArrayList(
                        "Åbent",
                        "Lukket",
                        "Service"
                );

        cBRoomState = new ComboBox(cBOptions);
        cBRoomState.setValue("Åbent");

        lRoomName = new Label("Skriv rum navn her");

        lRoomState = new Label("Sæt rum status her");
        lRoomMinInsert = new Label("Skriv minimum antal personer der skal være i rummet her");
        lRoomMaxInsert = new Label("Skriv maximum antal personer der skal være i rummet her");

        lRoomState.setTextAlignment(TextAlignment.CENTER);
        ListView<Room> roomView = new ListView();                                   //List
        ObservableList<Room> rooms = FXCollections.observableArrayList();           //List
        setBottomHBoxPadding(15, 15, 15, 15);
        settingsButton = new SettingsButton();
        addButton = new AddButton();
        VBox vBoxLeft = new VBox(20);
        VBox vBoxRight = new VBox(20);
        VBox vBoxCenter = new VBox(20);
        vBoxLeft.setAlignment(Pos.CENTER);
        vBoxRight.setAlignment(Pos.CENTER);
        vBoxCenter.setAlignment(Pos.CENTER);
        vBoxLeft.setPadding(new Insets(15, 15, 15, 7.5));
        vBoxRight.setPadding(new Insets(15, 7.5, 15, 15));
        vBoxCenter.setPadding(new Insets(15, 7.5, 15, 7.5));
        super.addToLeft(vBoxLeft);
        super.addToRight(vBoxRight);
        super.addToCenter(vBoxCenter);
        vBoxLeft.getChildren().addAll(
                lRoomName, tFRoomName,
                lRoomState, cBRoomState,
                lRoomMinInsert, tFRoomMinOccupation,
                lRoomMaxInsert, tFRoomMaxOccupation);

        vBoxRight.getChildren().addAll(roomView);
        vBoxCenter.getChildren().addAll(addButton, settingsButton);

        addButton.setOnAction(e -> {
            exceptionFejl = false;

            roomNameInsert = tFRoomName.getText();
            try {
                roomMinInsert = Integer.parseInt(tFRoomMinOccupation.getText());
            } catch (NumberFormatException ex) {
                exceptionPopup.display("Der må kun være tal i tekstfeldtet til minimum antal ansatte");
                exceptionFejl = true;
            }

            try {
                roomMaxInsert = Integer.parseInt(tFRoomMaxOccupation.getText());
            } catch (NumberFormatException ex) {
                exceptionPopup.display("Der må kun være tal i tekstfeldtet til maximum antal ansatte");
                exceptionFejl = true;
            }

            roomStateInsert = cBRoomState.getSelectionModel().getSelectedIndex() + 1;

            if (exceptionFejl != true) {
                rooms.add(new Room(roomNameInsert, roomStateInsert, roomMinInsert, roomMaxInsert));
                roomView.setItems(rooms);
            }

        });

        settingsButton.setOnAction(e -> {
            int index = roomView.getSelectionModel().getSelectedIndex();
            if (!roomView.getSelectionModel().isEmpty()) {
                rooms.remove(index);
                roomView.setItems(rooms);
            }
        });

        addRoom = new PopupMenuButton("Tilføj rum"); // Her skal listen køres igennem og der indsættes data i databasen
        addRoom.setOnAction(e -> {
            if (!rooms.isEmpty()) {
                try {
                    Xray.getInstance().getRoomControl().addRooms(rooms);
                    rooms.removeAll(rooms);
                } catch (SQLException ex) {
                    exceptionPopup.display("Der findes allerede et rum med det navn");
                    System.out.println(ex.getMessage());
                } catch (ClassNotFoundException ex) {
                    exceptionPopup.display("Der mangler en database driver, kontakt systemadministrator");
                }
                System.out.println(roomNameInsert + "\n" + roomStateInsert);
            } else {
                exceptionPopup.display("Der er ingen rum i listen der skal indsættes");
            }
        });

        super.addToBottomHBox(addRoom);
        super.display(title);
    }
}
