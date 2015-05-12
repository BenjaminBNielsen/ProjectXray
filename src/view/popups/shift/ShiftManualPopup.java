/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups.shift;

import control.Xray;
import exceptions.DatabaseException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.Employee;
import model.Room;
import model.TimeInvestment;
import org.joda.time.DateTimeConstants;
import static org.joda.time.DurationFieldType.hours;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import view.buttons.ImageButton;
import view.buttons.PopupMenuButton;
import view.popups.ExceptionPopup;
import view.popups.PopupWindow;

/**
 *
 * @author Yousef
 */
public class ShiftManualPopup extends PopupWindow {

    //Layouts
    private VBox contentPane, startTimePicker, endTimePicker;
    private HBox employeePicker, roomPicker, weekPicker, weekDayPicker, shiftTypePicker, startHourMinutePicker, endHourMinutePicker, startEndTimePicker;

    //Labels
    private Label lWeek, lEmployee, lRoom, lStart, lEnd;

    //Combobox
    private ComboBox cWeek, cEmployee, cRoom;

    //TextFields
    private TextField tStartHH, tStartMM, tEndHH, tEndMM;

    //Knapper
    private PopupMenuButton addShifts;

    private ImageButton dayShift, eveningShift, nightShift;
    LocalTime dayTime, eveningTime, nightTime;

    //Checkbokse
    private CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    //ExceptionPopup
    private ExceptionPopup exceptionPopup = new ExceptionPopup();

    //Lokal tid og 12 uger frem
    private LocalDateTime today = LocalDateTime.now(), twelveWeeks = today.plusWeeks(12);

    private TimeInvestment shift;

    public ShiftManualPopup() {
        initLayouts();
        initLabels();
        initTextFields();
        initComboboxes();
        initButtons();
        initCheckboxes();
        setup();
        initChangeListeners();

        //Gør at brugeren ikke kan ændre vinduets størrelse:
        super.getStage().setResizable(false);
    }

    private void initLayouts() {
        contentPane = new VBox(20);
        contentPane.setPadding(new Insets(0, 0, 15, 0));
        contentPane.setAlignment(Pos.CENTER);

        weekDayPicker = new HBox(25);
        weekDayPicker.setAlignment(Pos.CENTER);
        employeePicker = new HBox(25);
        employeePicker.setAlignment(Pos.CENTER);
        roomPicker = new HBox(25);
        roomPicker.setAlignment(Pos.CENTER);

        shiftTypePicker = new HBox(25);
        shiftTypePicker.setAlignment(Pos.CENTER);

        weekPicker = new HBox(25);
        weekPicker.setAlignment(Pos.CENTER);

        startEndTimePicker = new HBox(25);
        startEndTimePicker.setAlignment(Pos.CENTER);

        startTimePicker = new VBox(25);
        startTimePicker.setAlignment(Pos.CENTER);
        startHourMinutePicker = new HBox(25);
        startHourMinutePicker.setAlignment(Pos.CENTER);

        endTimePicker = new VBox(25);
        endTimePicker.setAlignment(Pos.CENTER);
        endHourMinutePicker = new HBox(25);
        endHourMinutePicker.setAlignment(Pos.CENTER);

    }

    private void initComboboxes() {
        cWeek = new ComboBox();
        cWeek.setPrefWidth(170);
        ArrayList<LocalDateTime> mondays = getTwelveMondays();
        for (int i = 0; i < mondays.size(); i++) {
            cWeek.getItems().add(mondays.get(i));
        }
        Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>> cellFactory = new Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>>() {
            @Override
            public ListCell<LocalDateTime> call(ListView<LocalDateTime> param) {

                return new ListCell<LocalDateTime>() {
                    @Override
                    public void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setText("Uge " + item.getWeekOfWeekyear() + " " + item.toString("/yy"));
                        }
                    }

                };
            }
        };

        cWeek.setButtonCell(cellFactory.call(null));
        cWeek.setCellFactory(cellFactory);
        

        cEmployee = new ComboBox();
        cEmployee.setPrefWidth(170);
        cEmployee.setDisable(true);

        cRoom = new ComboBox();
        cRoom.setPrefWidth(170);
        cRoom.setDisable(true);

    }

    private void initButtons() {

        addShifts = new PopupMenuButton("Tilføj vagter");
        addShifts.setOnAction(e -> {
            ArrayList<TimeInvestment> shifts = new ArrayList<>();
            //Henter datoerne på de valgte ugedage, hvor hvert index passer til en ugedag
            //så mandag er index 0 osv. Hvis ugedagen ikke er valgt er indexet null.
            int weekCounter = 0;

            //Initialisere start slut time og minutværdierne med ikke legitime tal 
            int startHH = -1;
            int startMM = -1;
            int endHH = -1;
            int endMM = -1;

            //Tjekker om der er skrevet tekst i de fire tekstfields der skal indeholde 
            //time og minutværdi.
            String inputErrorMessage = "Der kan kun indtastes tal i de 4 felter";
            try {
                startHH = Integer.parseInt(tStartHH.getText());
                startMM = Integer.parseInt(tStartMM.getText());
                endHH = Integer.parseInt(tEndHH.getText());
                endMM = Integer.parseInt(tEndMM.getText());
            } catch (NumberFormatException ex) {
                exceptionPopup.display(inputErrorMessage);
            }

            String wrongHourSize = "Der kan kun indtastes et validt timetal";
            String wrongMinSize = "Der kan kun indtastes et validt Minuttal";

            if (cWeek.getSelectionModel().getSelectedItem() == null) {
                exceptionPopup.display("Vælg en uge");
            } else if (cEmployee.getSelectionModel().getSelectedItem() == null) {
                exceptionPopup.display("Vælg en ansat");
            } else if (cRoom.getSelectionModel().getSelectedItem() == null) {
                exceptionPopup.display("Vælg et Rum");
            } else if (!monday.isSelected() && !tuesday.isSelected() && !wednesday.isSelected()
                    && !thursday.isSelected() && !friday.isSelected() && !saturday.isSelected()
                    && !sunday.isSelected()) {
                exceptionPopup.display("Vælg mindst en dag på ugen");

            } else if (startHH < 0 || startHH >= 24 || endHH < 0 || endHH >= 24) {
                exceptionPopup.display(wrongHourSize);
            } else if (startMM < 0 || startMM >= 60 || endMM < 0 || endMM >= 60) {
                exceptionPopup.display(wrongMinSize);
            } else {

                ArrayList<LocalDate> chosenDays = getCheckBoxLocalDate();
                ArrayList<LocalDateTime> chosenDaysNTime = new ArrayList<>();

                Hours hours = null;
                try {
                    hours = getEndLocalHours();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

                Minutes minutes = null;
                try {
                    minutes = getEndLocalMinutes();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());

                }

                LocalTime startTime = null;
                try {
                    startTime = getStartLocalTime();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());

                }

                if (hours != null || minutes != null || startTime != null) {

                    Employee employee = (Employee) cEmployee.getValue();
                    Room room = (Room) cRoom.getValue();

                    for (LocalDate chosenDay : chosenDays) {
                        if (chosenDay != null) {
                            //Her omdannes alle LocalDates til LocalDateTimes og indsættes i chosenDaysNTime arrayet
                            LocalDateTime dateToDateTime = chosenDay.toLocalDateTime(startTime);
                            chosenDaysNTime.add(dateToDateTime);
                        }
                    }

                    if (!chosenDaysNTime.isEmpty()) {
                        for (LocalDateTime chosenDaysNTime1 : chosenDaysNTime) {
                            TimeInvestment tm = new TimeInvestment(hours, minutes, chosenDaysNTime1, employee, room);
                            shifts.add(tm);
                        }
                    }

                    //TimeInvestmenthandler skal indsætte dem i databasen.
                    try {
                        Xray.getInstance().addTimeInvestments(shifts);
                    } catch (DatabaseException ex) {
                        System.out.println(ex.getMessage());
                    } 
                } else {
                    exceptionPopup.display("Tidsperioden for vagten kunne ikke kalkuleres, "
                            + "kontakt venligst systemadministratoren");
                }
            }
        });

        dayShift = new ImageButton("pictures/morgen 60.png","pictures/morgen 60 dark.png");
        dayShift.setOnAction(e -> {
            eveningShift.setUnPressed();
            nightShift.setUnPressed();
            
            tStartHH.setText("7");
            tStartMM.setText("30");
            tEndHH.setText("15");
            tEndMM.setText("15");
        });

        eveningShift = new ImageButton("pictures/aften 60.png", "pictures/aften 60 dark.png");
        eveningShift.setOnAction(e -> {
            dayShift.setUnPressed();
            nightShift.setUnPressed();
            
            tStartHH.setText("15");
            tStartMM.setText("15");
            tEndHH.setText("23");
            tEndMM.setText("30");
        });

        nightShift = new ImageButton("pictures/nat 60.png", "pictures/nat 60 dark.png");
        nightShift.setOnAction(e -> {
            dayShift.setUnPressed();
            eveningShift.setUnPressed();
            
            tStartHH.setText("23");
            tStartMM.setText("30");
            tEndHH.setText("7");
            tEndMM.setText("30");
        });

    }

    private void setup() {
        super.addToCenter(contentPane);

        endHourMinutePicker.getChildren().addAll(tEndHH, tEndMM);
        startHourMinutePicker.getChildren().addAll(tStartHH, tStartMM);
        endTimePicker.getChildren().addAll(lEnd, endHourMinutePicker);
        startTimePicker.getChildren().addAll(lStart, startHourMinutePicker);
        startEndTimePicker.getChildren().addAll(startTimePicker, endTimePicker);
        weekDayPicker.getChildren().addAll(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
        shiftTypePicker.getChildren().addAll(dayShift, eveningShift, nightShift);
        roomPicker.getChildren().addAll(lRoom, cRoom);
        employeePicker.getChildren().addAll(lEmployee, cEmployee);
        weekPicker.getChildren().addAll(lWeek, cWeek);
        contentPane.getChildren().addAll(weekPicker, employeePicker, roomPicker, shiftTypePicker, weekDayPicker, startEndTimePicker);

        super.addToBottomHBox(addShifts);

    }

    private void initLabels() {
        lWeek = new Label("Vælg uge");
        lWeek.setPrefWidth(85);
        lEmployee = new Label("Vælg ansat");
        lEmployee.setPrefWidth(85);
        lRoom = new Label("Vælg rum");
        lRoom.setPrefWidth(85);

        lStart = new Label("Start");
        lStart.setPrefWidth(85);
        lEnd = new Label("Slut");
        lEnd.setPrefWidth(85);
    }

    private void initChangeListeners() {
        cWeek.valueProperty().addListener(new ChangeListener<LocalDateTime>() {

            @Override
            public void changed(ObservableValue observable, LocalDateTime oldValue, LocalDateTime newValue) {
                if (newValue != null) {

                    try {
                        //Indsæt alle ansatte i comboboxen
                        ArrayList<Employee> employees;

                        //Fejlhåndter exceptions og giv ordentlige fejlbeskeder.
                        employees = Xray.getInstance().getPersonControl().getEmployees();

                        //Hver employee tilføjes til comboboksen
                        for (Employee employee : employees) {
                            cEmployee.getItems().add(employee);
                        }
                    } catch (DatabaseException ex) {
//                        String error = "Der kunne ikke hentes ansatte ind i drop-ned menuen"
//                                + " kontakt venligst systemadministrator.";
                        exceptionPopup.display(ex.getMessage());
                    } 
//                        catch (ClassNotFoundException ex) {
//                        String error = "Der kunne ikke oprettes forbindelse til databasen, kontakt venligst"
//                                + "din systemadministrator.";
//                        exceptionPopup.display(error);
//                    }
                    //Når man har valgt en dato skal comboboksen med ansatte komme frem
                    cEmployee.setDisable(false);
                }
            }

        });

        if (cEmployee != null) {
            cEmployee.valueProperty().addListener(new ChangeListener<Employee>() {

                @Override
                public void changed(ObservableValue observable, Employee oldValue, Employee newValue) {
                    if (newValue != null) {
                        //Hent rum
                        try {
                            //Indsæt alle rum i comboboxen
                            ArrayList<Room> rooms;

                            //Fejlhåndter exceptions og giv ordentlige fejlbeskeder.
                            rooms = Xray.getInstance().getRoomControl().getRooms();

                            //Hver room tilføjes til comboboksen
                            for (Room room : rooms) {
                                cRoom.getItems().add(room);
                            }
                        } catch (DatabaseException ex) {
//                            String error = "Der kunne ikke hentes rum ind i drop-ned menuen"
//                                    + " kontakt venligst systemadministrator.";
                            exceptionPopup.display(ex.getMessage());
                        } 
//                        catch (ClassNotFoundException ex) {
//                            String error = "Der kunne ikke oprettes forbindelse til databasen, kontakt venligst"
//                                    + "din systemadministrator.";
//                            exceptionPopup.display(error);
//                        }
                        //Når man har valgt en dato skal comboboksen med ansatte komme frem
                        cRoom.setDisable(false);
                    }
                }
            });
        }
    }

    private void initCheckboxes() {
        monday = new CheckBox("Mandag");
        tuesday = new CheckBox("Tirsdag");
        wednesday = new CheckBox("Onsdag");
        thursday = new CheckBox("Torsdag");
        friday = new CheckBox("Fredag");
        saturday = new CheckBox("Lørdag");
        sunday = new CheckBox("Søndag");
    }

    public LocalTime getStartLocalTime() throws Exception {
        String inputErrorMessage = "Der kan kun indtastes tal i de 4 felter";
        LocalTime startTime = null;

        int startHH = Integer.parseInt(tStartHH.getText());
        int startMM = Integer.parseInt(tStartMM.getText());

        startTime = new LocalTime(startHH, startMM, 0);

        return startTime;
    }

    public Hours getEndLocalHours() throws Exception {
        String inputErrorMessage = "Der kan kun indtastes tal i de 4 felter";

        Hours startToEnd;
        LocalTime endTime = null;
        LocalTime startTime = getStartLocalTime();

        int endHH = Integer.parseInt(tEndHH.getText());
        int endMM = Integer.parseInt(tEndMM.getText());

        endTime = new LocalTime(endHH, endMM, 0);

        startToEnd = Hours.hoursBetween(startTime, endTime);

        return startToEnd;
    }

    public Minutes getEndLocalMinutes() throws Exception {
        String inputErrorMessage = "Der kan kun indtastes tal i de 4 felter";

        Minutes startToEnd;
        LocalTime endTime = null;
        LocalTime startTime = getStartLocalTime();

        int endHH = Integer.parseInt(tEndHH.getText());
        int endMM = Integer.parseInt(tEndMM.getText());

        endTime = new LocalTime(endHH, endMM, 0);

        //samlede minutter fra start til slut
        startToEnd = Minutes.minutesBetween(startTime, endTime);
        //Da vi allerede får timer fra getEndLocalHours, så har vi kun brug for
        //det ekstra minutantal der er i en time. Ellers får vi det samlede minutantal
        //for hele vagten. Vi har kun brug for dem der går ud over timerne. 
        //Altså minuser vi det samlede minuttantal med det afrundede timeantal for at få "addedMinutes"
        Minutes addedMinutes = startToEnd.minus(getEndLocalHours().toStandardMinutes());

        return addedMinutes;
    }

    public ArrayList<LocalDate> getCheckBoxLocalDate() {
        ArrayList<LocalDate> chosenDays = new ArrayList<>();
        LocalDateTime dateTimeMon = (LocalDateTime) cWeek.getSelectionModel().getSelectedItem();

        //Checker om checkboxene er checkede, henter dato udfra den valgte mandag
        //i cWeek og ændre det til LocalDate så der senere kan sættes LocalTime
        if (monday.isSelected()) {
            LocalDate mon = dateTimeMon.toLocalDate();
            chosenDays.add(0, mon);
        } else if (!monday.isSelected()) {
            LocalDate mon = null;
            chosenDays.add(0, mon);
        }

        if (tuesday.isSelected()) {
            LocalDate mon = dateTimeMon.toLocalDate();
            chosenDays.add(1, mon.plusDays(1));
        } else if (!tuesday.isSelected()) {
            LocalDate mon = null;
            chosenDays.add(1, mon);
        }

        if (wednesday.isSelected()) {
            LocalDate mon = dateTimeMon.toLocalDate();
            chosenDays.add(2, mon.plusDays(2));
        } else if (!wednesday.isSelected()) {
            LocalDate mon = null;
            chosenDays.add(2, mon);
        }

        if (thursday.isSelected()) {
            LocalDate mon = dateTimeMon.toLocalDate();
            chosenDays.add(3, mon.plusDays(3));
        } else if (!thursday.isSelected()) {
            LocalDate mon = null;
            chosenDays.add(3, mon);
        }

        if (friday.isSelected()) {
            LocalDate mon = dateTimeMon.toLocalDate();
            chosenDays.add(4, mon.plusDays(4));
        } else if (!friday.isSelected()) {
            LocalDate mon = null;
            chosenDays.add(4, mon);
        }

        if (saturday.isSelected()) {
            LocalDate mon = dateTimeMon.toLocalDate();
            chosenDays.add(5, mon.plusDays(5));
        } else if (!saturday.isSelected()) {
            LocalDate mon = null;
            chosenDays.add(5, mon);
        }

        if (sunday.isSelected()) {
            LocalDate mon = dateTimeMon.toLocalDate();
            chosenDays.add(6, mon.plusDays(6));
            System.out.println(mon + "" + mon.plusDays(6) + "Whatethef");
        } else if (!sunday.isSelected()) {
            LocalDate mon = null;
            chosenDays.add(6, mon);
        }

        return chosenDays;
    }

    public ArrayList<LocalDateTime> getTwelveMondays() {
        ArrayList<LocalDateTime> mondays = new ArrayList<>();
        LocalDateTime monday;

        //Denne her uges mandag
        LocalDateTime thisMonday = today.withDayOfWeek(DateTimeConstants.MONDAY);

        //Starter monday på denne her uges mandag
        monday = thisMonday;

        //En løkke der tæller alle mandage op 12 uger frem og sætter dem i arrayListen
        for (int i = 0; i < 13; i++) {
            mondays.add(monday);

            if (monday.isBefore(twelveWeeks)) {
                monday = thisMonday.plusWeeks(i);

                System.out.println(monday);
            }
        }

        return mondays;
    }

    private void initTextFields() {
        tStartHH = new TextField("HH");
        tStartHH.setPrefWidth(50);

        tStartMM = new TextField("MM");
        tStartMM.setPrefWidth(50);

        tEndHH = new TextField("HH");
        tEndHH.setPrefWidth(50);

        tEndMM = new TextField("MM");
        tEndMM.setPrefWidth(50);

    }

}
