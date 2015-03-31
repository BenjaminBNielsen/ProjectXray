/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import handlers.ShiftHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Employee;
import model.Shift;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;

/* @author Benjamin */

public class ShiftControl {

    public ArrayList<Shift> getShifts() throws SQLException, ClassNotFoundException{
        return ShiftHandler.getInstance().getShifts();
    }

    public void addShift(Hours hours, Minutes minutes, LocalDateTime localDateTime, Employee employee) 
            throws SQLException, ClassNotFoundException {
        ShiftHandler.getInstance().addShift(hours, minutes, localDateTime, employee);
    }

    void addShifts(ArrayList<Shift> shifts) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
