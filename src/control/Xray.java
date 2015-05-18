/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.comparators.*;
import dbc.DatabaseConnection;
import exceptions.ControlException;
import exceptions.DatabaseException;
import technicalServices.persistence.TimeInvestmentHandler;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import model.Employee;
import model.LimitQualification;
import model.Room;
import model.RoomQualification;
import model.TimeInvestment;
import model.TimePeriod;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDateTime;
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
    private TimePeriodControl timePeriodControl;
    private TimeInvestmentControl timeInvestmentControl;
    private Connection databaseConnection;

    private Xray() throws DatabaseException {
        roomControl = new RoomControl();
        qualificationControl = new QualificationControl();
        personControl = new PersonControl();
        timePeriodControl = new TimePeriodControl();
        timeInvestmentControl = new TimeInvestmentControl();
    }

    public void createConnection() throws DatabaseException {
        //Opret forbindelse til databasen

        if (!DatabaseConnection.getInstance().hasConnection()) {
            System.out.println("heheheh");
            DatabaseConnection.getInstance().createConnection();
            roomControl = new RoomControl();
            qualificationControl = new QualificationControl();
            personControl = new PersonControl();

        }

        databaseConnection = DatabaseConnection.getInstance().getConnection();

    }

    public static Xray getInstance() {
        if (Instance == null) {
            try {
                Instance = new Xray();
            } catch (DatabaseException ex) {

            }
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

    public TimePeriodControl getTimePeriodControl() {
        return timePeriodControl;
    }

    public void setTimePeriodControl(TimePeriodControl timePeriodControl) {
        this.timePeriodControl = timePeriodControl;
    }

    public TimeInvestmentControl getTimeInvestmentControl() {
        return timeInvestmentControl;
    }

    public void setTimeInvestmentControl(TimeInvestmentControl timeInvestmentControl) {
        this.timeInvestmentControl = timeInvestmentControl;
    }

    /**
     * Tjekker om en given dato er i en given tidsperiode.
     *
     * @param dateTime metoden returnerer sand, hvis denne parameter ligger i
     * den givne periode.
     * @param periodStart definerer periodens start.
     * @param periodEnd definerer periodens slutning.
     * @return
     */
    public boolean isDateInPeriod(LocalDateTime dateTime, LocalDateTime periodStart,
            LocalDateTime periodEnd) {
        boolean inPeriod = false;

        if (dateTime.isEqual(periodStart)
                || (dateTime.isBefore(periodEnd)
                && dateTime.isAfter(periodStart))) {
            inPeriod = true;
        }

        return inPeriod;
    }
    
    public ArrayList<LocalDateTime> getDatesInPeriod(LocalDateTime startTime,
            LocalDateTime endTime){
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        LocalDateTime currentDate = new LocalDateTime(startTime);
        currentDate = currentDate.withHourOfDay(0);
        currentDate = currentDate.withMinuteOfHour(0);

        while(currentDate.isBefore(endTime)){
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        
        return dates;
    }

}
