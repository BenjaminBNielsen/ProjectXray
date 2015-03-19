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
import view.DatabasePopup;

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

    private Xray() throws SQLException, ClassNotFoundException {

        
    }

    public void createConnection(){
                //Opret forbindelse til databasen
        if (!DatabaseConnection.getInstance().hasConnection()) {
            try {
                System.out.println("heheheh");
                DatabaseConnection.getInstance().createConnection();
                                roomControl = new RoomControl();
        qualificationControl = new QualificationControl();
        personControl = new PersonControl();

            } catch (FileNotFoundException ex) {
                DatabasePopup dbp = new DatabasePopup();
                dbp.display("1");
            } catch (ClassNotFoundException ex) {
                DatabasePopup dbp = new DatabasePopup();
                dbp.display("2");
            } catch (SQLException ex) {
                DatabasePopup dbp = new DatabasePopup();
                dbp.display("3");
            }
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

}
