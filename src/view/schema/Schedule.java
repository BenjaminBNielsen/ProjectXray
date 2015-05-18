/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import view.schema.AssignedRoomRow;
import control.Xray;
import exceptions.DatabaseException;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
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
public class Schedule extends ScrollPane {

    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<TimeInvestment> timeInvestments;
    private ObservableList<HasChildren> scheduleListItems = FXCollections.observableArrayList();
    private LocalDateTime startTime;
    private ExceptionPopup exceptionPopup = new ExceptionPopup();
    private GridPane grid = new GridPane();
    private ArrayList<Node[]> gridLayoutlist = new ArrayList<>();

    public Schedule(ArrayList<TimeInvestment> timeInvestments, LocalDateTime startTime) {
        this.timeInvestments = timeInvestments;
        this.startTime = startTime;
        this.setFocusTraversable(false);

        //Tilføj headeren med ugens dage fra mandag til fredag:
        ScheduleHeader sh = new ScheduleHeader(startTime.getWeekOfWeekyear());
        scheduleListItems.add(sh);
        gridLayoutlist.add(sh.getChildrenList());

        try {
            addShiftTiles();
        } catch (DatabaseException ex) {
            exceptionPopup.display(ex.getMessage());
        }

        AssignedAllRoomsRow arAfternoon = new AssignedAllRoomsRow("Eftermiddag", startTime, timeInvestments, 15, 15, 5, 0);
        scheduleListItems.add(arAfternoon);
        gridLayoutlist.add(arAfternoon.getChildrenList());
        AssignedAllRoomsRow arNight = new AssignedAllRoomsRow("Nat", startTime, timeInvestments, 23, 15, 5, 0);
        scheduleListItems.add(arNight);
        gridLayoutlist.add(arNight.getChildrenList());

        WeekendRow wrSaturday = new WeekendRow("Lørdag", startTime.plusDays(5), timeInvestments);
        WeekendRow wrSunday = new WeekendRow("Søndag", startTime.plusDays(6), timeInvestments);
        scheduleListItems.add(wrSaturday);
        scheduleListItems.add(wrSunday);
        gridLayoutlist.add(wrSaturday.getChildrenList());
        gridLayoutlist.add(wrSunday.getChildrenList());

        this.setContent(grid);

        grid.setStyle("-fx-background-color: #FFF;");
        addGridConstraints();

        for (int i = 0; i < scheduleListItems.size(); i++) {

            if (i > (scheduleListItems.size() - 1) - 2) {
                GridPane.setColumnSpan(scheduleListItems.get(i).getChildrenList()[1], 5);
            }

            grid.addRow(i, scheduleListItems.get(i).getChildrenList());

        }

        VBox.setVgrow(this, Priority.ALWAYS);

        this.setFitToWidth(true);
    }

    //Den thrower en exception fordi metoden bliver kaldt i samme klasse oven over. Der bliver
    //exceptionen fanget.
    public void addShiftTiles() throws DatabaseException {

        rooms = Xray.getInstance().getRoomControl().getRooms();

        for (int i = 0; i < rooms.size(); i++) {
            ArrayList<TimeInvestment> shiftsOnRoom = getShiftsOnRoom(rooms.get(i), timeInvestments);
            AssignedRoomRow arr = new AssignedRoomRow(rooms.get(i), shiftsOnRoom, startTime, 7, 30, 5, 0);
            scheduleListItems.add(arr);

            gridLayoutlist.add(arr.getChildrenList());
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

    /**
     * Tilføj de nødvendige constratints på grid feltet. Sådan at den kolonne
     * længst mod venstre, er 160 px lang, og de sidste 5 kolonner udnytter
     * resten af pladsen ved brug af setHgrow
     */
    private void addGridConstraints() {
        ColumnConstraints tempCon = new ColumnConstraints(180, 180, Double.MAX_VALUE);
        grid.getColumnConstraints().add(tempCon);

        for (int i = 1; i < 6; i++) {
            tempCon = new ColumnConstraints(100, 100, Double.MAX_VALUE);
            tempCon.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(tempCon);

        }
    }

    public void updateSchedule(ArrayList<TimeInvestment> timeInvestements, LocalDateTime startTime) {
        this.timeInvestments = timeInvestments;
        this.startTime = startTime;
    }

    public GridPane getGrid() {
        return grid;
    }

    public void setGrid(GridPane grid) {
        this.grid = grid;
    }

    public ArrayList<Node[]> getGridLayoutlist() {
        return gridLayoutlist;
    }

    public void setGridLayoutlist(ArrayList<Node[]> gridLayoutlist) {
        this.gridLayoutlist = gridLayoutlist;
    }
    
}
