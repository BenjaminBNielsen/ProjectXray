/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import handlers.ShiftHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Shift;

/* @author Benjamin */

public class ShiftControl {

    public ArrayList<Shift> getShifts() throws SQLException, ClassNotFoundException{
        return ShiftHandler.getInstance().getShifts();
    }

    void addShifts(ArrayList<Shift> shifts) throws SQLException, ClassNotFoundException {
        ShiftHandler.getInstance().addShifts(shifts);
    }
    
    
}
