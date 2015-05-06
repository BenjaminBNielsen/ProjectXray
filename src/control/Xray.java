/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.comparators.*;
import dbc.DatabaseConnection;
import handlers.TimeInvestmentHandler;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import model.Employee;
import model.LimitQualification;
import model.Room;
import model.RoomQualification;
import model.TimeInvestment;
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
    private Connection databaseConnection;

    private Xray() throws SQLException, ClassNotFoundException {
        roomControl = new RoomControl();
        qualificationControl = new QualificationControl();
        personControl = new PersonControl();
    }

    public void createConnection() throws FileNotFoundException, SQLException, ClassNotFoundException {
        //Opret forbindelse til databasen
        if (!DatabaseConnection.getInstance().hasConnection()) {
            DatabaseConnection.getInstance().createConnection();
        }

        databaseConnection = DatabaseConnection.getInstance().getConnection();

    }

    public void addTimeInvestments(ArrayList<TimeInvestment> shifts) throws
            SQLException, ClassNotFoundException {
        TimeInvestmentHandler.getInstance().addTimeInvestments(shifts);
    }

    public ArrayList<TimeInvestment> getUnassignedTimeInvestments() throws
            SQLException, ClassNotFoundException {
        return TimeInvestmentHandler.getInstance().getUnassignedTimeInvestments();
    }

    public static Xray getInstance() {
        if (Instance == null) {
            try {
                Instance = new Xray();
            } catch (SQLException ex) {
            } catch (ClassNotFoundException ex) {
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

    /**
     * Denne metode tildeler rum til medarbejdere.
     *
     * @param shifts liste over vagter.
     * @param roomQualifications
     * @param limitQualifications
     * @param rooms liste over rum.
     *
     * @return Returnerer en liste af timeInvestments der har fået sine rum
     * tildelt.
     */
    public ArrayList<TimeInvestment> assignRooms(ArrayList<TimeInvestment> unassignedShifts,
            ArrayList<RoomQualification> roomQualifications,
            ArrayList<LimitQualification> limitQualifications) throws SQLException, ClassNotFoundException {
//        ArrayList<TimeInvestment> unassignedShifts = TimeInvestmentHandler.getInstance().
//                getUnassignedTimeInvestments();
//        ArrayList<RoomQualification> roomQualifications = qualificationControl.
//                getRoomQualifications();
//        ArrayList<LimitQualification> limitQualifications = qualificationControl.
//                getLimitQualifications();
        ArrayList<TimeInvestment> assignedShifts = new ArrayList<>();
        
        //Sortér liste af vagter ud fra denne prioritet: 1: dato, 2: timer, 3: minutter
        //4: Antal tilgængelige rum for medarbejder (lavest først) 5: fornavn 6: efternavn.
        unassignedShifts.sort(new RoomAmountComparator(roomQualifications));

        //Gennemløbnign af shifts, til tildeling af rum.
        for (int i = 0; i < unassignedShifts.size(); i++) {
            ArrayList<Room> rooms = getEmployeeRooms(unassignedShifts.get(i).getEmployee(), roomQualifications);
            TimeInvestment assignedShift = unassignedShifts.get(i);

            if (assignedShift.getRoom() == null) {
                assignedShift.setRoom(roomPriority(assignedShifts, rooms, assignedShift, limitQualifications));

            }

            if (assignedShift.getRoom() != null) {
                assignedShifts.add(assignedShift);
            } else {
                System.out.println("DENNE VAGT HAR IKKE FÅET TILDELT ET RUM");
            }
        }

        return assignedShifts;
    }

    /**
     * Denne metode finder det rum der har højest prioritet.
     *
     * @param timeInvestments liste over tidsinvesteringer (tildelinger af rum
     * til medarbejdere).
     * @param rooms liste over rum.
     * @return Det højst-prioriterede rum.
     */
    private Room roomPriority(ArrayList<TimeInvestment> timeInvestments,
            ArrayList<Room> rooms, TimeInvestment currentShift,
            ArrayList<LimitQualification> limitQualifications) {
        //Room objekt som starter med en null pointer, bliver uanset hvad defineret
        //senere.
        Room prioritizedRoom = null;

        //Tæl hvor mange gange de forskellige rum er blevet tildelt i currentShifts
        //starttidspunkt. Tællingen består i at alle Room-objekter i rooms får talt
        //op på deres count felt, så dette passer med antal tildelinger.

        countDateAssignmentsOfRoom(rooms, currentShift, timeInvestments);

        ArrayList<Room> roomsLimitNotReached = getRoomsLimitNotReached(currentShift.getEmployee(), limitQualifications);

        countDateAssignmentsOfRoom(roomsLimitNotReached, currentShift, timeInvestments);
        if (!roomsLimitNotReached.isEmpty()) {
            //Her inde bliver alle rum der stadig mangler at opfylde 
            //limit på limitQualifications behandlet.
            prioritizedRoom = getRoomMinMaxCompared(roomsLimitNotReached);

        } else {
            prioritizedRoom = getRoomMinMaxCompared(rooms);
        }

        return prioritizedRoom;
    }

    public boolean isAllreadyInList(Room room, ArrayList<Room> rooms) {
        boolean allreadyInList = false;
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomName().equals(room.getRoomName())) {
                allreadyInList = true;
            }
        }
        return allreadyInList;
    }

    /**
     * Henter alle de rum som en specifik medarbejder - ud fra sine
     * rumKvalifikationer - kan blive tildelt.
     *
     * @param employee den specifikke medarbejder.
     * @param roomQualifications en liste af rumKvalifikationer.
     * @return Den liste af rum som den specifikke medarbejder er kvalificeret
     * til.
     */
    public ArrayList<Room> getEmployeeRooms(Employee employee, ArrayList<RoomQualification> roomQualifications) {
        ArrayList<Room> employeeRooms = new ArrayList<>();

        //Løb den givne liste af rumKvalifikationer igennem.
        for (int i = 0; i < roomQualifications.size(); i++) {
            //Listen af medarbejdere på den kvalifikation for-løkken er nået til.
            ArrayList<Employee> employees = roomQualifications.get(i).getEmployees();
            for (int j = 0; j < employees.size(); j++) {
                //Lineær søgning efter den specifikke medarbejder.
                if (employees.get(j).getId() == employee.getId()) {
                    //Alle de rum som den kvalifikation er tilsluttet.
                    ArrayList<Room> rooms = roomQualifications.get(i).getRooms();
                    for (int k = 0; k < rooms.size(); k++) {
                        //tilføj til listen over rum, den specifikke medarbejder,
                        //kvalificeret til. Tilføj dem kun hvis de er åbne.
                        if (rooms.get(k).getRoomState() == 1) {
                            //Hvis ikke det nuværende rum allerede findes i listen.
                            if (!isAllreadyInList(rooms.get(k), employeeRooms)) {
                                employeeRooms.add(rooms.get(k));
                            }
                        }
                    }
                }
            }
        }
        return employeeRooms;
    }

    /**
     * Henter alle de rum som en specifik medarbejder - ud fra sine
     * limitKvalifikationer - kan blive tildelt.
     *
     * @param employee den specifikke medarbejder.
     * @param limitQualifications en liste af limitKvalifikationer.
     * @return Den liste af rum som den specifikke medarbejder er kvalificeret
     * til.
     */
    public ArrayList<Room> getEmployeeLimitRooms(Employee employee,
            ArrayList<LimitQualification> limitQualifications) {
        ArrayList<Room> employeeLimitRooms = new ArrayList<>();

        //Løb den givne liste af limitKvalifikationer igennem.
        for (int i = 0; i < limitQualifications.size(); i++) {
            //Listen af medarbejdere på den kvalifikation for-løkken er nået til.
            ArrayList<Employee> employees = limitQualifications.get(i).getEmployees();
            for (int j = 0; j < employees.size(); j++) {
                //Lineær søgning efter den specifikke medarbejder.
                if (employees.get(j).getId() == employee.getId()) {
                    //Alle de rum som den kvalifikation er tilsluttet.
                    ArrayList<Room> rooms = limitQualifications.get(i).getRooms();
                    for (int k = 0; k < rooms.size(); k++) {
                        //tilføj til listen over rum, den specifikke medarbejder,
                        //kvalificeret til. Tilføj dem kun hvis de er åbne.
                        if (rooms.get(k).getRoomState() == 1) {
                            employeeLimitRooms.add(rooms.get(k));
                        }
                    }
                }
            }
        }

        return employeeLimitRooms;
    }

    /**
     * Formålet med denne metode er at tælle én op på et rum i en given liste af
     * rum for hver gang dette rum er blevet tildelt en medarbejder ved hjælp af
     * et TimeInvestment-objekt. På en given vagts starttidspunkt. På denne måde
     * kan man stole på at alle rum i den givne liste har et count felt der
     * passer til den rigtige dato, frem for at være tidsløst (feltet angiver
     * kun et count, og er ligeglad med tiden, denne funktionalitet sikrer at
     * tiden stemmer).
     *
     * @param rooms
     * @param currentShift
     * @param timeInvestments
     */
    public void countDateAssignmentsOfRoom(ArrayList<Room> rooms, TimeInvestment currentShift,
            ArrayList<TimeInvestment> timeInvestments) {

        //Nulstil roomObjekternes count felt.
        for (Room room : rooms) {
            room.setCount(0);
        }

        if (timeInvestments.size() > 0) {
            for (int i = 0; i < timeInvestments.size(); i++) {
                String roomName = timeInvestments.get(i).getRoom().getRoomName();
                //Lineær søgning. Hvis det givne rum findes så skal dets tæller,
                //tælle én op.
                for (int j = 0; j < rooms.size(); j++) {
                    if (roomName.equals(rooms.get(j).getRoomName())) {
                        TimeInvestment currentTI = timeInvestments.get(i);
                        if (shiftIsInPeriod(currentShift, currentTI)) {
                            rooms.get(j).increment();
                            break; //break OK i søgning for optimering.
                        }
                        break;
                    }
                }
            }
            
        }
    }

    /**
     * Denne metode tjekker at en given tidsinvestering findes i en anden
     * tidsinvesterings tidsperiode. Denne tidsperiode er givet ved otherShift's
     * starttidspunkt, og dens sluttidspunkt som udregnes ved at lægge
     * tidsinvesteringens timer og minutter til.
     *
     * @param currentShift Den nuværende shift, hvis starttidspunkt skal være i
     * otherShifts.
     * @param otherShift Den anden vagt som currentShift skal sammenlignes med.
     * @return En boolean hvis værdi er sand hvis currentShift's starttidspunkt
     * befinder sig i otherShift's tidsperiode.
     */
    private boolean shiftIsInPeriod(TimeInvestment currentShift, TimeInvestment otherShift) {
        boolean isInPeriod = false;
        LocalDateTime otherShiftEndTime = otherShift.getStartTime().
                plus(otherShift.getHours()).plus(otherShift.getMinutes());

        //Other shift som sammenlignes med currentShift skal have sit starttidspunkt
        //før eller samtidig med currentShifts, og dens sluttidspunkt skal være
        //efter eller samtidig med currentShifts starttidspunkt.
        if (otherShift.getStartTime().isBefore(currentShift.getStartTime())
                || otherShift.getStartTime().isEqual(currentShift.getStartTime())) {
            if (otherShiftEndTime.isAfter(currentShift.getStartTime())) {
                //Hvis disse betingelser bliver opfyldt må det betyde at currentShift
                //er i otherShift's tidsperiode.
                isInPeriod = true;
            }
        }
        return isInPeriod;
    }

    private ArrayList<Room> getRoomsLimitNotReached(Employee employee,
            ArrayList<LimitQualification> limitQualifications) {

        ArrayList<Room> roomsLimitNotReached = new ArrayList<>();

        //Løb den givne liste af limitKvalifikationer igennem.
        for (int i = 0; i < limitQualifications.size(); i++) {
            //Listen af medarbejdere på den kvalifikation for-løkken er nået til.
            ArrayList<Employee> employees = limitQualifications.get(i).getEmployees();
            for (int j = 0; j < employees.size(); j++) {
                //Lineær søgning efter den specifikke medarbejder.
                if (employees.get(j).getId() == employee.getId()) {
                    //Alle de rum som den kvalifikation er tilsluttet.
                    ArrayList<Room> limitRooms = limitQualifications.get(i).getRooms();
                    for (int k = 0; k < limitRooms.size(); k++) {
                        Room limitRoom = limitRooms.get(k);
                        //tilføj til listen over rum, den specifikke medarbejder,
                        //er kvalificeret til. Tilføj dem kun hvis de er åbne.
                        if (limitRoom.getRoomState() == 1) {
                            //Hvis ikke det nuværende rum allerede findes i listen.
                            if (!isAllreadyInList(limitRoom, roomsLimitNotReached)) {
                                //Hvis begrænsningen endnu ikke er nået så tilføj
                                //til roomsLimitNotReached.
                                if (limitRoom.getCount() < limitQualifications.get(i).getLimit()) {
                                    roomsLimitNotReached.add(limitRoom);
                                }

                            }
                        }
                    }
                }
            }
        }
        return roomsLimitNotReached;
    }

    public Room getRoomMinMaxCompared(ArrayList<Room> rooms) {
        Room prioritizedRoom = null;

        //Initialiser en liste over rum, som skal indeholde alle de rum der har nået 
        //sine minimummer.
        ArrayList<Room> tempRoomsMinReached = new ArrayList<>();

        for (int i = 0; i < rooms.size(); i++) {
            if (!rooms.isEmpty()) {
                if (rooms.get(i).getMinOccupation() <= rooms.get(i).getCount()) {
                    //Kontroller at det nuværende rum har nået sit minimum, tilføj det
                    //til tempRoomsMinReached og fjern det fra rooms.
                    tempRoomsMinReached.add(rooms.get(i));
                    rooms.remove(i);

                    i--;
                }
            }
        }

        if (!rooms.isEmpty()) {
            rooms.sort(new RoomMinimumComparator());

            //Prioriter det rum der er blevet tildelt færrest gange. (højest prioritet
            //må være på plads 0).
            prioritizedRoom = rooms.get(0);
        } else {
            for (int i = 0; i < tempRoomsMinReached.size(); i++) {
                if (!tempRoomsMinReached.isEmpty()) {
                    if (tempRoomsMinReached.get(i).getMaxOccupation() <= tempRoomsMinReached.get(i).getCount()) {
                        tempRoomsMinReached.remove(i);

                        i--;
                    }
                }
            }
            tempRoomsMinReached.sort(new RoomMaximumComparator());

            if (!tempRoomsMinReached.isEmpty()) {
                prioritizedRoom = tempRoomsMinReached.get(0);

            }
        }

        return prioritizedRoom;
    }

    /**
     * Metode til at finde vagter på en given dato og i en given tidsperiode, 
     * i en given liste.
     *
     * @param date dato der skal søges efter.
     * @param shifts liste af vagter der skal søges i.
     * @param startHour Hvornår perioden starter i timer.
     * @param startMinute Hvornår perioden starter i minutter.
     * @param periodLengthHour Hvor lang tid perioden tager i timer.
     * @param periodLengthMinute Hvor lang tid perioden tager i minutter.
     * @return en liste af alle vagter i en given tidsperiode på en given dato.
     */

    public ArrayList<TimeInvestment> getShiftsInPeriod(LocalDateTime date, 
            ArrayList<TimeInvestment> shifts,
            int startHour, int startMinute, int periodLengthHour, int periodLengthMinute) {
        ArrayList<TimeInvestment> shiftsOnDate = new ArrayList<>();

        date = date.withField(DateTimeFieldType.hourOfDay(), 0);
        date = date.withField(DateTimeFieldType.minuteOfHour(), 0);

        LocalDateTime dateEndOfDay = date.plusHours(23).plusMinutes(59);

        for (int i = 0; i < shifts.size(); i++) {
            if (shifts.get(i).getStartTime().isAfter(date) && shifts.get(i).
                    getStartTime().isBefore(dateEndOfDay)) {
                shiftsOnDate.add(shifts.get(i));
            }
        }
        
        for (int j = 0; j < shiftsOnDate.size(); j++) {
                boolean isInPeriod = Xray.getInstance().isDateInPeriod(shiftsOnDate.get(j).getStartTime(), 
                        startHour, startMinute, periodLengthHour, periodLengthMinute);
                if(!isInPeriod){
                    shiftsOnDate.remove(j);
                    if(shiftsOnDate.size() > 0){
                        j--;
                    }
                }
            }
            
        return shiftsOnDate;
    }

    /**
     * Tjekker om en given dato er i en given tidsperiode.
     * @param date Dato der skal være i den givne periode hvis betingelsen skal
     * blive sand.
     * @param startHour Hvornår perioden starter i timer.
     * @param startMinute Hvornår perioden starter i minutter.
     * @param periodLengthHour Hvor lang tid perioden tager i timer.
     * @param periodLengthMinute Hvor lang tid perioden tager i minutter.
     * @return En boolean som er sand hvis den givne dato er i den givne tidsperiode.
     */
    public boolean isDateInPeriod(LocalDateTime date,
            int startHour, int startMinute, int periodLengthHour, int periodLengthMinute) {
        boolean inPeriod = false;

        LocalDateTime dateStartOfPeriod = date.withField(DateTimeFieldType.hourOfDay(), startHour);
        dateStartOfPeriod = dateStartOfPeriod.withField(DateTimeFieldType.minuteOfHour(), startMinute);
        LocalDateTime dateEndOfPeriod = dateStartOfPeriod.plusHours(periodLengthHour)
                .plusMinutes(periodLengthMinute);
        if (date.isEqual(dateStartOfPeriod)
                || (date.isBefore(dateEndOfPeriod)
                && date.isAfter(dateStartOfPeriod))) {
            inPeriod = true;
        }

        return inPeriod;
    }

}
