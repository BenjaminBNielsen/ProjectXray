/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.*;
import java.sql.*;
import java.util.logging.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;


public class RoomWindow extends PopupWindow {

    private MenuButton addRoom;
    private TextField tFRoomCount, tFRoomName, tFRoomState;
    private int /*roomCount,*/ roomCountInsert, roomStateInsert;
    private Label lRoomCount, lRoomName, lRoomState;
    private String roomNameInsert;
    

    @Override
    public void display(String title) {
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
            tFRoomCount.setPrefWidth(482);
            
        tFRoomName = new TextField();
        
        tFRoomState = new TextField();
        
        lRoomCount = new Label("Skriv rum nr. her");
        
        lRoomName = new Label("Skriv rum navn her");
        
        lRoomState = new Label("Skriv rum status her (brug tal, 1 = åbent, 2 = lukket, 3 = under service)");
        
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        super.addToLeft(vBox);
        vBox.getChildren().addAll(lRoomCount, tFRoomCount, 
                lRoomName, tFRoomName, 
                lRoomState, tFRoomState);
            
        addRoom = new MenuButton("Tilføj rum");
        addRoom.setOnAction(e -> {
            roomCountInsert = Integer.parseInt(tFRoomCount.getText());
            roomNameInsert = tFRoomName.getText();
            roomStateInsert = Integer.parseInt(tFRoomState.getText());
            if (roomStateInsert >=1) {
                if (roomStateInsert <=3) {
                    try {
                        Xray.getInstance().getRoomControl().addRoom(roomCountInsert, roomNameInsert, roomStateInsert);
                    } catch (SQLException ex) {
                        //Exception handling
                    } catch (ClassNotFoundException ex) {
                        //Exception handling
                    }
                    System.out.println(roomCountInsert+ "\n" + roomNameInsert + "\n" + roomStateInsert);
                } else {
                    System.out.println("Fejl, roomStateInsert er hæjere end 1");
                }
            } else {
                System.out.println("Fejl, roomStateInsert er lavere end 1");
            }
        });

        super.addToBottomHBox(addRoom);
        super.display(title);
    }
}
