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
    public void addShift(Shift shift) throws SQLException, ClassNotFoundException {

        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "insert into shift() values (" + shift.getId() + ","
                + shift.getHours().getHours() + "," + shift.getMinutes().getMinutes()
                + ",'" + shift.getLocalDateTime().toString() + "'," 
                + shift.getEmployee().getId() + ");";

        stmt.execute(sql);

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
            Employee employee = EmployeeHandler.getInstance().getEmployee(rs.getInt("employeeNr"));

            shifts.add(new Shift(id, hours, minutes, localDateTime, employee));
        }

        rs.close();
        stmt.close();
        return shifts;
    }

    public void addShifts(ArrayList<Shift> shifts)
            throws SQLException, ClassNotFoundException {

        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "insert into shift() values";
        //String sql = "insert into room() values(" + roomNumber + ",'"
        //        + roomName + "'," + roomState + ");";

        for (int i = 0; i < shifts.size(); i++) {
            Shift tempShift = shifts.get(i);
            sql += "(" + tempShift.getId();
            sql += "," + tempShift.getHours().getHours();
            sql += "," + tempShift.getMinutes().getMinutes();
            sql += ",'" + tempShift.getLocalDateTime();
            sql += "'," + tempShift.getEmployee().getId();
            if (i == shifts.size() - 1) {
                sql += ");";
            } else {
                sql += "),\n";
            }
        }

        stmt.execute(sql);
        
        stmt.close();
    }

    public Shift getShift(int id) throws SQLException, ClassNotFoundException {

        Shift shift = null;

        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "select * from shift where id =" + id;

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Hours hours = Hours.hours(rs.getInt("hours"));
            Minutes minutes = Minutes.minutes(rs.getInt("minutes"));
            LocalDateTime localDateTime = LocalDateTime.parse(rs.getString("startTime"));
            Employee employee = EmployeeHandler.getInstance().getEmployee(rs.getInt("employeeNr"));

            shift = new Shift(id, hours, minutes, localDateTime, employee);
        }

        rs.close();
        stmt.close();
        return shift;
    }
}
