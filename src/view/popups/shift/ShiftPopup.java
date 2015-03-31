/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups.shift;

import control.Xray;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Employee;
import org.joda.time.LocalDateTime;
import view.buttons.PopupMenuButton;
import view.popups.ExceptionPopup;
import view.popups.PopupWindow;

/**
 *
 * @author Benjamin
 */
public class ShiftPopup extends PopupWindow {

    //Layouts
    private VBox contentPane;
    private HBox datePicker, employeePicker;

    //Labels
    private Label lDate, lEmployee;

    //Combobox
    private ComboBox cDate, cEmployee;

    //Knapper
    private PopupMenuButton addShifts;

    //ExceptionPopup
    private ExceptionPopup exceptionPopup = new ExceptionPopup();

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
    }

    private void initComboboxes() {
        cDate = new ComboBox();
        cDate.setPrefWidth(170);

        //testdata
        LocalDateTime ldt1 = new LocalDateTime(2015, 3, 23, 0, 0);
        LocalDateTime ldt2 = new LocalDateTime(2015, 3, 30, 0, 0);
        LocalDateTime ldt3 = new LocalDateTime(2015, 4, 6, 0, 0);
        cDate.getItems().addAll(ldt1, ldt2, ldt3);

        cEmployee = new ComboBox();
        cEmployee.setPrefWidth(170);
        cEmployee.setDisable(true);

    }

    private void initButtons() {
        addShifts = new PopupMenuButton("Tilføj vagter");
        addShifts.setOnAction(e -> {
            for (ShiftPanel shiftPanel : shiftPanels) {
                System.out.println(shiftPanel.getShift());
            }
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
        contentPane.getChildren().addAll(datePicker, employeePicker);

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
                        } catch (SQLException ex) {
                            String error = "Der kunne ikke hentes ansatte ind i drop-ned menuen"
                + " kontakt venligst systemadministrator.";
                            exceptionPopup.display(error);
                        } catch (ClassNotFoundException ex) {
                            String error = "Der kunne ikke oprettes forbindelse til databasen, kontakt venligst"
                                    + "din systemadministrator.";
                            exceptionPopup.display(error);
                        } 
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
