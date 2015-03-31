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
import model.Employee;
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

    private ShiftHandler() {
        shifts = new ArrayList<>();
    }

    public static ShiftHandler getInstance() {
        if (instance == null) {
            instance = new ShiftHandler();
        }
        return instance;
    }

    //parametrene er fra model klassen Shift uden Id
    public void addShift(Hours hours, Minutes minutes, LocalDateTime localDateTime, Employee employee)
            throws SQLException, ClassNotFoundException {

        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "insert into shift(hours,minutes,startTime,employeeNr) "
                + "values (" + hours.getHours() + "," + minutes.getMinutes() + 
                ",'" + localDateTime.toString() + "'," + employee.getId() + ");";

        stmt.execute(sql);
        System.out.println(sql);

        stmt.close();
    }

    public ArrayList<Shift> getShifts() throws SQLException, ClassNotFoundException {

        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "select * from shift;";

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            Hours hours = Hours.hours(rs.getInt("hours"));
            Minutes minutes = Minutes.minutes(rs.getInt("minutes"));
            LocalDateTime localDateTime = LocalDateTime.parse(rs.getString("startTime"));
            Employee employee = EmployeeHandler.getInstance().getEmployee(rs.getInt("employeeId"));

            shifts.add(new Shift(id, hours, minutes, localDateTime, employee));
        }

        rs.close();
        stmt.close();
        return shifts;
    }
}
