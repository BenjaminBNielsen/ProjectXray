/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import java.util.ArrayList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.*;
import javafx.scene.paint.Paint;
import model.Employee;
import model.TimeInvestment;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Benjamin & Jonas
 */
public class ShiftTile extends BorderPane {

    ArrayList<TimeInvestment> shifts;
    VBox bottomVbox = new VBox(5);

    public ShiftTile(ArrayList<TimeInvestment> shifts) {
        this.shifts = shifts;
        bottomVbox.setAlignment(Pos.TOP_CENTER);
        this.setCenter(bottomVbox);
        this.setStyle("-fx-border-color: #d3d3d3;"
                + "-fx-border-width: 0.5px;");        
        initLabels();
    }

    private void initLabels() {
        for (int i = 0; i < shifts.size(); i++) {
            EmployeeLabel tempLabel = new EmployeeLabel(shifts.get(i));
            LocalDateTime tempShift = shifts.get(i).getStartTime();

            tempLabel.setTextFill(getColorOnShiftStart(tempShift));

            bottomVbox.getChildren().add(tempLabel);
        }
    }

    private Paint getColorOnShiftStart(LocalDateTime shiftTime) {
        //hours
        LocalDateTime tempShiftEveningStart
                = shiftTime.withField(DateTimeFieldType.hourOfDay(),
                        ShiftPeriodConstants.EVENING_SHIFT_HOURS_START.getHours());
        LocalDateTime tempShiftNightStart
                = shiftTime.withField(DateTimeFieldType.hourOfDay(),
                        ShiftPeriodConstants.NIGHT_SHIFT_HOURS_START.getHours());
        LocalDateTime tempShiftDayStart
                = shiftTime.withField(DateTimeFieldType.hourOfDay(),
                        ShiftPeriodConstants.DAY_SHIFT_HOURS_START.getHours());
        //Minutes.
        tempShiftEveningStart
                = tempShiftEveningStart.withField(DateTimeFieldType.minuteOfHour(),
                        ShiftPeriodConstants.EVENING_SHIFT_MINUTES_START.getMinutes());
        tempShiftNightStart
                = tempShiftNightStart.withField(DateTimeFieldType.minuteOfHour(),
                        ShiftPeriodConstants.NIGHT_SHIFT_MINUTES_START.getMinutes());
        tempShiftDayStart
                = tempShiftDayStart.withField(DateTimeFieldType.minuteOfHour(),
                        ShiftPeriodConstants.DAY_SHIFT_MINUTES_START.getMinutes());

        if (shiftTime.isEqual(tempShiftDayStart)
                || (shiftTime.isBefore(tempShiftEveningStart)
                && shiftTime.isAfter(tempShiftDayStart))) {
            return BLUE;
        } else if (shiftTime.isEqual(tempShiftEveningStart)
                || (shiftTime.isBefore(tempShiftNightStart)
                && shiftTime.isAfter(tempShiftEveningStart))) {
            return GREEN;
        } else {
            return RED;
        }
    }

    public ArrayList<TimeInvestment> getShifts() {
        return shifts;
    }

    public void setShifts(ArrayList<TimeInvestment> shifts) {
        this.shifts = shifts;
    }

    public VBox getBottomVbox() {
        return bottomVbox;
    }

    public void setBottomVbox(VBox bottomVbox) {
        this.bottomVbox = bottomVbox;
    }

}
