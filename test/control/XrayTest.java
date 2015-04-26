/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import model.Employee;
import model.LimitQualification;
import model.Occupation;
import model.Room;
import model.RoomQualification;
import model.TimeInvestment;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Benjamin
 */
public class XrayTest {

    public XrayTest() {
    }

    @Test
    public void testTwoShiftsOnOneDay() {
        ArrayList<Employee> employees = new ArrayList<>();
        ArrayList<TimeInvestment> shifts = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<RoomQualification> roomQualifications = new ArrayList<>();
        ArrayList<LimitQualification> limitQualifications = new ArrayList<>();

        //Tilføj employees
        Occupation o1 = new Occupation(1, "Negerkriger");
        employees.add(new Employee("Bente", "Knudsen", 1, 22334455, "earweraewr", "eawrew", o1));
        employees.add(new Employee("Anni", "Knudsen5", 2, 22334455, "earweraewr", "eawrew", o1));

        //Tildel vagter
        shifts.add(new TimeInvestment(Hours.FIVE, Minutes.ZERO, new LocalDateTime(2010, 9, 5, 0, 0), employees.get(0), null));
        shifts.add(new TimeInvestment(Hours.FIVE, Minutes.ZERO, new LocalDateTime(2010, 9, 5, 0, 0), employees.get(1), null));

        //Tilføj rum
        int roomState = 1;
        int maxEmps = 3;
        int maxStud = 2;
        Room ultraSound = new Room("Ultralyd", roomState, maxEmps, maxStud);

        rooms.add(ultraSound);

        String ultraSoundType = "ULTRA F***** LYD";
        String pvk = "PVK - indsprøjtning";

        //Ultralyd qual def.
        ArrayList<Employee> ultraSoundEmps = new ArrayList<>();
        ultraSoundEmps.add(employees.get(0));
        ultraSoundEmps.add(employees.get(1));
        ArrayList<Room> ultraSoundRooms = new ArrayList<>();
        ultraSoundRooms.add(ultraSound);
        RoomQualification qUltraSound = new RoomQualification(1, false, ultraSoundType, ultraSoundEmps, ultraSoundRooms);

        roomQualifications.add(qUltraSound);

        //PVK def.
        ArrayList<Employee> pvkEmps = new ArrayList<>();
        pvkEmps.add(employees.get(1));
        ArrayList<Room> pvkRooms = new ArrayList<>();
        pvkRooms.add(ultraSound);

        LimitQualification qPvk = new LimitQualification(20, false, pvk, pvkEmps, pvkRooms, 1);

        limitQualifications.add(qPvk);

        ArrayList<TimeInvestment> actualTI = Xray.getInstance().
                assignRooms(shifts, roomQualifications, new ArrayList<LimitQualification>());

        boolean hasFailedExpected = false;
        boolean hasFailedActual = false;
        String errorMessage = "";
        if (!actualTI.get(0).getHours().equals(actualTI.get(1).getHours())) {
            hasFailedActual = true;
            errorMessage = "Timerne på de to shifts stemte ikke over ens: ["
                    + actualTI.get(0).getHours() + "]!=[" + actualTI.get(1).getHours() + "]";
        } else if (!actualTI.get(0).getMinutes().equals(actualTI.get(1).getMinutes())) {
            hasFailedActual = true;
            errorMessage = "Minutterne på de to shifts stemte ikke over ens: ["
                    + actualTI.get(0).getMinutes() + "]!=[" + actualTI.get(1).getMinutes() + "]";
        } else if (!actualTI.get(0).getStartTime().equals(actualTI.get(1).getStartTime())) {
            hasFailedActual = true;
            errorMessage = "Datoerne stemte ikke over ens: ["
                    + actualTI.get(0).getStartTime() + "]!=[" + actualTI.get(1).getStartTime() + "]";
        }
        assertEquals(errorMessage, hasFailedExpected, hasFailedActual);
    }

    @Test
    public void testEmployeeLimitRoomsGetsRoomWithLimit() {
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<LimitQualification> limitQualifications = new ArrayList<>();
        ArrayList<Employee> employees = new ArrayList<>();

        Occupation o1 = new Occupation(1, "Radiograf");
        employees.add(new Employee("Bente", "Knudsen", 1, 22334455, "earweraewr", "eawrew", o1));

        //Tilføj rum
        int roomState = 1;
        int maxEmps = 3;
        int maxStud = 2;
        Room ultraSound = new Room("Ultralyd", roomState, maxEmps, maxStud);
        Room ctA = new Room("CT A", roomState, maxEmps, maxStud);
        Room ctB = new Room("CT B", roomState, maxEmps, maxStud);

        rooms.add(ultraSound);
        rooms.add(ctA);
        rooms.add(ctB);

        String pvk = "PVK - indsprøjtning";

        //PVK def.
        ArrayList<Employee> pvkEmps = new ArrayList<>();
        pvkEmps.add(employees.get(0));
        ArrayList<Room> pvkRooms = new ArrayList<>();
        pvkRooms.add(ultraSound);
        pvkRooms.add(ctA);

        LimitQualification qPvk = new LimitQualification(20, false, pvk, pvkEmps, pvkRooms, 1);

        limitQualifications.add(qPvk);

        //
        ArrayList<Room> limitRooms = Xray.getInstance().getEmployeeLimitRooms(pvkEmps.get(0), limitQualifications);

        boolean hasFailedActual = false;
        boolean hasFailedExpected = false;
        String errorMessage = "";

        if (limitRooms.size() != 2) {
            hasFailedActual = true;
            errorMessage = "Størrelsen på limitRooms svare ikke til den forventede "
                    + "{" + 2 + "} != {" + limitRooms.size() + "}";
        }
        for (int i = 0; i < limitRooms.size(); i++) {
            if (!limitRooms.get(i).getRoomName().equals(pvkRooms.get(i).getRoomName())) {
                hasFailedActual = true;
                errorMessage = "Navnet på rummet på plads " + i + " svarede ikke til det forventede "
                        + "{" + pvkRooms.get(i).getRoomName() + "} != {" + limitRooms.get(i).getRoomName() + "}";
            }

        }
        assertEquals(errorMessage, hasFailedExpected, hasFailedActual);
    }

    @Test
    public void testThatRoomMinimumGetsPriority() {
        ArrayList<Employee> employees = new ArrayList<>();
        ArrayList<TimeInvestment> shifts = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<RoomQualification> roomQualifications = new ArrayList<>();

        //Tilføj employees
        Occupation o1 = new Occupation(1, "Radiograf");
        employees.add(new Employee("Lillian", "Klenz Larsen", 1, 22334455, "earweraewr", "eawrew", o1));
        employees.add(new Employee("Stine", "Louise Jensen", 2, 22334455, "earweraewr", "eawrew", o1));
        employees.add(new Employee("Bente", "Jakobsen", 3, 22334455, "earweraewr", "eawrew", o1));

        //Tildel vagter
        shifts.add(new TimeInvestment(Hours.EIGHT, Minutes.ZERO, new LocalDateTime(2010, 9, 5, 0, 0), employees.get(0), null));
        shifts.add(new TimeInvestment(Hours.EIGHT, Minutes.ZERO, new LocalDateTime(2010, 9, 5, 0, 0), employees.get(1), null));
        shifts.add(new TimeInvestment(Hours.EIGHT, Minutes.ZERO, new LocalDateTime(2010, 9, 5, 0, 0), employees.get(2), null));

        //Tilføj rum
        int roomState = 1;
        int maxEmps = 3;
        Room ultraSound = new Room("Ultralyd", roomState, maxEmps, 2);
        Room ctA = new Room("CT A", roomState, maxEmps, 1);

        rooms.add(ultraSound);
        rooms.add(ctA);

        String ultraSoundType = "Universel kvalifikation";

        //Ultralyd qual def.
        ArrayList<Employee> universalQual = new ArrayList<>();
        universalQual.add(employees.get(0));
        universalQual.add(employees.get(1));
        universalQual.add(employees.get(2));
        ArrayList<Room> ultraSoundRooms = new ArrayList<>();
        ultraSoundRooms.add(ultraSound);
        ultraSoundRooms.add(ctA);
        RoomQualification qUltraSound = new RoomQualification(1, false, ultraSoundType, universalQual, ultraSoundRooms);

        roomQualifications.add(qUltraSound);

        ArrayList<TimeInvestment> actualTIs = Xray.getInstance().assignRooms
        (shifts, roomQualifications,new ArrayList<LimitQualification>());

        String expectedFirstRoomName = "Ultralyd";
        String actualFirstRoomName = actualTIs.get(0).getRoom().getRoomName();
        String errorMessage = "Det tildelte rum passede ikke til det forventede";

        assertEquals(errorMessage, expectedFirstRoomName, actualFirstRoomName);

        String expectedSecondRoomName = "CT A";
        String actualSecondRoomName = actualTIs.get(1).getRoom().getRoomName();
        errorMessage = "Det tildelte rum passede ikke til det forventede";

        assertEquals(errorMessage, expectedSecondRoomName, actualSecondRoomName);

        String expectedThirdRoomName = "Ultralyd";
        String actualThirdRoomName = actualTIs.get(2).getRoom().getRoomName();
        errorMessage = "Det tildelte rum passede ikke til det forventede";

        assertEquals(errorMessage, expectedThirdRoomName, actualThirdRoomName);

    }

    @Test
    public void assignedRoomsCantExceedMaximum() {
        ArrayList<Employee> employees = new ArrayList<>();
        ArrayList<TimeInvestment> shifts = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<RoomQualification> roomQualifications = new ArrayList<>();

        //Tilføj employees
        Occupation o1 = new Occupation(1, "Radiograf");
        employees.add(new Employee("Lillian", "Klenz Larsen", 1, 22334455, "earweraewr", "eawrew", o1));

        //Tildel vagter
        shifts.add(new TimeInvestment(Hours.EIGHT, Minutes.ZERO, new LocalDateTime(2010, 9, 5, 0, 0), employees.get(0), null));
        shifts.add(new TimeInvestment(Hours.EIGHT, Minutes.ZERO, new LocalDateTime(2010, 9, 5, 0, 0), employees.get(0), null));
        shifts.add(new TimeInvestment(Hours.EIGHT, Minutes.ZERO, new LocalDateTime(2010, 9, 5, 0, 0), employees.get(0), null));

        //Tilføj rum
        int roomState = 1;
        Room ultraSound = new Room("Ultralyd", roomState, 2, 0);

        rooms.add(ultraSound);

        String ultraSoundType = "Universel kvalifikation";

        //Ultralyd qual def.
        ArrayList<Employee> universalQual = new ArrayList<>();
        universalQual.add(employees.get(0));
        ArrayList<Room> ultraSoundRooms = new ArrayList<>();
        ultraSoundRooms.add(ultraSound);
        RoomQualification qUltraSound = new RoomQualification(1, false, ultraSoundType, universalQual, ultraSoundRooms);

        roomQualifications.add(qUltraSound);

        ArrayList<TimeInvestment> actualTIs = Xray.getInstance().assignRooms
        (shifts, roomQualifications, new ArrayList<LimitQualification>());
        
        int expectedSize = 2;
        int actualSize = actualTIs.size();
        String errorMessage = "Størrelsen på listen med timeInvestments var ikke"
                + " som forventet.";

        assertEquals(errorMessage, expectedSize, actualSize);
    }
    
    @Test
    public void testCountDateAssignmentsOfRoomIncrementsRight() {
        ArrayList<Employee> employees = new ArrayList<>();
        ArrayList<TimeInvestment> shifts = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();

        //Tilføj employees
        Occupation o1 = new Occupation(1, "Radiograf");
        employees.add(new Employee("Lillian", "Klenz Larsen", 1, 22334455, "earweraewr", "eawrew", o1));

        //Tilføj rum
        int roomState = 1;
        Room ultraSound = new Room("Ultralyd", roomState, 2, 0);

        rooms.add(ultraSound);

        //Tildel vagter
        shifts.add(new TimeInvestment(Hours.EIGHT, Minutes.ZERO, new LocalDateTime(2010, 9, 5, 0, 0), employees.get(0), rooms.get(0)));
        shifts.add(new TimeInvestment(Hours.EIGHT, Minutes.ZERO, new LocalDateTime(2010, 9, 5, 0, 0), employees.get(0), rooms.get(0)));
        shifts.add(new TimeInvestment(Hours.EIGHT, Minutes.ZERO, new LocalDateTime(2010, 9, 5, 10, 0), employees.get(0), rooms.get(0)));

        Xray.getInstance().countDateAssignmentsOfRoom(rooms, shifts.get(0), shifts);

        int expectedCount = 2;
        int actualCount = rooms.get(0).getCount();
        String errorMessage = "Det antal af rum-tildelinger på ultralyd-rummet "
                + "som metoden kom frem til, svarede ikke til det forventede: ["
                + expectedCount + "]!=[" + actualCount + "]";

        assertEquals(errorMessage, expectedCount, actualCount);
    }
    
    @Test //Jonas
    public void testIsAllreadyInList() {
        //Tester at den kan finde ud af om et rum allerede ligger i en arrayliste.
        boolean resultActual = false;
        boolean resultExpected = true;
        Room testRoom = new Room("Hej123", 1, 2, 3);
        ArrayList<Room> testRooms = new ArrayList<>();
        testRooms.add(testRoom);
        resultActual = Xray.getInstance().isAllreadyInList(testRoom, testRooms);
        assertEquals(resultExpected, resultActual);
    }
    
    @Test //Jonas
    public void testGetRoomMinMaxComparedPrioritizeLeastCount() {
        //Tester at den kan prioritere efter det laveste count.
        Room testRoom1 = new Room("TestRoom1", 1, 2, 3);
        Room testRoom2 = new Room("TestRoom2", 1, 2, 3);
        String prioritizedRoomActual = null;
        String prioritizedRoomExpected = testRoom2.getRoomName();
        ArrayList<Room> testRooms1 = new ArrayList<>();
        testRoom1.setCount(1);
        testRooms1.add(testRoom1); 
        testRooms1.add(testRoom2);
        prioritizedRoomActual = Xray.getInstance().getRoomMinMaxCompared(testRooms1).getRoomName();
        assertEquals(prioritizedRoomExpected, prioritizedRoomActual);
    }
    
    @Test //Jonas
    public void testGetRoomMinMaxComparedPrioritizeMinOccupation() {
        //Tester at den kan priotere efter minimums occupation.
        Room testRoom1 = new Room("TestRoom1", 1, 2, 3);
        Room testRoom2 = new Room("TestRoom2", 1, 3, 4);
        String prioritizedRoomActual = null;
        String prioritizedRoomExpected = testRoom2.getRoomName();
        ArrayList<Room> testRooms1 = new ArrayList<>();
        testRoom1.setCount(2);
        testRoom2.setCount(2);
        testRooms1.add(testRoom1); 
        testRooms1.add(testRoom2);
        prioritizedRoomActual = Xray.getInstance().getRoomMinMaxCompared(testRooms1).getRoomName();
        assertEquals(prioritizedRoomExpected, prioritizedRoomActual);
    }
    
    @Test //Jonas
    public void testGetRoomMinMaxComparedPrioritizeMaxOccupation() {
        //Tester at den kan angive prioritized room til det rum med størst forskel
        //på maksimum og count efter minimum er overholdt.
        Room testRoom1 = new Room("TestRoom1", 1, 2, 3);
        Room testRoom2 = new Room("TestRoom2", 1, 2, 5);
        String prioritizedRoomActual = null;
        String prioritizedRoomExpected = testRoom2.getRoomName();
        ArrayList<Room> testRooms1 = new ArrayList<>();
        testRoom1.setCount(2);
        testRoom2.setCount(2);
        testRooms1.add(testRoom1); 
        testRooms1.add(testRoom2);
        prioritizedRoomActual = Xray.getInstance().getRoomMinMaxCompared(testRooms1).getRoomName();
        assertEquals(prioritizedRoomExpected, prioritizedRoomActual);
    }
    
     @Test
    public void getEmployeeRoomsReturnsQualifiedRooms() {
        ArrayList<Employee> employee = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<RoomQualification> roomQualifications = new ArrayList<>();

        //Tilføj occupation
        Occupation o1 = new Occupation(1, "Radiograf");
        //Tilføj employee 
        Employee e1 = new Employee("Klaus", "Larsen", 1, 22334455, "femøvej 5", "eaw@ff.dk", o1);
        employee.add(e1);

        //Tilføj rum
        int roomState = 1;
        Room knoglerum = new Room("Knoglerum", roomState, 2, 0);
        rooms.add(knoglerum);

        String boneType = "Universel kvalifikation";

        //Ultralyd qual def.
        ArrayList<Employee> universalQual = new ArrayList<>();
        universalQual.add(employee.get(0));
        ArrayList<Room> knogleRooms = new ArrayList<>();
        knogleRooms.add(knoglerum);
        RoomQualification qBone = new RoomQualification(1, false, boneType, universalQual, knogleRooms);

        roomQualifications.add(qBone);

        String expectedRoomName = "Knoglerum";
        String actualRoomName = Xray.getInstance().getEmployeeRooms(e1, roomQualifications).get(0).getRoomName();
        String errorMessage = "Det var ikke det forventede rum, som blev fundet som resultat";

        assertEquals(errorMessage, expectedRoomName, actualRoomName);
    }
    
}
