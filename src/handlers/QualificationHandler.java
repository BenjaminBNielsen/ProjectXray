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
import model.Qualification;

/**
 *
 * @author Yousef
 */
public class QualificationHandler {
    private static QualificationHandler instance;
    //Denne ArrayList skal indeholde alle klubber i databasen.
    private ArrayList<Qualification> qualifications;

    private QualificationHandler() {
    }

    public ArrayList<Qualification> getQualification() {

        try {
            // Henter statementet fra den åbne forbindelse i MapperHandleren.
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            // Henter hele tabellen ind i et resultset.
            String SQL = "Select * from ";
            ResultSet rs = stmt.executeQuery(SQL);

            // Løber tabellen igennem og opretter klubobjekter løbende.
            while (rs.next()) {
                String qName = rs.getString("qName");
                Boolean training = rs.getBoolean("training");

                // Alle klubber fra databasen fyldes ind i ArrayListen.
                qualifications.add(new Qualification(qName, true));

            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        }

        // Returnerer ArrayListen.
        return qualifications;

    }

    public ArrayList<Qualification> getRoomQualification(/*Room selectedRoom*/) {
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
    
    public ArrayList<Qualification> getEmployeeQualification(/*Employee selectedEmployee*/) {
         ArrayList<Qualification> employeeQualifications = new ArrayList<>();
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
            
            String SQL = "Select * from qualification where employeeCPR = " + "";//selectedEmployee.getCPR();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String qName = rs.getString("qname");
                Boolean training = rs.getBoolean("training");

                employeeQualifications.add(new Qualification(qName, true));
            }
            
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        }

        return employeeQualifications;
    }
    
    public void createQualification(String qName, Boolean training) {
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
        if (instance == null) {
            instance = new QualificationHandler();
        }
        return instance;
    }
    
}
