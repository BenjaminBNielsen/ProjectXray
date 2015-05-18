/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import javafx.scene.control.Button;
import model.Room;

/**
 *
 * @author Jonas
 */
public class RoomLabel extends Button {
    Room room;
    //DEN HER KLASSE SKAL KALDES GENNEM LABELED I FRONTPAGE SÅ DE BLIVER TIL KNAPPER I STEDET FOR LABELS
    public RoomLabel(String text, Room room) {
        this.room = room;
        this.setStyle("-fx-focus-color: transparent;"
                + "-fx-faint-focus-color: transparent;"
                + "-fx-background-color: transparent;"
                + "-fx-font-family: Arial;"
                + "-fx-font-size: 18px;");
        String roomName = room.getRoomName();
//        this.setOnAction(e -> {
            
            //Det her skal fixes!
//            RoomChangePopup scp = new RoomChangePopup();
//            rcp.display("Foretag ændring på rum", room);
//        });
    }
}
                
