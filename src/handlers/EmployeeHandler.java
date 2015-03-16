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
import model.Occupation;

/* @author Benjamin */
public class EmployeeHandler {

    //Singleton instance.
    private static EmployeeHandler instance;

    //
    //Constructor
    public static EmployeeHandler getInstance() {
        if (instance == null) {
            instance = new EmployeeHandler();
        }
        return instance;
    }

    public void addEmployee(String firstName, String lastName, int cpr, int phoneNumber,
            String address, String eMail, Occupation occupation) throws SQLException, ClassNotFoundException {

        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        //indsætter som person.
        String sql = "insert into person() values('" + firstName + "','"
                + lastName + "');";

        stmt.execute(sql);

        //indsætter som medarbejder.
        sql = "insert into employee() values(" + cpr + ",";
        sql += phoneNumber + ",'" + address + "','" + eMail + "','"
                + firstName + "','" + lastName + "');";

        stmt.execute(sql);

        stmt.close();
    }

    public ArrayList<Employee> getEmployees() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employees = new ArrayList<>();

        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "select * from person,employee where person.firstName = employee.firstName"
                + " and person.lastName = employee.lastName;";

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String firstName = ("firstName");
            String lastName = ("lastName");
            int cpr = rs.getInt("cpr");
            int phoneNumber = rs.getInt("telephone");
            String address = rs.getString("address");
            String eMail = rs.getString("mail");

            employees.add(new Employee(firstName, lastName, cpr, phoneNumber, address,
                    eMail, new Occupation()));
        }

        rs.close();
        stmt.close();

        return employees;
    }

    public Employee getEmployee(int employeeCPR) throws SQLException, ClassNotFoundException {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        Employee employee = null;

        String sql = "Select * from qualification where employeeCPR = " + employeeCPR;

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String firstName = ("firstName");
            String lastName = ("lastName");
            int cpr = rs.getInt("cpr");
            int phoneNumber = rs.getInt("telephone");
            String address = rs.getString("address");
            String eMail = rs.getString("mail");
            //String occupation = rs.getString("occupation");

            employee = new Employee(firstName, lastName, cpr, phoneNumber, address, eMail, null/*occupation*/);

        }

        rs.close();
        stmt.close();

        return employee;

    }

}
