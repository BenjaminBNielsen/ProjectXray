/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.comparators.PeriodMinComparator;
import control.comparators.RoomAmountComparator;
import control.comparators.RoomMaximumComparator;
import control.comparators.RoomMinimumComparator;
import exceptions.DatabaseException;
import java.util.ArrayList;
import model.Employee;
import model.LimitQualification;
import model.Room;
import model.RoomAssignmentCounter;
import model.RoomQualification;
import model.TimeInvestment;
import model.TimePeriod;
import org.joda.time.LocalDateTime;
import technicalServices.persistence.TimeInvestmentHandler;

/**
 *
 * @author Benjamin
 */
public class TimeInvestmentControl {

    public void addTimeInvestments(ArrayList<TimeInvestment> shifts) throws
            DatabaseException {
        TimeInvestmentHandler.getInstance().addTimeInvestments(shifts);
    }

    public ArrayList<TimeInvestment> getUnassignedTimeInvestments() throws
            DatabaseException {
        return TimeInvestmentHandler.getInstance().getUnassignedTimeInvestments();
    }

    /**
     * Denne metode tildeler rum til medarbejdere.
     *
     * @param periodStart
     * @param periodEnd
     * @return Returnerer en liste af timeInvestments der har fået sine rum
     * tildelt.
     * @throws exceptions.DatabaseException
     */
    public ArrayList<TimeInvestment> assignRooms(LocalDateTime periodStart, 
            LocalDateTime periodEnd) throws DatabaseException {
        ArrayList<TimeInvestment> unassignedShifts = TimeInvestmentHandler.getInstance().
                getUnassignedTimeInvestments();
        unassignedShifts = getShiftsInPeriod(unassignedShifts, periodStart, periodEnd);
        ArrayList<RoomQualification> roomQualifications = Xray.getInstance().
                getQualificationControl().getRoomQualifications();
        ArrayList<LimitQualification> limitQualifications = Xray.getInstance().
                getQualificationControl().getLimitQualifications();

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
                //Vagten har ikke fået tildelt et rum, konsekvens abstraheret.
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
            ArrayList<LimitQualification> limitQualifications) throws DatabaseException {
        //Room objekt som starter med en null pointer, bliver uanset hvad defineret
        //senere.
        Room prioritizedRoom = null;

        //Tæl tildelinger til specifikke rum op for medarbejderen sluttet til 
        //currentShift. Så hvis rumX er blevet tildelt 5 gange til medarbejderen
        //i currentShift's tidsperiode, vil denne RoomAssignmentCounter blive 5,
        //og have rum reference til rumX.
        countDateAssignmentsOfRoomForEmp(rooms, currentShift, timeInvestments);

        //Tæl hvor mange gange de forskellige rum er blevet tildelt i currentShifts
        //starttidspunkt. Tællingen består i at alle Room-objekter i rooms får talt
        //op på deres count felt, så dette passer med antal tildelinger.
        countDateAssignmentsOfRoom(rooms, currentShift, timeInvestments);

        ArrayList<Room> roomsLimitNotReached = getRoomsLimitNotReached(currentShift.getEmployee(), limitQualifications);

        countDateAssignmentsOfRoom(roomsLimitNotReached, currentShift, timeInvestments);
        if (!roomsLimitNotReached.isEmpty()) {
            //Her inde bliver alle rum der stadig mangler at opfylde 
            //limit på limitQualifications behandlet.
            prioritizedRoom = getRoomMinMaxCompared(roomsLimitNotReached, currentShift);

        } else {
            prioritizedRoom = getRoomMinMaxCompared(rooms, currentShift);
        }

        return prioritizedRoom;
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
     * Returnerer en liste over alle de rum som ikke har opnået sine
     * begrænsninger, baseret på en liste af limitQualifications.
     *
     * @param employee
     * @param limitQualifications
     * @return en liste over alle de rum som ikke har opnået sine begrænsninger.
     */
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

                        LocalDateTime date = currentShift.getStartTime();
                        LocalDateTime periodStart = currentTI.getStartTime();
                        LocalDateTime periodEnd = new LocalDateTime(periodStart);
                        periodEnd = periodEnd.plus(currentTI.getHours());
                        periodEnd = periodEnd.plus(currentTI.getMinutes());
                        if (Xray.getInstance().isDateInPeriod(date, periodStart, periodEnd)) {
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
     * Tæller counters op på den employee som currentShift refererer til.
     *
     * @param rooms
     * @param currentShift
     * @param timeInvestments
     * @throws DatabaseException
     */
    public void countDateAssignmentsOfRoomForEmp(ArrayList<Room> rooms, TimeInvestment currentShift,
            ArrayList<TimeInvestment> timeInvestments) throws DatabaseException {

        Employee employee = currentShift.getEmployee();

        //Opret roomAssignment counters på currentShift's employee.
        createCounters(employee);

        ArrayList<TimeInvestment> empTimeInvestments
                = getEmpTimeInvestments(timeInvestments, currentShift);

        if (timeInvestments.size() > 0) {
            for (int i = 0; i < empTimeInvestments.size(); i++) {
                String roomName = empTimeInvestments.get(i).getRoom().getRoomName();

                //Lineær søgning. Hvis det givne rum findes så skal dets tæller,
                //i den passende roomCounter på employee tælle én op.
                for (int j = 0; j < rooms.size(); j++) {
                    if (roomName.equals(rooms.get(j).getRoomName())) {
                        employee.increment(rooms.get(j));
                        break; //break OK i søgning for optimering.
                    }
                }
            }

        }
    }

    /**
     * Returnerer en liste af vagter som hører til currentShift's employee, og
     * som er i samme tidsperiode som currentShift.
     *
     * @param timeInvestments den totale mængde af tidsinvesteringer.
     * @param currentShift den nuværende vagt.
     * @return
     */
    public ArrayList<TimeInvestment> getEmpTimeInvestments(ArrayList<TimeInvestment> timeInvestments,
            TimeInvestment currentShift) {
        ArrayList<TimeInvestment> employeeShifts = new ArrayList<>();

        for (TimeInvestment timeInvestment : timeInvestments) {
            if (timeInvestment.getEmployee().getId() == currentShift.getEmployee().getId()) {
                employeeShifts.add(timeInvestment);
            }
        }

        return employeeShifts;
    }

    /**
     * Tilføjer en liste af roomAssignmentCounters til den givne employee's
     * counters felt.
     *
     * @param employee employee hvis liste af counters skal opdateres.
     * @throws DatabaseException
     */
    public void createCounters(Employee employee) throws DatabaseException {

        ArrayList<Room> rooms;

        rooms = Xray.getInstance().getRoomControl().getRooms();

        employee.getCounters().clear();
        for (Room room : rooms) {
            employee.addCounter(new RoomAssignmentCounter(room, 0));
        }
    }

    public Room getRoomMinMaxCompared(ArrayList<Room> rooms, TimeInvestment currentShift) {
        Room prioritizedRoom = null;
        TimePeriodControl timePeriodControl = Xray.getInstance().getTimePeriodControl();

        //Hent liste af tidsperioder som currentShift bliver påvirket af.
        ArrayList<TimePeriod> timePeriods = timePeriodControl.getTimeperiods(currentShift);

        //Initialiser en liste over rum, som skal indeholde alle de rum der har en
        //tidsperiode-begrænsning.
        ArrayList<Room> roomsWithConstraints = new ArrayList<>();

        //Initialiser en liste over rum, som skal indeholde alle de rum der har nået 
        //sine minimummer.
        ArrayList<Room> tempRoomsMinReached = new ArrayList<>();

        for (int i = 0; i < rooms.size(); i++) {
            Room currentRoom = rooms.get(i);
            if (!rooms.isEmpty()) {
                if (timePeriods.size() > 0) {
                    if (timePeriodControl.hasPeriodConstraint(timePeriods, currentRoom)) {
                        roomsWithConstraints.add(currentRoom);

                        rooms.remove(i);

                        i--;
                    } else {
                        if (currentRoom.getMinOccupation() <= currentRoom.getCount()) {
                            //Kontroller at det nuværende rum har nået sit minimum, tilføj det
                            //til tempRoomsMinReached og fjern det fra rooms.
                            tempRoomsMinReached.add(currentRoom);
                            rooms.remove(i);

                            i--;
                        }
                    }
                }
            }
        }

        boolean isMinReached = true;
        boolean isMaxReached = false;

        ArrayList<TimePeriod> periodsForEmp = timePeriodControl.
                getTimePeriodsForEmp(timePeriods, currentShift);

        for (int i = 0; i < periodsForEmp.size(); i++) {
            TimePeriod period = periodsForEmp.get(i);
            //rsc giver mulighed for at vide hvor mange gange currentShift's
            //person er blevet tildelt det rum, iterationen er nået til.
            RoomAssignmentCounter rsc = currentShift.getEmployee().
                    getRoomAssignmentCounter(period.getRoom());

            //Fjern fra listen hvis maximummet er opnået.
            if (period.getMax() <= rsc.getCount()) {
                periodsForEmp.remove(period);
            }

        }
        ArrayList<RoomAssignmentCounter> racs = currentShift.getEmployee().getCounters();

        //Hvis der er rum med begrænsninger i form af timePeriods så gå dem igennem.
        if (!periodsForEmp.isEmpty()) {
            //Sorter listen af tidsperioder, sådan at det vigtigste rum refererer
            //den første tidsperiode i listen.
            periodsForEmp.sort(new PeriodMinComparator(currentShift.getEmployee()));
            prioritizedRoom = periodsForEmp.get(0).getRoom();

            isMinReached = isMinReached(periodsForEmp, currentShift);
            isMaxReached(periodsForEmp, currentShift);
        }

        if (isMinReached && !isMaxReached) {
            if (!rooms.isEmpty()) {
                rooms.sort(new RoomMinimumComparator(currentShift.getEmployee()));

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
        }

        return prioritizedRoom;
    }

    public boolean isMinReached(ArrayList<TimePeriod> timePeriods, TimeInvestment currentShift) {
        boolean minReached = true;
        for (TimePeriod timePeriod : timePeriods) {
            Employee thisEmp = currentShift.getEmployee();
            RoomAssignmentCounter thisRac = thisEmp.getRoomAssignmentCounter(timePeriod.getRoom());
            if (timePeriod.getMin() > thisRac.getCount()) {
                minReached = false;
            }
        }
        return minReached;
    }

    public boolean isMaxReached(ArrayList<TimePeriod> timePeriods, TimeInvestment currentShift) {
        boolean maxReached = false;
        for (TimePeriod timePeriod : timePeriods) {
            Employee thisEmp = currentShift.getEmployee();
            RoomAssignmentCounter thisRac = thisEmp.getRoomAssignmentCounter(timePeriod.getRoom());
            if (timePeriod.getMax() <= thisRac.getCount()) {
                maxReached = true;
            }
        }
        return maxReached;
    }

    /**
     * Metode til at finde vagter på en given dato og i en given tidsperiode, i
     * en given liste.
     *
     * @param shifts liste af vagter der skal søges i.
     * @param periodStart
     * @param periodEnd
     * @return en liste af alle vagter i en given tidsperiode på en given dato.
     */
    public ArrayList<TimeInvestment> getShiftsInPeriod(
            ArrayList<TimeInvestment> shifts, LocalDateTime periodStart,
            LocalDateTime periodEnd) {
        ArrayList<TimeInvestment> shiftsInPeriod = new ArrayList<>();

        for (int j = 0; j < shifts.size(); j++) {
            boolean isInPeriod = Xray.getInstance().isDateInPeriod(shifts.get(j).getStartTime(),
                    periodStart, periodEnd);
            if (isInPeriod) {
                shiftsInPeriod.add(shifts.get(j));
            }
        }

        return shiftsInPeriod;
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
    
    public ArrayList<TimeInvestment> getAssignedTimeInvestments() throws DatabaseException{
        return TimeInvestmentHandler.getInstance().getAssignedTimeInvestments();
    }

}
