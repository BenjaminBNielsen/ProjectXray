/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups.shift;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Employee;
import view.buttons.AddButton;
import view.buttons.PopupMenuButton;
import view.buttons.SettingsButton;
import view.popups.ExceptionPopup;
import view.popups.PopupWindow;

/**
 *
 * @author Benjamin
 */
public class ShiftPopup extends PopupWindow {

    //Layouts:
    private VBox leftPane, rightPane;

    //Labels
    private Label y;

    //Combobox
    private ComboBox cDate, cPerson;

    //Knapper
    private PopupMenuButton addEmployee;
    private AddButton addEmpToList;
    private SettingsButton changeEmpOnList;

    //Listview
    private ListView<Employee> employeeView;
    private ObservableList<Employee> employees = FXCollections.observableArrayList();

    //ExceptionPopup
    private ExceptionPopup exceptionPopup = new ExceptionPopup();
}
