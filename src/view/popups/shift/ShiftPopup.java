/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups.shift;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Employee;
import model.Occupation;
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
        LocalDateTime ldt1 = new LocalDateTime(2015, 3, 27, 8, 30);
        LocalDateTime ldt2 = new LocalDateTime(2015, 3, 27, 15, 15);
        LocalDateTime ldt3 = new LocalDateTime(2015, 3, 27, 23, 30);
        cDate.getItems().addAll(ldt1, ldt2, ldt3);
        
        cEmployee = new ComboBox();
        cEmployee.setPrefWidth(170);
        cEmployee.setDisable(true);
                
        //Mere testdata
        Employee emp1 = new Employee("eare", "aeraeraer", 1, 2, "ea", "we", new Occupation(1, "earewr"));
        cEmployee.getItems().add(emp1);
    }
    
    private void initButtons() {
        addShifts = new PopupMenuButton("Tilføj vagter");
        addShifts.setOnAction(e -> {
            
        });
    }
    
    private void initShiftPanels() {
//        LocalDateTime ldt = (LocalDateTime) cDate.getSelectionModel().getSelectedItem();
        LocalDateTime ldt = new LocalDateTime(2015, 3, 27, 8, 30);
        
        shiftPanels[0] = new ShiftPanel(1, "Mandag", ldt);
        shiftPanels[1] = new ShiftPanel(2, "Tirsdag", ldt);
        shiftPanels[2] = new ShiftPanel(3, "Onsdag", ldt);
        shiftPanels[3] = new ShiftPanel(4, "Torsdag", ldt);
        shiftPanels[4] = new ShiftPanel(5, "Fredag", ldt);
        shiftPanels[5] = new ShiftPanel(6, "Lørdag", ldt);
        shiftPanels[6] = new ShiftPanel(7, "Søndag", ldt);
        
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
                        }
                    }
                }
            });
        }
    }
}
