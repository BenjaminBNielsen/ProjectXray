/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dbc.DatabaseConnection;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import view.popups.DatabasePopup;

/**
 *
 * @author Yousef
 */
public class Xray {

    private static Xray Instance;
    private RoomControl roomControl;
    private QualificationControl qualificationControl;
    private PersonControl personControl;
    private Connection databaseConnection;
    private ShiftControl shiftControl;

    private Xray() throws SQLException, ClassNotFoundException {
        roomControl = new RoomControl();
        qualificationControl = new QualificationControl();
        personControl = new PersonControl();
        shiftControl = new ShiftControl();
    }

    public void createConnection() throws FileNotFoundException, SQLException, ClassNotFoundException {
        //Opret forbindelse til databasen
        if (!DatabaseConnection.getInstance().hasConnection()) {
            DatabaseConnection.getInstance().createConnection();
        }

        databaseConnection = DatabaseConnection.getInstance().getConnection();

    }

    public static Xray getInstance() throws SQLException, ClassNotFoundException {
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

    public PersonControl getPersonControl() {
        return personControl;
    }
    
    public ShiftControl getShiftControl(){
        return shiftControl;
    }
    
    public void setShiftControl(ShiftControl shiftControl){
        this.shiftControl = shiftControl;
    }

}
