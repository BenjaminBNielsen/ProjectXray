/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Yousef
 */
public class SingleQualification extends Qualification {

    private Employee employee;

    public SingleQualification(int id, boolean training, Employee employee) {
        super(id, training);
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
