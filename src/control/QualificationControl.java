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
import model.Room;

/**
 *
 * @author Yousef
 */
public class QualificationControl {
    private static QualificationControl Instance;
    
    public void createQualification(String qName, Boolean training) throws SQLException{
        QualificationHandler.getInstance().createQualification(qName, training);
        
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
    
    public ArrayList<Qualification> getRoomQualifications(Room selectedRoom) throws ClassNotFoundException{
        return QualificationHandler.getInstance().getRoomQualifications(selectedRoom);
        
    }
    
    public void setEmployeeQualifications(ArrayList<Qualification> selectedQualifications){
        QualificationHandler.getInstance().setEmployeeQualifications(selectedQualifications);
    }
    
//    public static QualificationControl getInstance() {
//        if (Instance == null) {
//            Instance = new QualificationControl();
//        }
//        return Instance;
//    }
}
