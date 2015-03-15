/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import handlers.EmployeeHandler;
import handlers.StudentHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Employee;
import model.Student;

/**
 *
 * @author Benjamin
 */
public class PersonControl {

    public ArrayList<Employee> getEmployees() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employees = EmployeeHandler.getInstance().getEmployees();

        return employees;
    }
    
        public ArrayList<Student> getStudents() throws SQLException, ClassNotFoundException {
        ArrayList<Student> students = StudentHandler.getInstance().getStudents();

        return students;
    }

}
