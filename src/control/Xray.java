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
public class Xray {
    private static Xray Instance;
    private RoomControl roomControl;
    private QualificationControl qualificationControl;
    private PersonControl personControl;
    
    private Xray() throws SQLException, ClassNotFoundException {
        roomControl = new RoomControl();
        qualificationControl = new QualificationControl();
        personControl = new PersonControl();
    }
    
    private static Xray getInstance() throws SQLException, ClassNotFoundException {
        if (Instance == null) {
            Instance = new Xray();
        }
        return Instance;
    }

    public RoomControl getRoomControl() {
        return roomControl;
    }

    public void setRoomControl(RoomControl roomControl) {
        this.roomControl = roomControl;
    }

    public QualificationControl getQualificationControl() {
        return qualificationControl;
    }

    public void setQualificationControl(QualificationControl qualificationControl) {
        this.qualificationControl = qualificationControl;
    }
    
    
}