/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


public class RoomWindow extends PopupWindow {

    private MenuButton addRoom;

    @Override
    public void display(String title) {

        addRoom = new MenuButton("Tilføj rum");
        addRoom.setOnAction(e -> {

        });

        super.addToBottomHBox(addRoom);
        super.display(title);
    }
}
