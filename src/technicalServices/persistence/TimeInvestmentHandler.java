/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package technicalServices.persistence;

import dbc.DatabaseConnection;
import exceptions.DatabaseException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.Room;
import model.TimeInvestment;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;

/* @author Benjamin */
public class TimeInvestmentHandler {

    private static TimeInvestmentHandler instance;

    private TimeInvestmentHandler() {

    }

    public static TimeInvestmentHandler getInstance() {
        if (instance == null) {
            instance = new TimeInvestmentHandler();
        }
        return instance;
    }

    public void addTimeInvestments(ArrayList<TimeInvestment> shifts) throws
            DatabaseException {
        try {
            Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String sql = "insert into timeInvestment(employeeNr,roomName,"
                    + "hours,minutes,startTime) values";
            for (int i = 0; i < shifts.size(); i++) {
                TimeInvestment shift = shifts.get(i);
            //Skal være null hvis referencen til room er null.
                //Det nedenstående undgår nullpointer.
                String roomName = null;
                if (shift.getRoom() != null) {
                    roomName = "'" + shift.getRoom().getRoomName() + "'";
                }
                sql += "(" + shift.getEmployee().getId() + "," + roomName
                        + "," + shift.getHours().getHours() + "," + shift.getMinutes().getMinutes()
                        + ",'" + shift.getStartTime().toString() + "')";
                if (i == shifts.size() - 1) {
                    sql += ";";
                } else {
                    sql += ",";
                }
            }

            stmt.execute(sql);

            stmt.close();
        } catch (SQLException ex) {
            throw new DatabaseException("Der kunne ikke tilføjes vagter til databasen.");
        }
    }

    public ArrayList<TimeInvestment> getUnassignedTimeInvestments() throws DatabaseException {

        ArrayList<TimeInvestment> timeInvestments = new ArrayList<>();
        try {
            Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            //Hent eller timeInvestment-objekter hvor rum-linket er null.
            String sql = "select * from timeInvestment where roomName is null;";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                Employee emp = EmployeeHandler.getInstance().getEmployee(rs.getInt("employeeNr"));
                Hours hours = Hours.hours(rs.getInt("hours"));
                Minutes minutes = Minutes.minutes(rs.getInt("minutes"));
                LocalDateTime startTime = LocalDateTime.parse(rs.getString("startTime"));

                timeInvestments.add(new TimeInvestment(id, hours, minutes, startTime, emp, null));
            }

            return timeInvestments;
        } catch (SQLException ex) {
            throw new DatabaseException("Der kunne ikke hentes utildelte vagter fra databasen.");
        }
    }

    public ArrayList<TimeInvestment> getAssignedTimeInvestments() throws DatabaseException {
        ArrayList<TimeInvestment> timeInvestments = new ArrayList<>();
        try {
            Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            //Hent eller timeInvestment-objekter hvor rum-linket er null.
            String sql = "select * from timeInvestment where roomName is not null;";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                Employee emp = EmployeeHandler.getInstance().getEmployee(rs.getInt("employeeNr"));
                Hours hours = Hours.hours(rs.getInt("hours"));
                Minutes minutes = Minutes.minutes(rs.getInt("minutes"));
               LocalDateTime startTime = LocalDateTime.parse(rs.getString("startTime"));
                Room room = RoomHandler.getInstance().getRoom(rs.getString("roomName"));
                timeInvestments.add(new TimeInvestment(id, hours, minutes, startTime, emp, room));
            }

            return timeInvestments;
        } catch (SQLException ex) {
            throw new DatabaseException("Der kunne ikke hentes tildelte vagter fra databasen.");
        }
    }

    /**
     *
     * @param timeInvestment Det timeinvestment som skal opdateres i forhold til
     * vagtskemaet.
     */
    public void updateTimeInvestment(TimeInvestment timeInvestment) throws DatabaseException {

        try {
            Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            int id = timeInvestment.getId();
            String roomName = timeInvestment.getRoom().getRoomName();
            int hours = timeInvestment.getHours().getHours();
            int minutes = timeInvestment.getMinutes().getMinutes();
            String starttime = timeInvestment.getStartTime().toString();

            String sql = "Update timeInvestment"
                    + " set roomName = '" + roomName + "', hours = " + hours + ", minutes = " + minutes + ",startTime = '" + starttime + "' where id = " + id;

            stmt.execute(sql);
            stmt.close();
        } catch (SQLException ex) {
            throw new DatabaseException("Vagterne i databasen kunne ikke updateres.");
        }

    }

}
