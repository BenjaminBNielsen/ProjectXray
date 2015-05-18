/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import exceptions.DatabaseException;
import technicalServices.persistence.QualificationHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import model.CourseQualification;
import model.Employee;
import model.Qualification;
import model.Room;
import model.RoomQualification;
import model.LimitQualification;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Yousef
 */
public class QualificationControl {
    public QualificationControl() throws DatabaseException {
        
    }
    
    public ArrayList<RoomQualification> getRoomQualifications() throws DatabaseException{
        return QualificationHandler.getInstance().getRoomQualifications();
    }
    
    public ArrayList<LimitQualification> getLimitQualifications() throws DatabaseException{
        return QualificationHandler.getInstance().getLimitQualifications();
    }
    
    public ArrayList<CourseQualification> getLimitCourseQualification() throws DatabaseException{
        return QualificationHandler.getInstance().getCourseQualifications();
    }
    
   
//    public void createQualification(QualificationType type, Boolean training, Room room) throws SQLException{
//        QualificationHandler.getInstance().createQualification(type, training, room);
//        
//    }
    
//    public ArrayList<Qualification> getQualifications(){
//        return QualificationHandler.getInstance().getRoomQualifications();
//        
//    }
    
//    public ArrayList<LimitQualification> getSingleQualifications(Employee employee) throws ClassNotFoundException{
//        return QualificationHandler.getInstance().getSingleQualifications();
//        
//    }
    
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
    
    public void addRoomQualification(ObservableList<Room> observableRooms, ObservableList<Employee> observableEmployees, String type) throws DatabaseException {
        QualificationHandler.getInstance().addRoomQualification(observableRooms, observableEmployees, type);
    }
    
    public void addLimitQualification(ObservableList<Room> observableRooms, ObservableList<Employee> observableEmployees, String type, int limit) throws DatabaseException {
        QualificationHandler.getInstance().addLimitQualification(observableRooms, observableEmployees, type, limit);
    }
    
    public void addCourseQualification(ObservableList<Room> observableRooms, ObservableList<Employee> observableEmployees, String type, int limit, LocalDateTime localDateStart, LocalDateTime localDateEnd) throws DatabaseException {
        QualificationHandler.getInstance().addLimitCourseQualification(observableRooms, observableEmployees, type, limit, localDateStart, localDateEnd);
    }
            
}
