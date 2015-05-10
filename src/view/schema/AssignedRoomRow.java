/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import control.Xray;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import model.Room;
import model.TimeInvestment;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import view.Frontpage;

/**
 *
 * @author Jonas
 */
public class AssignedRoomRow extends HBox implements HasChildren {

    private Room room;
    private LocalDateTime startTime;
    private ArrayList<TimeInvestment> shifts;
    private LabelTile roomLabel;

    public AssignedRoomRow(Room room, ArrayList<TimeInvestment> shifts, LocalDateTime startTime,
            int startHour, int startMinute, int periodLengthHour, int periodLengthMinute) {
        this.startTime = startTime;
        this.room = room;
        this.shifts = shifts;

        roomLabel = new LabelTile(room.getRoomName());

        //Tilføj rumnavn til venstre.
        this.getChildren().add(roomLabel);

        //Tilføj vagter til dagene på ugen. i0 = mandag, i6 = søndag.
        for (int i = 0; i < 5; i++) {
            LocalDateTime date = startTime.plusDays(i);
            date = date.withField(DateTimeFieldType.hourOfDay(), startHour);
            date = date.withField(DateTimeFieldType.minuteOfHour(), startMinute);
                        //Nulstil sekunder og millisekunder (vigtigt).
            date = date.withSecondOfMinute(0);
            date = date.withMillisOfSecond(0);

            LocalDateTime endDate = new LocalDateTime(date);
            endDate = endDate.plusHours(periodLengthHour).plusMinutes(periodLengthMinute);

            ShiftTile shiftTile = new ShiftTile(Xray.getInstance().
                    getShiftsInPeriod(shifts, date, endDate));

            this.getChildren().add(shiftTile);
        }

    }

    @Override
    public Node[] getChildrenList() {
        ObservableList<Node> sliChildren = this.getChildren();

        Node[] childrenAsArray = new Node[sliChildren.size()];

        for (int i = 0; i < sliChildren.size(); i++) {
            childrenAsArray[i] = sliChildren.get(i);
        }
        return childrenAsArray;
    }

}
