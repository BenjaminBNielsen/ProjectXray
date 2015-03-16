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
public class Xray {
    private static Xray Instance;
    
    public ArrayList<Qualification> getQualifications(){
        return QualificationControl.getInstance().getQualifications();
        
    }
    
    public ArrayList<Qualification> getEmployeeQualifications(Employee selectedEmployee){
        return QualificationControl.getInstance().getEmployeeQualifications(selectedEmployee);
        
    }
    
//    public ArrayList<Qualification> getRoomQualifications(Room selectedRoom){
//        return QualificationControl.getInstance().getRoomQualifications(selectedRoom);
//        
//    }
    
    public static Xray getInstance() {
        if (Instance == null) {
            Instance = new Xray();
        }
        return Instance;
}
}