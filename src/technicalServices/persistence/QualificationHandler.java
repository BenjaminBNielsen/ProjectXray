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
import java.util.Arrays;
import java.util.Observable;
import javafx.collections.ObservableList;
import model.Employee;
import model.Qualification;
import model.Room;
import model.RoomQualification;
import model.LimitQualification;
import view.popups.ExceptionPopup;

/**
 *
 * @author Yousef
 */
public class QualificationHandler {

    private static QualificationHandler instance;

    private QualificationHandler() {
    }

    public ArrayList<RoomQualification> getRoomQualificationsFromRoom(Room room) throws DatabaseException {
        ArrayList<RoomQualification> roomQualifications = new ArrayList<>();
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String roomName = room.getRoomName();
            String SQL = "Select * from qualification where roomId = " + roomName;
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("id");
                Boolean training = rs.getBoolean("training");
                int employeeId = rs.getInt("employeeid");
                Employee employee = EmployeeHandler.getInstance().getEmployee(employeeId);

                //roomQualifications.add(new RoomQualification(id, training, employee, room));
            }

            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            throw new DatabaseException("Der kunne ikke hentes kvalifikationer fra et specifikt rum.");

        }

        return roomQualifications;
    }

    public ArrayList<RoomQualification> getRoomQualifications() throws DatabaseException {
        ArrayList<RoomQualification> roomQualifications = new ArrayList<>();
        try {
            Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String SQL = "select * from roomQualification, qualification where "
                    + "roomQualification.id = qualification.id;";
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("qualification.id");
                String type = rs.getString("type");

                Statement stmtEmployees = DatabaseConnection.getInstance().getConnection().createStatement();
                String sql = "select * from qualToEmp where qualId = " + id;
                ArrayList<Employee> qualEmployees = new ArrayList<>();

                ResultSet rsEmployees = stmtEmployees.executeQuery(sql);

                while (rsEmployees.next()) {
                    int empId = rsEmployees.getInt("employeeNr");
                    qualEmployees.add(EmployeeHandler.getInstance().getEmployee(empId));
                }

                stmtEmployees.close();
                rsEmployees.close();

                Statement stmtRooms = DatabaseConnection.getInstance().getConnection().createStatement();
                sql = "select * from qualToRoom where qualId = " + id;
                ArrayList<Room> qualRooms = new ArrayList<>();

                ResultSet rsRooms = stmtRooms.executeQuery(sql);

                while (rsRooms.next()) {
                    String roomName = rsRooms.getString("roomName");
                    qualRooms.add(RoomHandler.getInstance().getRoom(roomName));
                }

                stmtRooms.close();
                rsRooms.close();

                roomQualifications.add(new RoomQualification(id, type, qualEmployees, qualRooms));
            }

            stmt.close();
            rs.close();

            return roomQualifications;
        } catch (SQLException ex) {
            throw new DatabaseException("Der kunne ikke hentes nogle rum kvalifikationer.");
        }
    }

    public ArrayList<LimitQualification> getLimitQualifications() throws DatabaseException {
        ArrayList<LimitQualification> limitQualifications = new ArrayList<>();
        try {
            Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String SQL = "select * from limitQualification, qualification where "
                    + "limitQualification.id = qualification.id;";
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("qualification.id");
                String type = rs.getString("type");
                int limit = rs.getInt("limit");

                Statement stmtEmployees = DatabaseConnection.getInstance().getConnection().createStatement();
                String sql = "select * from qualToEmp where qualId = " + id;
                ArrayList<Employee> qualEmployees = new ArrayList<>();

                ResultSet rsEmployees = stmtEmployees.executeQuery(sql);

                while (rsEmployees.next()) {
                    int empId = rsEmployees.getInt("employeeNr");
                    qualEmployees.add(EmployeeHandler.getInstance().getEmployee(empId));
                }

                stmtEmployees.close();
                rsEmployees.close();

                Statement stmtRooms = DatabaseConnection.getInstance().getConnection().createStatement();
                sql = "select * from qualToRoom where qualId = " + id;
                ArrayList<Room> qualRooms = new ArrayList<>();

                ResultSet rsRooms = stmtRooms.executeQuery(sql);

                while (rsRooms.next()) {
                    String roomName = rsRooms.getString("roomName");
                    qualRooms.add(RoomHandler.getInstance().getRoom(roomName));
                }

                stmtRooms.close();
                rsRooms.close();

                limitQualifications.add(new LimitQualification(id, type, qualEmployees, qualRooms, limit));
            }

            stmt.close();
            rs.close();

            return limitQualifications;
        } catch (SQLException ex) {
            throw new DatabaseException("Der kunne ikke hentes nogle universelle kvalifikationer fra databasen");
        }
        
    }

//    public ArrayList<Qualification> getQualificationsForSeveralEmployees(ArrayList<Employee> employees) throws ClassNotFoundException {
//        ArrayList<Qualification> employeesQualifications = new ArrayList<>();
//        for (int i = 0; i < employees.size(); i++) {
//            Employee selectedEmployees = employees.get(i);
//            employeesQualifications.add(getEmployeeQualifications(selectedEmployees).get(i));
//        }
//        return employeesQualifications;
//    }
//    public void setEmployeeQualifications(ArrayList<Qualification> selectedQualifications) {
//        for (int i = 0; i < selectedQualifications.size(); i++) {
//            Qualification selectedQualification = selectedQualifications.get(i);
//            Boolean training = selectedQualifications.get(i).isTraining();
//            Employee selectedEmployee = selectedQualifications.get(i).getEmployee();
//
//            setEmployeeQualification(selectedQualification, training, selectedEmployee);
//        }
//    }
//    public void setEmployeeQualification(Qualification qualification, Boolean training, Employee employee) {
//        try {
//            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
//
//            QualificationType type = qualification.getId();
//            int id = employee.getId();
//
//            String SQL = "insert into qualification(type, training, employeeid) values (";
//            SQL += type + "," + training + "," + id + ")";
//
//            stmt.execute(SQL);
//            stmt.close();
//        } catch (SQLException ex) {
//            System.out.println("SQL FEJL: " + ex.getMessage());
//        }
//
//    }
//    public void createQualification(QualificationType type, Boolean training, Room room) {
//        try {
//            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
//
//            String SQL = "insert into qualification(qualiTypeId, training, roomId) values (";
//            SQL += type + "," + training + "," + room +")";
//
//            stmt.execute(SQL);
//            stmt.close();
//        } catch (SQLException ex) {
//            System.out.println("SQL FEJL: " + ex.getMessage());
//        }
//
//    }
    public static QualificationHandler getInstance() {
        if (instance == null) {
            instance = new QualificationHandler();
        }
        return instance;
    }

    public void addRoomQualification(ObservableList<Room> observableRooms, ObservableList<Employee> observableEmployees, String type)
            throws DatabaseException {

        int qualId = 0;
        boolean hasFailed = false;
        Statement stmt = null;
        Statement stmt1 = null;
        Statement stmt2 = null;
        Statement stmt3 = null;
        try {
            stmt = DatabaseConnection.getInstance().getConnection().createStatement();
            String sql = "insert into qualification(type) values";

            sql += "('" + type + "');";

            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stmt.getGeneratedKeys();

            while (rs.next()) {
                qualId = rs.getInt(1);
            }
            System.out.println(qualId);
        } catch (SQLException ex) {
            hasFailed = true;
            throw new DatabaseException("Der må ikke være flere kvalifikationer med samme navn.");

        }
        try {
            stmt1 = DatabaseConnection.getInstance().getConnection().createStatement();
            String sql1 = "insert into qualToRoom(roomName, qualId) values";

            for (int i = 0; i < observableRooms.size(); i++) {
                Room tempRoom = observableRooms.get(i);
                sql1 += "('" + tempRoom.getRoomName();
                sql1 += "'," + qualId;
                if (i == observableRooms.size() - 1) {
                    sql1 += ");";
                } else {
                    sql1 += "),\n";
                }
            }
            stmt1.execute(sql1);

        } catch (SQLException ex) {
            hasFailed = true;
            throw new DatabaseException("Der må ikke være flere af samme rum.");
        }
        try {
            stmt2 = DatabaseConnection.getInstance().getConnection().createStatement();
            String sql2 = "insert into qualToEmp(employeeNr, qualId) values";

            for (int i = 0; i < observableEmployees.size(); i++) {
                Employee tempEmployee = observableEmployees.get(i);
                sql2 += "(" + tempEmployee.getId();
                sql2 += ", " + qualId;
                if (i == observableEmployees.size() - 1) {
                    sql2 += ");";
                } else {
                    sql2 += "),\n";
                }

            }
            stmt2.execute(sql2);

        } catch (SQLException ex) {
            hasFailed = true;
            throw new DatabaseException("Der må ikke være flere af samme medarbejder.");
        }
        try {
            stmt3 = DatabaseConnection.getInstance().getConnection().createStatement();
            String sql3 = "insert into roomQualification(id) values";

            sql3 += "(" + qualId + ");";

            stmt3.execute(sql3);

        } catch (SQLException ex) {
            hasFailed = true;
            throw new DatabaseException("");
        }

        try {
            if (hasFailed == true) {
                stmt.cancel();
                stmt1.cancel();
                stmt2.cancel();
                stmt3.cancel();
            }
            stmt3.close();
            stmt2.close();
            stmt1.close();
            stmt.close();
        } catch (SQLException ex) {
            throw new DatabaseException("");
        }

    }

    public void addLimitQualification(ObservableList<Room> observableRooms, ObservableList<Employee> observableEmployees, String type, int limit) 
        throws DatabaseException {

        int qualId = 0;
        boolean hasFailed = false;
        Statement stmt = null;
        Statement stmt1 = null;
        Statement stmt2 = null;
        Statement stmt3 = null;
        try {
            stmt = DatabaseConnection.getInstance().getConnection().createStatement();
            String sql = "insert into qualification(type) values";

            sql += "('" + type + "');";

            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stmt.getGeneratedKeys();

            while (rs.next()) {
                qualId = rs.getInt(1);
            }
            System.out.println(qualId);
        } catch (SQLException ex) {
            hasFailed = true;
            throw new DatabaseException("Der må ikke være flere kvalifikationer med samme navn. sqllimit");

        }
        try {
            stmt1 = DatabaseConnection.getInstance().getConnection().createStatement();
            String sql1 = "insert into qualToRoom(roomName, qualId) values";

            for (int i = 0; i < observableRooms.size(); i++) {
                Room tempRoom = observableRooms.get(i);
                sql1 += "('" + tempRoom.getRoomName();
                sql1 += "'," + qualId;
                if (i == observableRooms.size() - 1) {
                    sql1 += ");";
                } else {
                    sql1 += "),\n";
                }
            }
            stmt1.execute(sql1);

        } catch (SQLException ex) {
            hasFailed = true;
            throw new DatabaseException("Der må ikke være flere af samme rum. sql 2limit");
        }
        try {
            stmt2 = DatabaseConnection.getInstance().getConnection().createStatement();
            String sql2 = "insert into qualToEmp(employeeNr, qualId) values";

            for (int i = 0; i < observableEmployees.size(); i++) {
                Employee tempEmployee = observableEmployees.get(i);
                sql2 += "(" + tempEmployee.getId();
                sql2 += ", " + qualId;
                if (i == observableEmployees.size() - 1) {
                    sql2 += ");";
                } else {
                    sql2 += "),\n";
                }

            }
            stmt2.execute(sql2);

        } catch (SQLException ex) {
            hasFailed = true;
            throw new DatabaseException("Der må ikke være flere af samme medarbejder.");
        }
        try {
            stmt3 = DatabaseConnection.getInstance().getConnection().createStatement();
            String sql3 = "insert into limitQualification(id, `limit`) values";

            sql3 += "(" + qualId + ", " + limit +");";

            stmt3.execute(sql3);

        } catch (SQLException ex) {
            hasFailed = true;
            throw new DatabaseException("sql3limitqual");
        }

        try {
            if (hasFailed == true) {
                stmt.cancel();
                stmt1.cancel();
                stmt2.cancel();
                stmt3.cancel();
            }
            stmt3.close();
            stmt2.close();
            stmt1.close();
            stmt.close();
        } catch (SQLException ex) {
            throw new DatabaseException("Hersf");
        }
    }

}
