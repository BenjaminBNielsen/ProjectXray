/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package technicalServices.persistence;


import dbc.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import model.Employee;
import model.Qualification;
import model.Room;
import model.RoomQualification;
import model.LimitQualification;

/**
 *
 * @author Yousef
 */
public class QualificationHandler {

    private static QualificationHandler instance;

    private QualificationHandler() {
    }

    public ArrayList<RoomQualification> getRoomQualificationsFromRoom(Room room) throws ClassNotFoundException {
        ArrayList<RoomQualification> roomQualifications = new ArrayList<>();
        try {
            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();


            String roomName = room.getRoomName();
            String SQL = "Select * from qualification where roomId = " + roomName;
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("id");
                Boolean training = rs.getBoolean("training");
                int employeeId = rs.getInt("employeeid");
                Employee employee = EmployeeHandler.getInstance().getEmployee(employeeId);
                

                //roomQualifications.add(new RoomQualification(id, training, employee, room));
            }

            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        }

        return roomQualifications;
    }
    
     public ArrayList<RoomQualification> getRoomQualifications() {
        ArrayList<RoomQualification> roomQualifications = new ArrayList<>();
        try {
            Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String SQL = "select * from roomQualification, qualification where "
                    + "roomQualification.id = qualification.id;";
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("qualification.id");
                String type = rs.getString("type");
                
                Statement stmtEmployees = DatabaseConnection.getInstance().getConnection().createStatement();
                String sql = "select * from qualToEmp where qualId = " + id;
                ArrayList<Employee> qualEmployees = new ArrayList<>();
                
                ResultSet rsEmployees = stmtEmployees.executeQuery(sql);
                
                while(rsEmployees.next()){
                    int empId = rsEmployees.getInt("employeeNr");
                    qualEmployees.add(EmployeeHandler.getInstance().getEmployee(empId));
                }
                
                stmtEmployees.close();
                rsEmployees.close();
                
                Statement stmtRooms = DatabaseConnection.getInstance().getConnection().createStatement();
                sql = "select * from qualToRoom where qualId = " + id;
                ArrayList<Room> qualRooms = new ArrayList<>();
                
                ResultSet rsRooms = stmtRooms.executeQuery(sql);
                
                while(rsRooms.next()){
                    String roomName = rsRooms.getString("roomName");
                    qualRooms.add(RoomHandler.getInstance().getRoom(roomName));
                }
 
                stmtRooms.close();
                rsRooms.close();
                
                roomQualifications.add(new RoomQualification(id, type, qualEmployees, qualRooms));
            }

            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        } catch(ClassNotFoundException ex){
            System.out.println("DET VIRKER IKKE DET LOOORT");
        } catch(Exception ex){
            System.out.println("LORTET VIRKER STADIG IKKE");
        }

        return roomQualifications;
    }

    public ArrayList<LimitQualification> getLimitQualifications() throws ClassNotFoundException {
        ArrayList<LimitQualification> limitQualifications = new ArrayList<>();
        try {
            Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();

            String SQL = "select * from limitQualification, qualification where "
                    + "limitQualification.id = qualification.id;";
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("qualification.id");
                String type = rs.getString("type");
                int limit = rs.getInt("limit");
                
                Statement stmtEmployees = DatabaseConnection.getInstance().getConnection().createStatement();
                String sql = "select * from qualToEmp where qualId = " + id;
                ArrayList<Employee> qualEmployees = new ArrayList<>();
                
                ResultSet rsEmployees = stmtEmployees.executeQuery(sql);
                
                while(rsEmployees.next()){
                    int empId = rsEmployees.getInt("employeeNr");
                    qualEmployees.add(EmployeeHandler.getInstance().getEmployee(empId));
                }
                
                stmtEmployees.close();
                rsEmployees.close();
                
                Statement stmtRooms = DatabaseConnection.getInstance().getConnection().createStatement();
                sql = "select * from qualToRoom where qualId = " + id;
                ArrayList<Room> qualRooms = new ArrayList<>();
                
                ResultSet rsRooms = stmtRooms.executeQuery(sql);
                
                while(rsRooms.next()){
                    String roomName = rsRooms.getString("roomName");
                    qualRooms.add(RoomHandler.getInstance().getRoom(roomName));
                }
                
                stmtRooms.close();
                rsRooms.close();
                
                limitQualifications.add(new LimitQualification(id, type, qualEmployees, qualRooms, limit));
            }

            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("SQL Fejl: " + ex.getMessage());

        } catch(ClassNotFoundException ex){
            System.out.println("DET VIRKER IKKE DET LOOORT");
        } catch(Exception ex){
            System.out.println("LORTET VIRKER STADIG IKKE");
        }

        return limitQualifications;
    }

//    public ArrayList<Qualification> getQualificationsForSeveralEmployees(ArrayList<Employee> employees) throws ClassNotFoundException {
//        ArrayList<Qualification> employeesQualifications = new ArrayList<>();
//        for (int i = 0; i < employees.size(); i++) {
//            Employee selectedEmployees = employees.get(i);
//            employeesQualifications.add(getEmployeeQualifications(selectedEmployees).get(i));
//        }
//        return employeesQualifications;
//    }

//    public void setEmployeeQualifications(ArrayList<Qualification> selectedQualifications) {
//        for (int i = 0; i < selectedQualifications.size(); i++) {
//            Qualification selectedQualification = selectedQualifications.get(i);
//            Boolean training = selectedQualifications.get(i).isTraining();
//            Employee selectedEmployee = selectedQualifications.get(i).getEmployee();
//
//            setEmployeeQualification(selectedQualification, training, selectedEmployee);
//        }
//    }

//    public void setEmployeeQualification(Qualification qualification, Boolean training, Employee employee) {
//        try {
//            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
//
//            QualificationType type = qualification.getId();
//            int id = employee.getId();
//
//            String SQL = "insert into qualification(type, training, employeeid) values (";
//            SQL += type + "," + training + "," + id + ")";
//
//            stmt.execute(SQL);
//            stmt.close();
//        } catch (SQLException ex) {
//            System.out.println("SQL FEJL: " + ex.getMessage());
//        }
//
//    }

//    public void createQualification(QualificationType type, Boolean training, Room room) {
//        try {
//            java.sql.Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();
//
//            String SQL = "insert into qualification(qualiTypeId, training, roomId) values (";
//            SQL += type + "," + training + "," + room +")";
//
//            stmt.execute(SQL);
//            stmt.close();
//        } catch (SQLException ex) {
//            System.out.println("SQL FEJL: " + ex.getMessage());
//        }
//
//    }

    public static QualificationHandler getInstance() {
        if (instance == null) {
            instance = new QualificationHandler();
        }
        return instance;
    }

}
