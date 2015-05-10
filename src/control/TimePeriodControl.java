/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import exceptions.DatabaseException;
import java.util.ArrayList;
import model.Employee;
import model.Room;
import model.TimeInvestment;
import model.TimePeriod;
import org.joda.time.LocalDateTime;
import technicalServices.persistence.EmployeeHandler;
import technicalServices.persistence.RoomHandler;

/* @author Benjamin */
public class TimePeriodControl {

    /**
     * Henter alle tidsperioder ind, og sorterer dem fra der endten ligger uden
     * for currentShift's periode eller ikke refererer til samme employee som
     * currentShift.
     *
     * @param currentShift
     * @return Tidsperioder i currentShift's tidsperiode for currentShift's
     * medarbejder.
     */
    public ArrayList<TimePeriod> getTimeperiods(TimeInvestment currentShift) {
        ArrayList<TimePeriod> timePeriods = new ArrayList<>();
        try{
        Employee mie = EmployeeHandler.getInstance().getEmployee(31);
        Room mr2 = RoomHandler.getInstance().getRoom("MR 2");
            LocalDateTime start = new LocalDateTime(2015, 4, 12, 0, 0);
            LocalDateTime end = new LocalDateTime(2015, 4, 18, 0, 0);
        
        timePeriods.add(new TimePeriod(start, end, 1, 10, mr2, mie));
        timePeriods.add(new TimePeriod(start, end, 1, 10, mr2, mie));
        }catch(DatabaseException ex){}
        
        for (int i = 0; i < timePeriods.size(); i++){
            boolean shiftInPeriod = Xray.getInstance().isDateInPeriod(currentShift.getStartTime(),
                    timePeriods.get(i).getStartTime(), timePeriods.get(i).getEndTime());
            if (!(shiftInPeriod) || timePeriods.get(i).getEmployee().getId() != currentShift.getEmployee().getId()) {
                timePeriods.remove(timePeriods.get(i));
                i--;
            }
        }

        return timePeriods;
    }

    public boolean hasPeriodConstraint(ArrayList<TimePeriod> timePeriods, Room room) {
        boolean hasPeriodConstraint = false;
        for (TimePeriod timePeriod : timePeriods) {
            if (timePeriod.getRoom().getRoomName().equals(room.getRoomName())) {
                hasPeriodConstraint = true;
                break;//OK i søgning.
            }
        }
        return hasPeriodConstraint;
    }

    public ArrayList<TimePeriod> getTimePeriodsForEmp(ArrayList<TimePeriod> timePeriods,
            TimeInvestment currentShift) {

        ArrayList<TimePeriod> timePeriodsForEmp = new ArrayList<>();

        for (TimePeriod timePeriod : timePeriods) {
            boolean shiftInPeriod = Xray.getInstance().isDateInPeriod(currentShift.getStartTime(),
                    timePeriod.getStartTime(), timePeriod.getEndTime());
            if (shiftInPeriod || timePeriod.getEmployee().getId() == currentShift.getEmployee().getId()) {
                timePeriodsForEmp.add(timePeriod);
            }
        }

        return timePeriodsForEmp;
    }

}
