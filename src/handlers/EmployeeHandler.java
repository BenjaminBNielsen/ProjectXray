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
import javafx.collections.ObservableList;
import model.Employee;
import model.Occupation;

/* @author Benjamin */
public class EmployeeHandler {

    //Singleton instance.
    private static EmployeeHandler instance;
    private Employee employee;

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
                + firstName + "','" + lastName + "'," + occupation.getId() + ");";

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

//            employees.add(new Employee(firstName, lastName, cpr, phoneNumber, address,
//                    eMail, new Occupation()));
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
    
//    public Employee getEmployee(int employeeNr) throws SQLException, ClassNotFoundException {
//        
//        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
//        employee = null;
//        
//        String sql = "Select * from employee,person where id = nr" + 
//                        "and nr = " + employeeNr;
//        
//        ResultSet rs = stmt.executeQuery(sql);
//
//        if (rs.next()) {
//            String firstName = ("firstName");
//            String lastName = ("lastName");
//            int telephone = rs.getInt("telephone");
//            String address = ("adress");
//            String mail = ("mail");
//
//            employee = new Employee(firstName, lastName, employeeNr, telephone, address, mail); 
//        }
//
//        rs.close();
//        stmt.close();
//        return employee;
//    }

    public void addEmployees(ObservableList<Employee> employees) throws SQLException {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "insert into person() values";
        for (int i = 0; i < employees.size(); i++) {
            int id = employees.get(i).getId();
            String firstName = employees.get(i).getFirstName();
            String lastName = employees.get(i).getLastName();

            //Hvis det ikk er den sidste employee indsættes den i sql statementet
            //med et "," til sidst så flere personer kan tilføjes
            if (i != employees.size() - 1) {
                sql += "(" + id + ",'" + firstName + "','" + lastName + "'),";
            } else {
                sql += "(" + id + ",'" + firstName + "','" + lastName + "');";
            }

        }

        //Eksekver sql statementen
        System.out.println(sql);
        stmt.execute(sql);
        
        //Lav en ny statement
        stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        sql = "insert into employee() values";
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            
            //indsætter som medarbejder.
            if(i != employees.size()-1){
            sql +="(" + employee.getId() + "," + employee.getPhoneNumber() + ",'" 
                    + employee.getAddress() + "','" + employee.geteMail() + "'," +
                        employee.getOccupation().getId() + ");";
            }else{
                sql +="(" + employee.getId() + "," + employee.getPhoneNumber() + ",'" 
                    + employee.getAddress() + "','" + employee.geteMail() + "'," +
                        employee.getOccupation().getId() + ");";
            }
            System.out.println(employee.getFirstName() + " " + " indsat successfuldt.");
        }
        System.out.println(sql);

        stmt.execute(sql);
        stmt.close();
        
    }

}
