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
import model.Student;
import model.Occupation;

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

    public void addStudent(String firstName, String lastName, int module) 
            throws SQLException, ClassNotFoundException {

        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        //indsætter som person.
        String sql = "insert into person() values('" + firstName + "','"
                + lastName + "');";

        stmt.execute(sql);

        //indsætter som medarbejder.
        sql = "insert into student() values(" + module + ",'";
        sql += firstName + "','" + lastName + "');";

        stmt.execute(sql);

        stmt.close();
    }

    public ArrayList<Student> getStudents() throws SQLException, ClassNotFoundException {
        ArrayList<Student> students = new ArrayList<>();

        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "select * from person,student where person.firstName = employee.firstName"
                + " and person.lastName = employee.lastName;";

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String firstName = ("firstName");
            String lastName = ("lastName");
            int module = rs.getInt("module");

            students.add(new Student(firstName, lastName, module));
        }

        rs.close();
        stmt.close();

        return students;
    }
}
