/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.schema;

import javafx.scene.control.Label;
import model.Employee;

/**
 *
 * @author Benjamin
 */
public class EmployeeLabel extends Label{
 
    private Employee employee;
    
    public EmployeeLabel(Employee employee){
        this.employee = employee;
        this.setText(employee.getFirstName() + " " + employee.getLastName());
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
