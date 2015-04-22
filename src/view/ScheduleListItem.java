/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Room;

/**
 *
 * @author Jonas
 */
public class ScheduleListItem extends HBox{
    private Room room;
    
    
    public ScheduleListItem(Room room) {
        //Integer 1-7 hvor 1 er mandag etc. til brug med JodaTime.
        this.room = room;
    }
}
