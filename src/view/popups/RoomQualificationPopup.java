/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import model.Employee;
import model.Room;
import view.buttons.AddButton;
import view.buttons.AddButtonV2;
import view.buttons.PopupMenuButton;
import view.buttons.SettingsButton;

/**
 *
 * @author Jonas
 */
public class RoomQualificationPopup extends PopupWindow{
    private ComboBox cBRoomBox, cBEmployeeBox;
    private ArrayList<Room> rooms, roomsInsert;
    private ArrayList<Employee> employees, employeesInsert;
    private ObservableList<Room> observableListRooms;
    private ObservableList<Employee> observableListEmployees;
    private TextField tFType;
    private String type, typeInsert;
    private ListView listViewRoom, listViewEmployee;
    private SettingsButton settingsButton;
    private AddButtonV2 addButtonRoom, addButtonEmployee;
    private PopupMenuButton addRoom;
    private Label lRoomLabel, lEmployeeLabel, lType, 
            lRoomListView, lEmployeeListView;
    private Room roomInsert;
    private Employee employeeInsert;
    
    
    public RoomQualificationPopup(ArrayList<Room> rooms, ArrayList<Employee> employees) {
        this.rooms = rooms;
        this.employees = employees;
    }
    
    @Override
    public void display(String title) {
        ExceptionPopup exceptionPopup = new ExceptionPopup(); //Til exceptionhandling.
        
        lType = new Label("Skriv navn her");
        lEmployeeLabel = new Label("Vælg ansat der skal bearbejdes");
        lRoomLabel = new Label("Vælg rum der skal bearbejdes");
        lRoomListView = new Label("Her lægges alle rum:");
        lEmployeeListView = new Label("Her lægges alle ansatte:");
        
        tFType = new TextField();
        
        cBRoomBox = new ComboBox();
        cBEmployeeBox = new ComboBox();
        
        // Der skal lægges elementer ind i vores combobokse.
        ObservableList<Room> observableListRooms = 
                FXCollections.observableArrayList();
        
        for (int i = 0; i < rooms.size(); i++) {
            cBRoomBox.getItems().add(rooms.get(i));
        }
        
        //cBRoomBox.setValue(observableRooms.get(0));
        
       //Nu er det employee comboboksens tur
        ObservableList<Employee> observableListEmployees = 
                FXCollections.observableArrayList();
        
        for (int i = 0; i < employees.size(); i++) {
            cBEmployeeBox.getItems().add(employees.get(i));
        }
        
        
        //cBEmployeeBox.setValue(observableEmployees.get(0));
        
        lType.setTextAlignment(TextAlignment.CENTER);
        
        ListView<Room> listViewRoom = new ListView();
        ListView<Employee> listViewEmployee = new ListView();
        
        setBottomHBoxPadding(15, 15, 15, 15);
        settingsButton = new SettingsButton();
        addButtonRoom = new AddButtonV2("Tilføj rum");
        addButtonEmployee = new AddButtonV2("Tilføj ansat");
        VBox vBoxLeft = new VBox(20);
        VBox vBoxRight = new VBox(20);
        VBox vBoxCenter = new VBox(20);
        vBoxLeft.setAlignment(Pos.CENTER);
        vBoxRight.setAlignment(Pos.CENTER);
        vBoxCenter.setAlignment(Pos.CENTER);
        vBoxLeft.setPadding(new Insets(15, 15, 15, 7.5));
        vBoxRight.setPadding(new Insets(15, 7.5, 15, 15));
        vBoxCenter.setPadding(new Insets(15, 7.5, 15, 7.5));
        super.addToLeft(vBoxLeft);
        super.addToRight(vBoxRight);
        super.addToCenter(vBoxCenter);
        
        vBoxLeft.getChildren().addAll(
                lType, tFType, 
                lEmployeeLabel, cBEmployeeBox,
                lRoomLabel, cBRoomBox);
        
        vBoxRight.getChildren().addAll(lEmployeeListView, listViewEmployee ,
                lRoomListView, listViewRoom);
        vBoxCenter.getChildren().addAll(addButtonEmployee, settingsButton, addButtonRoom);
        
        addButtonRoom.setOnAction(e -> {
            roomInsert = (Room) cBRoomBox.getSelectionModel().getSelectedItem();
            observableListRooms.add(roomInsert);
            listViewRoom.setItems(observableListRooms);
        });
        
        addButtonEmployee.setOnAction(e -> {
            employeeInsert = (Employee) cBEmployeeBox.getSelectionModel().getSelectedItem();
            observableListEmployees.add(employeeInsert);
            listViewEmployee.setItems(observableListEmployees);
        });
        
        settingsButton.setOnAction(e -> {
            int indexRoom = listViewRoom.getSelectionModel().getSelectedIndex();
            int indexEmployee = listViewEmployee.getSelectionModel().getSelectedIndex();
            if (listViewRoom.isFocused()) {
                observableListRooms.remove(indexRoom);
                listViewRoom.setItems(observableListRooms);
            } else if (listViewEmployee.isFocused()) {
                observableListEmployees.remove(indexEmployee);
                listViewEmployee.setItems(observableListEmployees);
            }
        });
        
        addRoom = new PopupMenuButton("Tilføj kvalifikationer"); // Her skal listen køres igennem og der indsættes data i databasen
        addRoom.setOnAction(e -> {
            typeInsert = tFType.getText();
            
            
        });
        
        super.addToBottomHBox(addRoom);
        super.display(title);
        
        
        
        
    }
}
