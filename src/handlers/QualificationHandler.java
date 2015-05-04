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
import java.util.Arrays;
import java.util.Observable;
import javafx.collections.ObservableList;
import model.Employee;
import model.Qualification;
import model.Room;
import model.RoomQualification;
import model.LimitQualification;

/**
 *
 * @author Yousef
 */
public class QualificationHandler {

    private static QualificationHandler instance;
    private ArrayList<RoomQualification> roomQualifications;
    private ArrayList<LimitQualification> limitQualifications;

    private QualificationHandler() {
    }

    public ArrayList<RoomQualification> getRoomQualificationsFromRoom(Room room) throws ClassNotFoundException {
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
            System.out.println("SQL Fejl: " + ex.getMessage());

        }

        return roomQualifications;
    }

    public ArrayList<RoomQualification> getRoomQualifications() {
        ArrayList<RoomQualification> roomQualifications = new ArrayList<>();
        try {
            Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String SQL = "select * from roomQualification, qualification where "
                    + "roomQualification.id = qualification.id;";
            System.out.println(SQL);
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("qualification.id");
                String type = rs.getString("type");
                Boolean training = rs.getBoolean("training");

                Statement stmtEmployees = DatabaseConnection.getInstance().getConnection().createStatement();
                String sql = "select * from qualToEmp where qualId = " + id;
                System.out.println(sql);
                ArrayList<Employee> qualEmployees = new ArrayList<>();

                ResultSet rsEmployees = stmtEmployees.executeQuery(sql);

                while (rsEmployees.next()) {
                    System.out.println(rsEmployees.getRow());
                    int empId = rsEmployees.getInt("employeeNr");
                    qualEmployees.add(EmployeeHandler.getInstance().getEmployee(empId));
                }
                System.out.println("----------");
                for (int i = 0; i < qualEmployees.size(); i++) {
                    System.out.println(qualEmployees.get(i));

                }
                System.out.println("----------");

                stmtEmployees.close();
                rsEmployees.close();

                Statement stmtRooms = DatabaseConnection.getInstance().getConnection().createStatement();
                sql = "select * from qualToRoom where qualId = " + id;
                System.out.println(sql);
                ArrayList<Room> qualRooms = new ArrayList<>();

                ResultSet rsRooms = stmtRooms.executeQuery(sql);

                while (rsRooms.next()) {
                    System.out.println(rsRooms.getRow());
                    String roomName = rsRooms.getString("roomName");
                    qualRooms.add(RoomHandler.getInstance().getRoom(roomName));
                }

                stmtRooms.close();
                rsRooms.close();

                roomQualifications.add(new RoomQualification(id, training, type, qualEmployees, qualRooms));
            }

            for (int i = 0; i < roomQualifications.size(); i++) {
                System.out.println(roomQualifications.get(i));
                System.out.println(Arrays.asList(roomQualifications.get(i).getEmployees().toArray()));
                System.out.println(Arrays.asList(roomQualifications.get(i).getRooms().toArray()));

            }

            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        } catch (ClassNotFoundException ex) {
            System.out.println("DET VIRKER IKKE DET LOOORT");
        } catch (Exception ex) {
            System.out.println("LORTET VIRKER STADIG IKKE");
        }

        return roomQualifications;
    }

    public ArrayList<LimitQualification> getSingleQualifications(Employee employee) throws ClassNotFoundException {
        ArrayList<LimitQualification> singleQualifications = new ArrayList<>();
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String SQL = "Select * from qualification where employeeid = " + employee.getId();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("id");
                Boolean training = rs.getBoolean("training");

                //singleQualifications.add(new LimitQualification(id, training, employee));
            }

            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        }

        return singleQualifications;
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
            throws SQLException, ClassNotFoundException {

        int qualId = 0;

        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        String sql = "insert into qualification(type) values";

        sql += "('" + type + "');";

        stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = stmt.getGeneratedKeys();

        while (rs.next()) {
            qualId = rs.getInt(1);
        }
        System.out.println(qualId);
        stmt.close();

        Statement stmt1 = DatabaseConnection.getInstance().getConnection().createStatement();
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
        stmt1.close();

        Statement stmt2 = DatabaseConnection.getInstance().getConnection().createStatement();
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
        stmt2.close();

        Statement stmt3 = DatabaseConnection.getInstance().getConnection().createStatement();
        String sql3 = "insert into roomQualification(id) values";

        sql3 += "(" + qualId + ");";

        stmt3.execute(sql3);
        stmt3.close();

    }
}
