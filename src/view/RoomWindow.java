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
import view.buttons.MenuButton;
import view.buttons.SettingsButton;

public class RoomWindow extends PopupWindow {

    private MenuButton addRoom;                                 //Selvlavet klasse MenuButton
    private TextField tFRoomCount, tFRoomName, tFRoomState;     //JavaFXTextFields
    private int roomCountInsert, roomStateInsert;               //Integers
    private Label lRoomCount, lRoomName, lRoomState, lAddRooms; //JavaFXLabels
    private String roomNameInsert;                              //String
    private SettingsButton settingsButton;                      //Selvlavet klasse SettingsButton
    private AddButton addButton;                                //Selvlavet klasse AddButton

    @Override
    public void display(String title) {
        //Selvlavet klasse til exception/problem håndtering
        ExceptionPopup exceptionPopup = new ExceptionPopup(); //Til exceptionhandling.

        tFRoomCount = new TextField();
        tFRoomName = new TextField();
        tFRoomState = new TextField();

        lRoomCount = new Label("Skriv rum nr. her");
        lRoomName = new Label("Skriv rum navn her");
        lRoomState = new Label("Skriv rum status her, brug tal\n 1 = åbent, 2 = lukket, 3 = under service");
        lAddRooms = new Label("Tilføj rum her");

//        lRoomState.setTextAlignment(TextAlignment.CENTER);

        ListView<Room> roomView = new ListView();
        //Her bliver der lavet en ObservableList og det er sådan set det samme 
        //som en ArrayList<> men det er fordi at vores ListView's generic type 
        //skal passe overegens med vores ObservableList<>
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        //Den her metode er en del af superklassen PopupWindow.
        setBottomHBoxPadding(15, 15, 15, 15);

        settingsButton = new SettingsButton();

        addButton = new AddButton();

        VBox vBoxLeft = new VBox(20);
        VBox vBoxRight = new VBox(20);
        VBox vBoxCenter = new VBox(20);

        vBoxLeft.setAlignment(Pos.TOP_LEFT);
        vBoxRight.setAlignment(Pos.TOP_LEFT);
        vBoxCenter.setAlignment(Pos.CENTER);
        //Vi giver dem padding så de ikke sidder lige op af hinanden
        vBoxLeft.setPadding(new Insets(15, 15, 15, 0));
        vBoxRight.setPadding(new Insets(15, 0, 15, 15));
        vBoxCenter.setPadding(new Insets(15, 15, 15, 15));
        //Vi tilføjer dem hver i sær til forskellige metoder af superklassen
        //De bliver tilføjet til en BorderPane i superklassen
        super.addToLeft(vBoxLeft);
        super.addToRight(vBoxRight);
        super.addToCenter(vBoxCenter);

        vBoxLeft.getChildren().addAll(lRoomCount, tFRoomCount,
                lRoomName, tFRoomName,
                lRoomState, tFRoomState);

        vBoxRight.getChildren().addAll(lAddRooms, roomView);
        
        vBoxCenter.getChildren().addAll(addButton, settingsButton);
        
        roomView.setPrefHeight(200);
        
        //Her bliver selve koden der sætter objekter af klassen Room ind i 
        //vores ObservableList<> afviklet. Der er exceptionhandling mm.
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
                    //Den her exception kan komme hvis der ikke står nogen i det
                    //textfield som der bliver kaldt Integer.parseInt på, eller
                    //hvis der står bogstaver
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
        addRoom = new MenuButton("Tilføj rum"); // Her skal listen køres igennem og der indsættes data i databasen
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
