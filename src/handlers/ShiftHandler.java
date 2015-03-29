/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package handlers;

import dbc.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Room;
import model.Shift;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;

/**
 *
 * @author Jonas
 */
public class ShiftHandler {
    private static ShiftHandler instance;
    private ArrayList<Shift> shifts;
    
    private ShiftHandler () {
        shifts = new ArrayList<>();
    }
    
    public static ShiftHandler getInstance() {
        if (instance == null) {
            instance = new ShiftHandler();
        }
        return instance;
    }
    
    //parametrene er fra model klassen Shift uden Id
    public void addShifts (Hours hours, Minutes minutes, LocalDateTime localDateTime) 
        throws SQLException, ClassNotFoundException {
        
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        
        String sql = "insert into shift('hours', 'minutes', 'localDate') values";
        
        stmt.execute(sql);

        stmt.close();
    }
    
    public ArrayList<Shift> getShifts() throws SQLException {
        
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "select * from shift;";

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            Hours hours = Hours.parseHours("hours");
            Minutes minutes = Minutes.parseMinutes("minutes");
            LocalDateTime localDateTime = LocalDateTime.parse("localDateTime");

           // shifts.add(new Shift(id, hours, minutes, localDateTime, employee));
        }

        rs.close();
        stmt.close();
        return shifts;
    }
}
