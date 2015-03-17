/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import handlers.QualificationHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Employee;
import model.Qualification;
import model.QualificationType;
import model.Room;

/**
 *
 * @author Yousef
 */
public class QualificationControl {
    public QualificationControl() throws SQLException, ClassNotFoundException {
        
    }
    
    public void createQualification(QualificationType type, Boolean training) throws SQLException{
        QualificationHandler.getInstance().createQualification(type, training);
        
    }
    
    public ArrayList<Qualification> getQualifications(){
        return QualificationHandler.getInstance().getQualifications();
        
    }
    
    public ArrayList<Qualification> getEmployeeQualifications(Employee selectedEmployee) throws ClassNotFoundException{
        return QualificationHandler.getInstance().getEmployeeQualifications(selectedEmployee);
        
    }
    
    public ArrayList<Qualification> getQualificationsForSeveralEmployees(ArrayList<Employee> employees) throws ClassNotFoundException{
        return QualificationHandler.getInstance().getQualificationsForSeveralEmployees(employees);
        
    }
    
    public ArrayList<Qualification> getRoomQualifications(Room room) throws ClassNotFoundException{
        return QualificationHandler.getInstance().getRoomQualifications(room);
        
    }
    
    public void setEmployeeQualifications(ArrayList<Qualification> selectedQualifications){
        QualificationHandler.getInstance().setEmployeeQualifications(selectedQualifications);
    }
    
    
}
