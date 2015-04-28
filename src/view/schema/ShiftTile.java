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
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.*;
import model.Employee;
import model.TimeInvestment;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Benjamin
 */
public class ShiftTile extends BorderPane{
    
    ArrayList<TimeInvestment> shifts;
    VBox bottomVbox = new VBox(5);
    
    public ShiftTile(ArrayList<TimeInvestment> shifts, double width){
        this.shifts = shifts;
        this.setPrefSize(width, 100);
        bottomVbox.setAlignment(Pos.TOP_CENTER);
        this.setCenter(bottomVbox);
                
        initLabels();
    }

    private void initLabels() {
        for (int i = 0; i < shifts.size(); i++) {
            Employee currentShiftEmployee = shifts.get(i).getEmployee();
            EmployeeLabel tempLabel = new EmployeeLabel(currentShiftEmployee);
            LocalDateTime tempShift = shifts.get(i).getStartTime();
            //Hours.
            LocalDateTime tempShiftEveningStart = tempShift.withField(DateTimeFieldType.hourOfDay(), ShiftPeriodConstants.EVENING_SHIFT_HOURS_START.getHours());
            LocalDateTime tempShiftNightStart = tempShift.withField(DateTimeFieldType.hourOfDay(), ShiftPeriodConstants.NIGHT_SHIFT_HOURS_START.getHours());
            LocalDateTime tempShiftDayStart = tempShift.withField(DateTimeFieldType.hourOfDay(), ShiftPeriodConstants.DAY_SHIFT_HOURS_START.getHours());
            //Minutes.
            tempShiftEveningStart = tempShiftEveningStart.withField(DateTimeFieldType.minuteOfHour(), ShiftPeriodConstants.EVENING_SHIFT_MINUTES_START.getMinutes());
            tempShiftNightStart = tempShiftEveningStart.withField(DateTimeFieldType.minuteOfHour(), ShiftPeriodConstants.NIGHT_SHIFT_MINUTES_START.getMinutes());
            tempShiftDayStart = tempShiftEveningStart.withField(DateTimeFieldType.minuteOfHour(), ShiftPeriodConstants.DAY_SHIFT_MINUTES_START.getMinutes());
            //Label farve.
            if (tempShift.isBefore(tempShiftEveningStart) && tempShift.isAfter(tempShiftDayStart) || tempShift.isEqual(tempShiftDayStart)) {
                tempLabel.setTextFill(BLUE);
            } else if (tempShift.isBefore(tempShiftNightStart) && tempShift.isAfter(tempShiftEveningStart) || tempShift.isEqual(tempShiftEveningStart)) {
                tempLabel.setTextFill(GREEN);
            } else {
                tempLabel.setTextFill(RED);
            }
            bottomVBox.getChildren().add(tempLabel);
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
