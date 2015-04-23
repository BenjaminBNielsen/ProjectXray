/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import handlers.EmployeeHandler;
import handlers.OccupationHandler;
import handlers.StudentHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import model.Employee;
import model.Occupation;
import model.Student;

/**
 *
 * @author Benjamin
 */
public class PersonControl {

    public void addEmployees(ObservableList<Employee> employees) throws SQLException, ClassNotFoundException {

        EmployeeHandler.getInstance().addEmployees(employees);
    }

    public void addStudents(ObservableList<Student> students) throws SQLException, ClassNotFoundException {

        StudentHandler.getInstance().addStudents(students);
    }

    public ArrayList<Employee> getEmployees() throws SQLException, ClassNotFoundException {
        return EmployeeHandler.getInstance().getEmployees();
    }

    public ArrayList<Student> getStudents() throws SQLException, ClassNotFoundException {
        return StudentHandler.getInstance().getStudents();
    }
    
    public ObservableList<Occupation> getOccupations() throws SQLException, ClassNotFoundException {
        return OccupationHandler.getInstance().getOccupations();
    }

}
