/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import control.Xray;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.TimeInvestment;
import org.joda.time.LocalDateTime;
import view.Frontpage;

/**
 *
 * @author Jonas
 */
public class WeekendRow extends HBox implements HasChildren {

    private String dayName;
    private ArrayList<TimeInvestment> shifts;
    private RoomTile labelTile;
    private ShiftTile shiftTile;
    private LocalDateTime date;

    public WeekendRow(String dayName, LocalDateTime date, ArrayList<TimeInvestment> shifts) {
        this.dayName = dayName;
        this.date = date;
        this.shifts = shifts;

        shifts = Xray.getInstance().getShiftsInPeriod(shifts, date, date.plusHours(23)
                .plusMinutes(59));

        labelTile = new RoomTile(dayName);
        shiftTile = new ShiftTile(shifts);

        this.getChildren().addAll(labelTile, shiftTile);

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
