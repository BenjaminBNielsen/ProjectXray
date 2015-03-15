/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import databaseConnection.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
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


                qualifications.add(new Qualification(qName, false));

            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        }

        return qualifications;

    }

    public ArrayList<Qualification> getRoomQualifications(Room selectedRoom) {
         ArrayList<Qualification> employeeQualifications = new ArrayList<>();
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
            
            String SQL = "Select * from qualification where roomNumber = " + "";//selectedRoom.getRoomNr();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String qName = rs.getString("qname");
                Boolean training = rs.getBoolean("training");

                employeeQualifications.add(new Qualification(qName, training));
            }
            
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        }

        return employeeQualifications;
    }
    
    public ArrayList<Qualification> getEmployeeQualifications(Employee selectedEmployee) {
         ArrayList<Qualification> employeeQualifications = new ArrayList<>();
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
            
            String SQL = "Select * from qualification where employeeCPR = " + "";//selectedEmployee.getCPR();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String qName = rs.getString("qname");
                Boolean training = rs.getBoolean("training");

                employeeQualifications.add(new Qualification(qName, false));
            }
            
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        }

        return employeeQualifications;
    }
    
    public void createQualifications(String qName, Boolean training) {
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String SQL = "insert into qualification() values (";
            SQL += qName + ",'" + training + ")";

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
