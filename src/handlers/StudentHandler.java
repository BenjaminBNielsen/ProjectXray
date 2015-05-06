/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import dbc.DatabaseConnection;
import exceptions.DatabaseException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import model.Student;

/* @author Benjamin */
public class StudentHandler {
    //Singleton instance.

    private static StudentHandler instance;

    //
    //Constructor
    public static StudentHandler getInstance() {
        if (instance == null) {
            instance = new StudentHandler();
        }
        return instance;
    }

    public void addStudents(ObservableList<Student> students) throws DatabaseException {
        try {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "insert into person() values";
        for (int i = 0; i < students.size(); i++) {
            int id = students.get(i).getId();
            String firstName = students.get(i).getFirstName();
            String lastName = students.get(i).getLastName();

            //Hvis det ikk er den sidste employee indsættes den i sql statementet
            //med et "," til sidst så flere personer kan tilføjes
            if (i != students.size() - 1) {
                sql += "(" + id + ",'" + firstName + "','" + lastName + "'),";
            } else {
                sql += "(" + id + ",'" + firstName + "','" + lastName + "');";
            }

        }

        //Eksekver sql statementen
        System.out.println(sql);
        stmt.execute(sql);
        stmt.close();
        } catch(SQLException ex) {
            throw new DatabaseException("Der kan ikke være flere personer med samme ID.");    
        }

        //Lav en ny statement
        try {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        String sql = "insert into student() values";
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            //indsætter som medarbejder.
            if (i != students.size() - 1) {
                sql += "(" + student.getId() + "," + student.getModule() + "),";
            } else {
                sql += "(" + student.getId() + "," + student.getModule() + ");";
            }
            System.out.println(student.getFirstName() + " " + " indsat successfuldt.");
        }
        System.out.println(sql);

        stmt.execute(sql);
        stmt.close();
        } catch(SQLException ex) {
            throw new DatabaseException("Der findes allerede en elev med det ID.");    
        }
    }

    public void addStudent(Student student) throws DatabaseException {
        try {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        int id = student.getId();
        int module = student.getModule();

        //indsætter som person.
        String sql = "insert into person() values(" + id + ",'" + firstName + "','"
                + lastName + "');";

        stmt.execute(sql);

        //indsætter som medarbejder.
        sql = "insert into student() values(" + id + "," + module + ");";

        stmt.execute(sql);

        stmt.close();
        } catch(SQLException ex) {
            throw new DatabaseException("Der findes allerede en person med det ID.");    
        }
    }

    public ArrayList<Student> getStudents() throws DatabaseException {
        ArrayList<Student> students = new ArrayList<>();
        try {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "select * from person,student where id = nr;";

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("nr");
            String firstName = ("firstName");
            String lastName = ("lastName");
            int module = rs.getInt("module");

            students.add(new Student(id, firstName, lastName, module));
        }

        rs.close();
        stmt.close();

        return students;
        } catch(SQLException ex) {
            throw new DatabaseException("Der kunne ikke hentes elever fra databasen.");    
        }
    }

    public Student getStudent(int id) throws DatabaseException {
        try {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        Student student = null;

        String sql = "Select * from student,person where id = nr"
                + " and nr = " + id;

        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            String firstName = ("firstName");
            String lastName = ("lastName");
            int module = rs.getInt("module");

            student = new Student(id, firstName, lastName, module);
        }

        rs.close();
        stmt.close();
        return student;
        } catch(SQLException ex) {
            throw new DatabaseException("Der kunne ikke findes nogen elev med det ID.");    
        }
    }

}
