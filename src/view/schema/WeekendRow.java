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
public class WeekendRow implements HasChildren {

    private ArrayList<Node> children = new ArrayList<>();

    public WeekendRow(String dayName, LocalDateTime date, ArrayList<TimeInvestment> shifts) {

        shifts = Xray.getInstance().getTimeInvestmentControl().
                getShiftsInPeriod(shifts, date, date.plusHours(23).plusMinutes(59));

        LabelTile labelTile = new LabelTile(dayName);
        ShiftTile shiftTile = new ShiftTile(shifts);

        children.add(labelTile);
        children.add(shiftTile);
    }

    @Override
    public Node[] getChildrenList(){

            Node[] childrenAsArray = new Node[children.size()];

            for (int i = 0; i < children.size(); i++) {
                childrenAsArray[i] = children.get(i);
            }
        return childrenAsArray;
    }
}
