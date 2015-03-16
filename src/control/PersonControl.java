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
import model.Occupation;
import model.Student;

/**
 *
 * @author Benjamin
 */
public class PersonControl {

    public void addEmployees(ArrayList<Employee> employees) throws SQLException, ClassNotFoundException{
        
        for (Employee employee : employees) {
            String firstName = employee.getFirstName();
            String lastName = employee.getLastName();
            int cpr = employee.getId();
            int phoneNumber = employee.getPhoneNumber();
            String address = employee.getAddress();
            String eMail = employee.geteMail();
            Occupation occupation = employee.getOccupation();
            EmployeeHandler.getInstance().addEmployee(firstName, lastName, cpr, 
                    phoneNumber, address, eMail, occupation);
        }
    }
    
        public void addStudents(ArrayList<Student> students) throws SQLException, ClassNotFoundException{
        
        for (Student student : students) {
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            int module = student.getModule();

            StudentHandler.getInstance().addStudent(firstName, lastName, module);
        }
    }
    
    public ArrayList<Employee> getEmployees() throws SQLException, ClassNotFoundException {
        return EmployeeHandler.getInstance().getEmployees();
    }
    
        public ArrayList<Student> getStudents() throws SQLException, ClassNotFoundException {
        return StudentHandler.getInstance().getStudents();
    }

}
