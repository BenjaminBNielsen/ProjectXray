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
import javafx.scene.layout.TilePane;
import model.TimeInvestment;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Benjamin
 */
public class AssignedAllRoomsRow extends TilePane implements HasChildren {

    private ArrayList<TimeInvestment> shifts;
    private LabelTile shiftTypeLabel;
    private LocalDateTime startTime;

    public AssignedAllRoomsRow(String shiftName, LocalDateTime startTime,
            ArrayList<TimeInvestment> shifts,
            int startHour, int startMinute, int periodLengthHour, int periodLengthMinute) {

        this.shifts = shifts;
        this.startTime = startTime;
        shiftTypeLabel = new LabelTile(shiftName);

        this.setOrientation(Orientation.HORIZONTAL);

        this.getChildren().add(shiftTypeLabel);

        //Tilføj vagter til dagene på ugen. i0 = mandag, i6 = søndag.
        for (int i = 0; i < 5; i++) {
            LocalDateTime dayOfWeek = startTime.plusDays(i);
            ShiftTile shiftTile = new ShiftTile(Xray.getInstance().
                    getShiftsInPeriod(dayOfWeek, shifts, startHour, startMinute,
                            periodLengthHour, periodLengthMinute));
            this.getChildren().add(shiftTile);

        }
    }

     @Override
    public Node[] getChildrenList(){
        ObservableList<Node> sliChildren = this.getChildren();

            Node[] childrenAsArray = new Node[sliChildren.size()];

            for (int i = 0; i < sliChildren.size(); i++) {
                childrenAsArray[i] = sliChildren.get(i);
            }
        return childrenAsArray;
    }
}
