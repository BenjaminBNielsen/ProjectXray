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

/**
 *
 * @author Yousef
 */
public class QualificationHandler {

    private static QualificationHandler instance;
    private ArrayList<Qualification> qualifications;

    private QualificationHandler() {
    }

    public ArrayList<Qualification> getQualifications() {

        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String SQL = "Select * from qualifiction";
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                QualificationType type = (QualificationType) rs.getObject("type");
                Boolean training = rs.getBoolean("training");
                Employee employee = null;
                Room room = null;

                qualifications.add(new Qualification(type, false, employee, room));

            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        }

        return qualifications;

    }

    public ArrayList<Qualification> getRoomQualifications(Room room) throws ClassNotFoundException {
        ArrayList<Qualification> roomQualifications = new ArrayList<>();
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();


            int roomNumber = room.getRoomNumber();
            String SQL = "Select * from qualification where roomId = " + roomNumber;
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                QualificationType type = (QualificationType) rs.getObject("type");
                Boolean training = rs.getBoolean("training");
                int id = rs.getInt("employeeid");
                Employee employee = null;

                roomQualifications.add(new Qualification(type, training, employee, room));
            }

            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        }

        return roomQualifications;
    }

    public ArrayList<Qualification> getEmployeeQualifications(Employee employee) throws ClassNotFoundException {
        ArrayList<Qualification> employeeQualifications = new ArrayList<>();
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String SQL = "Select * from qualification where employeeid = " + employee.getId();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                QualificationType type = (QualificationType) rs.getObject("type");
                Boolean training = rs.getBoolean("training");
                int employeeCPR = rs.getInt("employeeid");
                int roomNr = rs.getInt("roomnumber");
                Room room = RoomHandler.getInstance().getRoom(roomNr);

                employeeQualifications.add(new Qualification(type, training, employee, room));
            }

            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        }

        return employeeQualifications;
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

            QualificationType type = qualification.getType();
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
