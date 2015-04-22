/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import control.Xray;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Room;
/**
 *
 * @author Jonas
 */
public class Schedule extends ListView {
    
    private ScheduleListItem vBoxMon, vBoxTues, vBoxWed, vBoxThurs, vBoxFri, vBoxSat, vBoxSun;
    private HBox vBoxContainer = new HBox();
    private ArrayList<Room> rooms = new ArrayList<>();
    private ObservableList<ScheduleListItem> scheduleListItems = FXCollections.observableArrayList();
    private ListView<ScheduleListItem> Schedule = new ListView();
    
    
    
    public Schedule() {
        
        
    }
    
    public void display (String title) throws SQLException, ClassNotFoundException {
        
        vBoxContainer.getChildren().addAll(vBoxMon, vBoxTues, vBoxWed, vBoxThurs, vBoxFri, vBoxSat, vBoxSun);
        
        rooms = Xray.getInstance().getRoomControl().getRooms();
        
        
        for (int i = 0; i < rooms.size(); i++) {
            //I denne for løkke skal der tilføjes et rum til hver dag.
            //Debater om der skal laves en extension af VBox som svare til et rum
            //Er det overhovedet vbox der skal bruges? (Det her er de kasser der er i skemaet)
            scheduleListItems.add(new ScheduleListItem(rooms.get(i)));
        }
        
        //der skal også være en løkke til at indsætte rum verticalt ligesom dage sidder horizontalt.
        
        this.setVisible(true);
    }
}
