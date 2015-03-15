/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import handlers.QualificationHandler;
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
    
    public ArrayList<Qualification> getQualifications(){
        return QualificationHandler.getInstance().getQualifications();
        
    }
    
    public ArrayList<Qualification> getEmployeeQualifications(Employee selectedEmployee){
        return QualificationHandler.getInstance().getEmployeeQualifications(selectedEmployee);
        
    }
    
    public ArrayList<Qualification> getRoomQualifications(Room selectedRoom){
        return QualificationHandler.getInstance().getRoomQualifications(selectedRoom);
        
    }
    
    public static QualificationControl getInstance() {
        if (Instance == null) {
            Instance = new QualificationControl();
        }
        return Instance;
    }
}
