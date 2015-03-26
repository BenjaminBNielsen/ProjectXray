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
import model.Qualification;
import model.QualificationType;
import model.Room;
import model.RoomQualification;

/**
 *
 * @author Yousef
 */
public class QualificationHandler {

    private static QualificationHandler instance;
    private ArrayList<Qualification> qualifications;

    private QualificationHandler() {
    }

    public ArrayList<Qualification> getRoomQualifications(Room room) throws ClassNotFoundException {
        ArrayList<Qualification> roomQualifications = new ArrayList<>();
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
                

                roomQualifications.add(new RoomQualification(id, training, employee, room));
            }

            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        }

        return roomQualifications;
    }

    public ArrayList<Qualification> getSingleQualifications(Employee employee) throws ClassNotFoundException {
        ArrayList<Qualification> singleQualifications = new ArrayList<>();
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String SQL = "Select * from qualification where employeeid = " + employee.getId();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("id");
                Boolean training = rs.getBoolean("training");
                String roomName = ("roomname");
                Room room = RoomHandler.getInstance().getRoomName(roomName);
                

                singleQualifications.add(new RoomQualification(id, training, employee, room));
            }

            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        }

        return singleQualifications;
    }

    public ArrayList<Qualification> getQualificationsForSeveralEmployees(ArrayList<Employee> employees) throws ClassNotFoundException {
        ArrayList<Qualification> employeesQualifications = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            Employee selectedEmployees = employees.get(i);
            employeesQualifications.add(getEmployeeQualifications(selectedEmployees).get(i));
        }
        return employeesQualifications;
    }

    public void setEmployeeQualifications(ArrayList<Qualification> selectedQualifications) {
        for (int i = 0; i < selectedQualifications.size(); i++) {
            Qualification selectedQualification = selectedQualifications.get(i);
            Boolean training = selectedQualifications.get(i).isTraining();
            Employee selectedEmployee = selectedQualifications.get(i).getEmployee();

            setEmployeeQualification(selectedQualification, training, selectedEmployee);
        }
    }

    public void setEmployeeQualification(Qualification qualification, Boolean training, Employee employee) {
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            QualificationType type = qualification.getId();
            int id = employee.getId();

            String SQL = "insert into qualification(type, training, employeeid) values (";
            SQL += type + "," + training + "," + id + ")";

            stmt.execute(SQL);
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("SQL FEJL: " + ex.getMessage());
        }

    }

    public void createQualification(QualificationType type, Boolean training, Room room) {
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String SQL = "insert into qualification(qualiTypeId, training, roomId) values (";
            SQL += type + "," + training + "," + room +")";

            stmt.execute(SQL);
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("SQL FEJL: " + ex.getMessage());
        }

    }

    public static QualificationHandler getInstance() {
        if (instance == null) {
            instance = new QualificationHandler();
        }
        return instance;
    }

}
