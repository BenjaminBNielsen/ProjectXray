/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import handlers.QualificationHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import model.Employee;
import model.Qualification;
import model.Room;
import model.RoomQualification;
import model.LimitQualification;

/**
 *
 * @author Yousef
 */
public class QualificationControl {
    public QualificationControl() throws SQLException, ClassNotFoundException {
        
    }
   
//    public void createQualification(QualificationType type, Boolean training, Room room) throws SQLException{
//        QualificationHandler.getInstance().createQualification(type, training, room);
//        
//    }
    
//    public ArrayList<Qualification> getQualifications(){
//        return QualificationHandler.getInstance().getRoomQualifications();
//        
//    }
    
    public ArrayList<LimitQualification> getSingleQualifications(Employee employee) throws ClassNotFoundException{
        return QualificationHandler.getInstance().getSingleQualifications(employee);
        
    }
    
//        public ArrayList<RoomQualification> getRoomQualifications(Room room) throws ClassNotFoundException{
//        //return QualificationHandler.getInstance().getRoomQualifications(room);
//        
//    }
        
//    public ArrayList<Qualification> getQualificationsForSeveralEmployees(ArrayList<Employee> employees) throws ClassNotFoundException{
//        return QualificationHandler.getInstance().getQualificationsForSeveralEmployees(employees);
//        
//    }
//    

//    
//    public void setEmployeeQualifications(ArrayList<Qualification> selectedQualifications){
//        QualificationHandler.getInstance().setEmployeeQualifications(selectedQualifications);
//    }
//    
    
}
