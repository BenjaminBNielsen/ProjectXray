 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import control.Xray;
import dbc.DatabaseConnection;
import exceptions.DatabaseException;
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

    //
    //Constructor
    public static EmployeeHandler getInstance() {
        if (instance == null) {
            instance = new EmployeeHandler();
        }
        return instance;
    }

    public void addEmployee(Employee employee) throws DatabaseException {
        try{
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        int nr = employee.getId();
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        int phoneNumber = employee.getPhoneNumber();
        String address = employee.getAddress();
        String eMail = employee.geteMail();
        int occupationId = employee.getOccupation().getId();
        
        //indsætter som person.
        String sql = "insert into person() values(" + nr + ",'" + firstName + "','"
                + lastName + "');";

        stmt.execute(sql);

        //indsætter som medarbejder.
        sql = "insert into employee() values(" + nr + ",";
        sql += phoneNumber + ",'" + address + "','" + eMail + "',"
                + occupationId+ ");";

        stmt.execute(sql);

        stmt.close();
        } catch(SQLException ex) {
            throw new DatabaseException("Der findes allerede en ansat med det ID.");
        }
    }

    public ArrayList<Employee> getEmployees() throws DatabaseException {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

        String sql = "select * from person,employee where id = nr";

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            int nr = rs.getInt("nr");
            int phoneNumber = rs.getInt("telephone");
            String address = rs.getString("address");
            String eMail = rs.getString("mail");
            Occupation occupation = OccupationHandler.getInstance().
                    getOccupation(rs.getInt("occupationId"));

            employees.add(new Employee(firstName, lastName, nr, phoneNumber, address,
                    eMail, occupation));
            
        }

        rs.close();
        stmt.close();

        return employees;
        } catch(SQLException ex) {
            throw new DatabaseException("Der kan ikke være flere employees med det nummer.");
        }
    }
    
    public Employee getEmployee(int employeeNr) throws DatabaseException {
        try {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        Employee employee = null;
        
        String sql = "Select * from employee,person where id = nr" + 
                        " and nr = " + employeeNr;
        
        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            int telephone = rs.getInt("telephone");
            String address = rs.getString("address");
            String mail = rs.getString("mail");
            Occupation occupation = OccupationHandler.getInstance().
                    getOccupation(rs.getInt("occupationId"));
            employee = new Employee(firstName, lastName, employeeNr, telephone, address, mail, occupation); 
        }

        rs.close();
        stmt.close();
        return employee;
        } catch(SQLException ex) {
            throw new DatabaseException("Der kunne ikke findes en ansat med det ID.");
        }
    }

    public void addEmployees(ObservableList<Employee> employees) throws DatabaseException {
        try {
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
        stmt.execute(sql);
        } catch(SQLException ex) {
            throw new DatabaseException("Der findes allerede en person med det ID.");
        }
        //Lav en ny statement
        try {
        Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
        String sql = "insert into employee() values";
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            
            //indsætter som medarbejder.
            if(i != employees.size()-1){
            sql +="(" + employee.getId() + "," + employee.getPhoneNumber() + ",'" 
                    + employee.getAddress() + "','" + employee.geteMail() + "'," +
                        employee.getOccupation().getId() + "),";
            }else{
                sql +="(" + employee.getId() + "," + employee.getPhoneNumber() + ",'" 
                    + employee.getAddress() + "','" + employee.geteMail() + "'," +
                        employee.getOccupation().getId() + ");";
            }
        }

        stmt.execute(sql);
        stmt.close();
        } catch(SQLException ex) {
            throw new DatabaseException("Der findes allerede en person med det ID.");
        }
        
    }

}
