/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups.shift;

import control.Xray;
import exceptions.DatabaseException;
import technicalServices.persistence.TimeInvestmentHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.Employee;
import model.TimeInvestment;
import org.joda.time.LocalDateTime;
import view.buttons.PopupMenuButton;
import view.buttons.ShiftManualButton;
import view.popups.ExceptionPopup;
import view.popups.PopupWindow;
import view.schema.ScheduleHeader;

/**
 *
 * @author Benjamin
 */
public class ShiftPopup extends PopupWindow {

    //Layouts
    private VBox contentPane, dateEmployeePicker;
    private HBox datePicker, employeePicker, manualDateEmployeePicker;

    //Labels
    private Label lDate, lEmployee;

    //Combobox
    private ComboBox cDate, cEmployee;

    //Knapper
    private PopupMenuButton addShifts;
    private ShiftManualButton shiftManual;

    //ExceptionPopup
    private ExceptionPopup exceptionPopup = new ExceptionPopup();
    
    //ArryListe med

    //Liste af ShiftPaneler
    private ShiftPanel[] shiftPanels;

    //Codeblock, eksekveres lige før constructoren
    {
        shiftPanels = new ShiftPanel[7];
    }

    public ShiftPopup() {
        initLayouts();
        initLabels();
        initComboboxes();
        initButtons();
        initShiftPanels();
        initChangeListeners();
        setup();

        //Gør at brugeren ikke kan ændre vinduets størrelse:
        super.getStage().setResizable(false);
    }

    private void initLayouts() {
        contentPane = new VBox(20);
        contentPane.setPadding(new Insets(0, 0, 15, 0));
        contentPane.setAlignment(Pos.CENTER);
        datePicker = new HBox(25);
        datePicker.setAlignment(Pos.CENTER);
        employeePicker = new HBox(25);
        employeePicker.setAlignment(Pos.CENTER);
        manualDateEmployeePicker = new HBox(25);
        manualDateEmployeePicker.setAlignment(Pos.CENTER);
        dateEmployeePicker = new VBox(20);

    }

    private void initComboboxes() {

        cDate = new ComboBox();
        cDate.setPrefWidth(170);

        LocalDateTime now = new LocalDateTime();
        now = now.withHourOfDay(0);
        now = now.withMinuteOfHour(0);
        LocalDateTime currentWeek = now;
        
        LocalDateTime threeMonthsForward = now.plusMonths(3);
        ArrayList<LocalDateTime> startDates = Xray.getInstance().getDatesInPeriod(currentWeek, threeMonthsForward);
        for (int i = 0; i < startDates.size(); i++) {
            if (startDates.get(i).getDayOfWeek() == 1) {
                cDate.getItems().add(startDates.get(i));
            }
        }

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

        cDate.setButtonCell(cellFactory.call(null));
        cDate.setCellFactory(cellFactory);

        cEmployee = new ComboBox();
        cEmployee.setPrefWidth(170);
        cEmployee.setDisable(true);

    }

    private void initButtons() {
        addShifts = new PopupMenuButton("Tilføj vagter");
        addShifts.setOnAction(e -> {
            ArrayList<TimeInvestment> shifts = new ArrayList<>();
            for (int i = 0; i < shiftPanels.length; i++) {
                if (shiftPanels[i].getShift() != null) {
                    shifts.add(shiftPanels[i].getShift());
                }

            }
            for (int i = 0; i < shifts.size(); i++) {
                System.out.println(shifts.get(i));
            }

            //TimeInvestmenthandler skal indsætte dem i databasen.
            try {
                Xray.getInstance().getTimeInvestmentControl().addTimeInvestments(shifts);
            } catch (DatabaseException ex) {
                System.out.println(ex.getMessage());
            }
        });

        shiftManual = new ShiftManualButton("Manuel vagt");
        shiftManual.setOnAction(e -> {
            shiftManual.setOnActionCode();
        });
    }

    private void initShiftPanels() {
        LocalDateTime ldt = (LocalDateTime) cDate.getSelectionModel().getSelectedItem();
        Employee emp = (Employee) cEmployee.getSelectionModel().getSelectedItem();

        shiftPanels[0] = new ShiftPanel(1, "Mandag", ldt, emp);
        shiftPanels[1] = new ShiftPanel(2, "Tirsdag", ldt, emp);
        shiftPanels[2] = new ShiftPanel(3, "Onsdag", ldt, emp);
        shiftPanels[3] = new ShiftPanel(4, "Torsdag", ldt, emp);
        shiftPanels[4] = new ShiftPanel(5, "Fredag", ldt, emp);
        shiftPanels[5] = new ShiftPanel(6, "Lørdag", ldt, emp);
        shiftPanels[6] = new ShiftPanel(7, "Søndag", ldt, emp);

        for (ShiftPanel shiftPanel : shiftPanels) {
            shiftPanel.setAlignment(Pos.CENTER);
            shiftPanel.setDisable(true);
        }
    }

    private void setup() {
        super.addToCenter(contentPane);
        contentPane.getChildren().addAll(manualDateEmployeePicker);

        manualDateEmployeePicker.getChildren().addAll(dateEmployeePicker, shiftManual);

        dateEmployeePicker.getChildren().addAll(datePicker, employeePicker);

        datePicker.getChildren().addAll(lDate, cDate);
        employeePicker.getChildren().addAll(lEmployee, cEmployee);

        contentPane.getChildren().addAll(shiftPanels);

        super.addToBottomHBox(addShifts);

    }

    private void initLabels() {
        lDate = new Label("Vælg dato");
        lDate.setPrefWidth(85);
        lEmployee = new Label("Vælg ansat");
        lEmployee.setPrefWidth(85);
    }

    private void initChangeListeners() {
        if (cDate != null) {
            cDate.valueProperty().addListener(new ChangeListener<LocalDateTime>() {

                @Override
                public void changed(ObservableValue observable, LocalDateTime oldValue, LocalDateTime newValue) {
                    if (newValue != null) {

                        for (ShiftPanel shiftPanel : shiftPanels) {
                            shiftPanel.setStartTime((LocalDateTime) cDate.getSelectionModel().getSelectedItem());
                        }

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
//                            String error = "Der kunne ikke hentes ansatte ind i drop-ned menuen"
//                                    + " kontakt venligst systemadministrator.";
                            exceptionPopup.display(ex.getMessage());
                        }
//                            catch (ClassNotFoundException ex) {
//                            String error = "Der kunne ikke oprettes forbindelse til databasen, kontakt venligst"
//                                    + "din systemadministrator.";
//                            exceptionPopup.display(error);
//                        } 
                        //Når man har valgt en dato skal comboboksen med ansatte komme frem
                        cEmployee.setDisable(false);
                    }
                }

            });
        }
        if (cEmployee != null) {
            cEmployee.valueProperty().addListener(new ChangeListener<Employee>() {

                @Override
                public void changed(ObservableValue observable, Employee oldValue, Employee newValue) {
                    if (newValue != null) {
                        for (ShiftPanel shiftPanel : shiftPanels) {
                            shiftPanel.setDisable(false);
                            shiftPanel.setEmployee((Employee) cEmployee.getSelectionModel().getSelectedItem());
                        }
                    }
                }
            });
        }
    }
}
