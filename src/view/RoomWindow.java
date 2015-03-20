/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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

public class RoomWindow extends PopupWindow {

    private PopupMenuButton addRoom;
    private TextField tFRoomCount, tFRoomName, tFRoomState;
    private int /*roomCount,*/ roomCountInsert, roomStateInsert;
    private Label lRoomCount, lRoomName, lRoomState;
    private String roomNameInsert;
    private SettingsButton settingsButton;
    private AddButton addButton;

    @Override
    public void display(String title) {
        ExceptionPopup exceptionPopup = new ExceptionPopup(); //Til exceptionhandling.
        //De udkommenterede ting der har relevans til nedenstående metode er til at få programmet
        //til at skrive et autogeneret tal ind i en textfield baseret på antallet af rum
        //i databasen.
//        try {
//            roomCount = Xray.getInstance().getRoomControl().getRoomCount();
//        } catch (SQLException ex) {
//            //Skriv code til exception handling
//        } catch (ClassNotFoundException ex) {
//            //Skriv code til exception handling
//        }
        tFRoomCount = new TextField();
        //textFieldRoomCount.setText("" + roomCount);

        tFRoomName = new TextField();

        tFRoomState = new TextField();

        lRoomCount = new Label("Skriv rum nr. her");

        lRoomName = new Label("Skriv rum navn her");

        lRoomState = new Label("Skriv rum status her, brug tal\n 1 = åbent, 2 = lukket, 3 = under service");
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
        vBoxLeft.getChildren().addAll(lRoomCount, tFRoomCount,
                lRoomName, tFRoomName,
                lRoomState, tFRoomState);

        vBoxRight.getChildren().addAll(roomView);
        vBoxCenter.getChildren().addAll(addButton, settingsButton);

        addButton.setOnAction(e -> {
            int count = 0;
            int count2 = 0;
            try {
                roomCountInsert = Integer.parseInt(tFRoomCount.getText());
            } catch (NumberFormatException ex) {
                exceptionPopup.display("Der kan kun indtastes tal i 'rum nr'");
                count2 = 1;
            }
            roomNameInsert = tFRoomName.getText();
            if (count2 != 1) {
                try {
                    roomStateInsert = Integer.parseInt(tFRoomState.getText());

                } catch (NumberFormatException ex) {
                    exceptionPopup.display("der kan kun skrives 1,2 eller 3 i 'rum status'");
                    count = 1;
                }

                if (count != 1) {
                    if (roomStateInsert >= 1) {
                        if (roomStateInsert <= 3) {       
                            rooms.add(new Room(roomCountInsert, roomNameInsert, roomStateInsert));
                            roomView.setItems(rooms);
                        } else {
                            System.out.println("Fejl, roomStateInsert er højere end 3");
                            exceptionPopup.display("Det indtastede tal i 'rum status' er højere end 3");
                            tFRoomState.setText("");
                        }
                    } else {
                        System.out.println("Fejl, roomStateInsert er lavere end 1");
                        exceptionPopup.display("Det indtastede tal i 'rum status' er lavere end 1");
                        tFRoomState.setText("");
                    }
                }
            }
        });

//        settingsButton.setOnAction(e -> {
//            roomView.getSelectionModel().getSelectedItem();
//        });
        
        addRoom = new PopupMenuButton("Tilføj rum"); // Her skal listen køres igennem og der indsættes data i databasen
        addRoom.setOnAction(e -> {
            try {
                Xray.getInstance().getRoomControl().addRooms(rooms);
            } catch (SQLException ex) {
                exceptionPopup.display("mySQL gav følgende fejlbesked: " + ex.getMessage());
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                exceptionPopup.display("mySQL gav følgende fejlbesked: " + ex.getMessage());
            } catch (NullPointerException ex) {
                exceptionPopup.display("mySQL gav følgende fejlbesked: " + ex.getMessage());
            }
            System.out.println(roomCountInsert + "\n" + roomNameInsert + "\n" + roomStateInsert);
            rooms.removeAll(rooms);
        });

        super.addToBottomHBox(addRoom);
        super.display(title);
    }
}
