/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import view.schema.AssignedRoomRow;
import control.Xray;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import model.TimeInvestment;
import org.joda.time.LocalDateTime;
import view.popups.ExceptionPopup;

/**
 *
 * @author Jonas
 */
public class Schedule extends ListView {

    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<TimeInvestment> timeInvestments;
    private ObservableList<Node> scheduleListItems = FXCollections.observableArrayList();
    private LocalDateTime startTime;
    private ExceptionPopup exceptionPopup = new ExceptionPopup();
    private WeekendRow weekendRowSat, weekendRowSun;
    private WeekendTile weekendTileSat, weekendTileSun, weekendTileSatEmp,
            weekendTileSunEmp;

    public Schedule(ArrayList<TimeInvestment> timeInvestments, LocalDateTime startTime) {
        this.timeInvestments = timeInvestments;
        this.startTime = startTime;
        this.setEditable(false);
        this.setFocusTraversable(false);
        
        //Tilføj headeren med ugens dage fra mandag til fredag:
        scheduleListItems.add(new ScheduleHeader(startTime.getWeekOfWeekyear()));
        
        try {
            addShiftTiles();
        } catch (SQLException ex) {
            exceptionPopup.display("I forbindelse med indhentning af rum fra databasen"
                    + " er der sket en fejl, kontakt din systemadministrator. SQL gav"
                    + " følgende fejlbesked: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            exceptionPopup.display("Der er ikke oprettet forbindelse til databasen, "
                    + "fordi der mangler en driver.");
        }
        
        initWeekend();
        
        this.setItems(scheduleListItems);
        

    }

    public void addShiftTiles() throws SQLException, ClassNotFoundException {

        rooms = Xray.getInstance().getRoomControl().getRooms();
        
        for (int i = 0; i < rooms.size(); i++) {
            ArrayList<TimeInvestment> shiftsOnRoom = getShiftsOnRoom(rooms.get(i), timeInvestments);

            scheduleListItems.add(new AssignedRoomRow(rooms.get(i), shiftsOnRoom, startTime));
        }

        

    }

    private ArrayList<TimeInvestment> getShiftsOnRoom(Room room, ArrayList<TimeInvestment> timeInvestments) {
        ArrayList<TimeInvestment> shiftsOnRoom = new ArrayList<>();

        for (int i = 0; i < timeInvestments.size(); i++) {
            if (timeInvestments.get(i).getRoom() != null) {
                if (timeInvestments.get(i).getRoom().getRoomName().equals(room.getRoomName())) {
                    shiftsOnRoom.add(timeInvestments.get(i));
                }
            }
        }

        return shiftsOnRoom;
    }
    
    public void initWeekend() {
//        weekendTileSat = new WeekendTile("Lørdag", 100, 155);
//        weekendTileSun = new WeekendTile("Søndag", 100, 155);
//        weekendTileSatEmp = new WeekendTile();
//        weekendTileSunEmp = new WeekendTile();
//        weekendTilesSat = new ArrayList<>();
//        weekendTilesSun = new ArrayList<>();
//        weekendTilesSat.add(weekendTileSat);
//        weekendTilesSat.add(weekendTileSatEmp);
//        weekendTilesSun.add(weekendTileSun);
//        weekendTilesSun.add(weekendTileSunEmp);
//        weekendRowSat = new WeekendRow();
//        weekendRowSun = new WeekendRow();
//        weekendRowSat.addWeekendTiles(weekendTilesSat);
//        weekendRowSun.addWeekendTiles(weekendTilesSun);
//        scheduleListItems.addAll(weekendRowSat, weekendRowSun);
    } 
}
