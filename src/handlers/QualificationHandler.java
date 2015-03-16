/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import databaseConnection.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Employee;
import model.Qualification;
import model.Room;

/**
 *
 * @author Yousef
 */
public class QualificationHandler {

    private static QualificationHandler Instance;
    private ArrayList<Qualification> qualifications;

    private QualificationHandler() {
    }

    public ArrayList<Qualification> getQualifications() {

        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String SQL = "Select * from qualifiction";
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String qName = rs.getString("qName");
                Boolean training = rs.getBoolean("training");
                Employee employee = null;
                Room room = null;

                qualifications.add(new Qualification(qName, false, employee, room));

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
            String SQL = "Select * from qualification where roomnumber = " + roomNumber;
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String qName = rs.getString("qname");
                Boolean training = rs.getBoolean("training");
                Employee placeholderEmployee = null;
                int roomNr = rs.getInt("roomnumber");
                Room gottenRoom = RoomHandler.getInstance().getRoom(roomNr);

                roomQualifications.add(new Qualification(qName, training, placeholderEmployee, gottenRoom));
            }

            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        }

        return roomQualifications;
    }

    public ArrayList<Qualification> getEmployeeQualifications(Employee selectedEmployee) throws ClassNotFoundException {
        ArrayList<Qualification> employeeQualifications = new ArrayList<>();
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String SQL = "Select * from qualification where employeeCPR = " + selectedEmployee.getCpr();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String qName = rs.getString("qname");
                Boolean training = rs.getBoolean("training");
                int employeeCPR = rs.getInt("employeeCPR");
                Employee employee = EmployeeHandler.getInstance().getEmployee(employeeCPR);
                Room placeholderRoom = null;

                employeeQualifications.add(new Qualification(qName, training, employee, placeholderRoom));
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

    public void setEmployeeQualification(Qualification selectedQualification, Boolean training, Employee selectedEmployee) {
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String qName = selectedQualification.getqName();
            int employeeCPR = selectedEmployee.getCpr();

            String SQL = "insert into qualification() values ('";
            SQL += qName + "'," + training + "," + employeeCPR + ")";

            stmt.execute(SQL);
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("SQL FEJL: " + ex.getMessage());
        }

    }

    public void createQualifications(String qName, Boolean training) {
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String SQL = "insert into qualification() values ('";
            SQL += qName + "'," + training + ")";

            stmt.execute(SQL);
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("SQL FEJL: " + ex.getMessage());
        }

    }

    public static QualificationHandler getInstance() {
        if (Instance == null) {
            Instance = new QualificationHandler();
        }
        return Instance;
    }

}
