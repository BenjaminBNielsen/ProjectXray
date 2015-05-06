/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import exceptions.DatabaseException;
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

    public void addEmployees(ObservableList<Employee> employees) throws DatabaseException {

        EmployeeHandler.getInstance().addEmployees(employees);
    }

    public void addStudents(ObservableList<Student> students) throws DatabaseException {

        StudentHandler.getInstance().addStudents(students);
    }

    public ArrayList<Employee> getEmployees() throws DatabaseException {
        return EmployeeHandler.getInstance().getEmployees();
    }

    public ArrayList<Student> getStudents() throws DatabaseException {
        return StudentHandler.getInstance().getStudents();
    }
    
    public ObservableList<Occupation> getOccupations() throws DatabaseException {
        return OccupationHandler.getInstance().getOccupations();
    }

}
