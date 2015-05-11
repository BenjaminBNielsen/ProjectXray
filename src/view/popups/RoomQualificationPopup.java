/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups;

import control.Xray;
import exceptions.DatabaseException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.RadialGradient;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import model.Employee;
import model.Room;
import org.joda.time.LocalDateTime;
import view.buttons.AddButton;
import view.buttons.AddButtonV2;
import view.buttons.PopupMenuButton;
import view.buttons.SettingsButton;
import view.schema.ScheduleHeader;

/**
 *
 * @author Jonas
 */
public class RoomQualificationPopup extends PopupWindow {

    private ComboBox cBRoomBox, cBEmployeeBox, cBStartDate, cBEndDate;
    private ArrayList<Room> rooms, roomsInsert;
    private ArrayList<Employee> employees, employeesInsert;
    private ArrayList<LocalDateTime> dateTimeListStart, dateTimeListEnd;
    private ObservableList<Room> observableListRooms;
    private ObservableList<Employee> observableListEmployees;
    private LocalDateTime localDateTimeObject, localDateStart, localDateEnd;
    private TextField tFType, tFLimit;
    private String type, typeInsert;
    private ListView listViewRoom, listViewEmployee;
    private SettingsButton settingsButton;
    private AddButtonV2 addButtonRoom, addButtonEmployee;
    private PopupMenuButton addQualification;
    private Label lRoomLabel, lEmployeeLabel, lType,
            lRoomListView, lEmployeeListView;
    private Room roomInsert;
    private Employee employeeInsert;
    private RadioButton rButtonLimitqual, rButtonCourse;
    private boolean limitCheck, course;
    private int limit;

    public RoomQualificationPopup(ArrayList<Room> rooms, ArrayList<Employee> employees) {
        this.rooms = rooms;
        this.employees = employees;
    }

    @Override
    public void display(String title) {
        ExceptionPopup exceptionPopup = new ExceptionPopup(); //Til exceptionhandling.

        lType = new Label("Skriv hvilken type kvalifikation det er");
        lEmployeeLabel = new Label("Vælg ansat der skal bearbejdes");
        lRoomLabel = new Label("Vælg rum der skal bearbejdes");
        lRoomListView = new Label("Her lægges alle rum:");
        lEmployeeListView = new Label("Her lægges alle ansatte:");
        localDateTimeObject = LocalDateTime.now();
        tFType = new TextField();
        tFLimit = new TextField();
        tFLimit.setDisable(true);
        cBRoomBox = new ComboBox();
        cBEmployeeBox = new ComboBox();
        cBStartDate = new ComboBox();
        cBStartDate.setDisable(true);
        cBEndDate = new ComboBox();
        cBEndDate.setDisable(true);

        rButtonCourse = new RadioButton("Kursus");
        course = false;
        rButtonCourse.setDisable(true);

        rButtonCourse.setOnAction(e -> {
            if (rButtonCourse.isSelected()) {
                course = true;
                cBStartDate.setDisable(false);
                cBEndDate.setDisable(false);
            } else {
                course = false;
                cBStartDate.setDisable(true);
                cBEndDate.setDisable(true);
            }
        });

        rButtonLimitqual = new RadioButton("Universel kvalifikation");
        limitCheck = false;
        rButtonLimitqual.setOnAction(e -> {

            if (rButtonLimitqual.isSelected()) {
                limitCheck = true;
                tFLimit.setDisable(false);
                rButtonCourse.setDisable(false);
            } else {
                limitCheck = false;
                tFLimit.setDisable(true);
                cBStartDate.setDisable(true);
                cBEndDate.setDisable(true);
                rButtonCourse.setDisable(true);
                rButtonCourse.setSelected(false);
            }
        });

        // Der skal lægges elementer ind i vores combobokse.
        ObservableList<Room> observableListRooms
                = FXCollections.observableArrayList();

        for (int i = 0; i < rooms.size(); i++) {
            cBRoomBox.getItems().add(rooms.get(i));
        }
        cBRoomBox.setValue(rooms.get(0));
        //cBRoomBox.setValue(observableRooms.get(0));

        //Nu er det employee comboboksens tur
        ObservableList<Employee> observableListEmployees
                = FXCollections.observableArrayList();

        for (int i = 0; i < employees.size(); i++) {
            cBEmployeeBox.getItems().add(employees.get(i));
        }
        cBEmployeeBox.setValue(employees.get(0));

        Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>> cellFactory = new Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>>() {
            @Override
            public ListCell<LocalDateTime> call(ListView<LocalDateTime> param) {

                return new ListCell<LocalDateTime>() {
                    @Override
                    public void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {

                            String value = ScheduleHeader.WEEK_DAY_NAMES[item.getDayOfWeek() - 1];
                            value = value.replaceFirst(value.substring(1, value.length()),
                                    value.substring(1, value.length()).toLowerCase());

                            setText("Uge " + item.getWeekOfWeekyear() + " den " + item.getDayOfMonth() + "/" + item.getMonthOfYear() + " - " + value);
                        }
                    }

                };
            }
        };

        cBStartDate.setButtonCell(cellFactory.call(null));
        cBStartDate.setCellFactory(cellFactory);

        Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>> cellFactory2 = new Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>>() {
            @Override
            public ListCell<LocalDateTime> call(ListView<LocalDateTime> param) {

                return new ListCell<LocalDateTime>() {
                    @Override
                    public void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            String value = ScheduleHeader.WEEK_DAY_NAMES[item.getDayOfWeek() - 1];
                            value = value.replaceFirst(value.substring(1, value.length()),
                                    value.substring(1, value.length()).toLowerCase());
                            setText("Uge " + item.getWeekOfWeekyear() + " den " + item.getDayOfMonth() + "/" + item.getMonthOfYear() + " - " + value);
                        }
                    }

                };
            }
        };

        cBEndDate.setButtonCell(cellFactory2.call(null));
        cBEndDate.setCellFactory(cellFactory2);

        dateTimeListStart = new ArrayList<>();
        dateTimeListEnd = new ArrayList<>();

        for (int i = 30; i >= 1; i--) {
            LocalDateTime date = localDateTimeObject.minusDays(i);
            dateTimeListStart.add(date);
        }

        for (int i = 0; i < 30; i++) {
            LocalDateTime date = localDateTimeObject.plusDays(i);
            dateTimeListStart.add(date);
        }

        cBStartDate.getItems().addAll(dateTimeListStart);
        cBStartDate.setValue(dateTimeListStart.get(30));

        for (int i = 0; i < 365; i++) {
            LocalDateTime date = localDateTimeObject.plusDays(i);
            dateTimeListEnd.add(date);
        }

        cBEndDate.getItems().addAll(dateTimeListEnd);
        cBEndDate.setValue(dateTimeListEnd.get(0));

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

        vBoxRight.getChildren().addAll(lEmployeeListView, listViewEmployee,
                lRoomListView, listViewRoom);
        vBoxCenter.getChildren().addAll(addButtonEmployee, settingsButton,
                addButtonRoom, rButtonLimitqual, tFLimit, rButtonCourse,
                cBStartDate, cBEndDate);

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

        //Skal bruge hjælp til at kunne fjerne fokus fra det ene listview når det anden vælges
        settingsButton.setOnAction(e -> {
//            int indexRoom = listViewRoom.getSelectionModel().getSelectedIndex();
//            int indexEmployee = listViewEmployee.getSelectionModel().getSelectedIndex();
//            if (listViewRoom.isFocused()) {
//                observableListRooms.remove(indexRoom);
//                listViewRoom.setItems(observableListRooms);
//            } else if (listViewEmployee.isFocused()) {
//                observableListEmployees.remove(indexEmployee);
//                listViewEmployee.setItems(observableListEmployees);
//            }
            if (listViewEmployee.isFocused()) {
                int index2 = listViewEmployee.getSelectionModel().getSelectedIndex();
                if (!listViewEmployee.getSelectionModel().isEmpty()) {
                    observableListEmployees.remove(index2);
                    listViewEmployee.setItems(observableListEmployees);
                }
            }
            if (listViewRoom.isFocused()) {
                int index = listViewRoom.getSelectionModel().getSelectedIndex();
                if (!listViewRoom.getSelectionModel().isEmpty()) {
                    observableListRooms.remove(index);
                    listViewRoom.setItems(observableListRooms);
                }
            }
        });

        addQualification = new PopupMenuButton("Tilføj kvalifikationer"); // Her skal listen køres igennem og der indsættes data i databasen
        addQualification.setOnAction(e -> {

            if (limitCheck != true) {
                typeInsert = tFType.getText();
                try {
                    Xray.getInstance().getQualificationControl().addRoomQualification(observableListRooms, observableListEmployees, typeInsert);
                } catch (DatabaseException ex) {
                    exceptionPopup.display(ex.getMessage());
                }
            } else if (limitCheck == true) {
                if (course != true) {
                    try {

                        typeInsert = tFType.getText();
                        limit = Integer.parseInt(tFLimit.getText());
                        Xray.getInstance().getQualificationControl().addLimitQualification(observableListRooms, observableListEmployees, typeInsert, limit);
                    } catch (DatabaseException ex) {
                        exceptionPopup.display(ex.getMessage());
                    }
                } else if (course == true) {
                    try {
                        typeInsert = tFType.getText();
                        localDateStart = (LocalDateTime) cBStartDate.getSelectionModel().getSelectedItem();
                        localDateEnd = (LocalDateTime) cBEndDate.getSelectionModel().getSelectedItem();
                        Xray.getInstance().getQualificationControl().addCourseQualification(observableListRooms, observableListEmployees, typeInsert, limit, localDateStart, localDateEnd);
                    } catch (DatabaseException ex) {
                        exceptionPopup.display(ex.getMessage());
                    }
                }
            }
        });

        super.addToBottomHBox(addQualification);
        super.display(title);

    }
}
