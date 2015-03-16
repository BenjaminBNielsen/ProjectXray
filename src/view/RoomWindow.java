/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.RoomControl;
import javafx.scene.control.*;


public class RoomWindow extends PopupWindow {

    private MenuButton addRoom;
    private TextField textFieldRoomCount;
    private TextField textFieldRoomName;
    private TextField TextFieldRoomState;

    @Override
    public void display(String title) {
        
        textFieldRoomCount = new TextField();
//            textFieldRoomCount.setText();

        addRoom = new MenuButton("Tilføj rum");
        addRoom.setOnAction(e -> {

        });

        super.addToBottomHBox(addRoom);
        super.display(title);
    }
}
