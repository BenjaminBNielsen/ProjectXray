/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package technicalServices.persistence;

import dbc.DatabaseConnection;
import exceptions.DatabaseException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TimePeriod;

/* @author Benjamin */
public class TimePeriodHandler {

    private static TimePeriodHandler instance;

    public static TimePeriodHandler getInstance() {
        if (instance == null) {
            instance = new TimePeriodHandler();
        }
        return instance;
    }

    public void addTimePeriods(ArrayList<TimePeriod> timePeriods) throws DatabaseException {
        boolean hasFailed = false;
        Statement stmt = null;
        try {
            stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        } catch (SQLException ex) {
            hasFailed = true;
            throw new DatabaseException("Der var ikke forbindelse til databasen, reglen er ikke tilføjet.");
        }

        String sql = "insert into timePeriod(startTime, endTime, min, max, employeeNr,roomName) values ('";
        for (int i = 0; i < timePeriods.size(); i++) {
            TimePeriod tp = timePeriods.get(i);
            sql += tp.getStartTime() + "','" + tp.getEndTime() + "'," + tp.getMin() + "," + tp.getMax()
                    + "," + tp.getEmployee().getId() + ",'" + tp.getRoom().getRoomName() + "')";
            if (i < timePeriods.size() - 1) {
                sql += ",('";
            } else {
                sql += ";";
            }
        }
System.out.println(sql);
        if (!hasFailed) {
            try {
                stmt.execute(sql);

                stmt.close();
            } catch (SQLException ex) {
                String str = "Grundet en fejl i databasen, er reglen ikke tilføjet."
                        + " Databasen gav følgende fejlmeddelse: " + ex.getMessage();
                throw new DatabaseException(str);
            }
        }
    }

}
