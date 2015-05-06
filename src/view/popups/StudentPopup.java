/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups;

import control.Xray;
import exceptions.DatabaseException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Employee;
import model.Occupation;
import model.Student;
import view.buttons.AddButton;
import view.buttons.PopupMenuButton;
import view.buttons.SettingsButton;

/**
 *
 * @author Benjamin
 */
public class StudentPopup extends PopupWindow{

    public static final int COLUMN_STANDARD_WIDTH = 200;

    //Layouts
    private VBox contentStudent, addStudentPane, middleContentPane;

    //Labels
    private Label lPersonNumber, lName, lLastName, lModule, lAddPersons;

    //Textboxes
    private TextField tPersonNumber, tName, tLastName, tModule;

    //Knapper
    private PopupMenuButton addStudent;
    private AddButton addStuToList;
    private SettingsButton changeStuOnList;

    //Listview
    private ListView<Student> studentView;
    private ObservableList<Student> students = FXCollections.observableArrayList();

    //ExceptionPopup
    private ExceptionPopup exceptionPopup = new ExceptionPopup();

    @Override
    public void display(String title) {
        initLayouts();
        initLabels();
        initTextFields();
        setupEmployeeScreen();
        initListViews();
        initButtons();

        super.addToLeft(contentStudent);
        super.addToCenter(middleContentPane);
        super.addToRight(addStudentPane);
        super.display(title);
    }

    private void setupEmployeeScreen() {
        //Tilføj til venstre side
        contentStudent.setAlignment(Pos.TOP_LEFT);
        contentStudent.getChildren().addAll(lPersonNumber, tPersonNumber, lName,
                tName, lLastName, tLastName, lModule, tModule);

        //Tilføj til højre side
        addStudentPane.setAlignment(Pos.TOP_LEFT);
        addStudentPane.getChildren().addAll(lAddPersons);

        middleContentPane.setAlignment(Pos.CENTER);
    }

    private void initButtons() {
        addStudent = new PopupMenuButton("Tilføj ansatte");
        addStudent.setOnAction(e -> {
            try {
                Xray.getInstance().getPersonControl().addStudents(students);
            } catch (DatabaseException ex) {
            } 
        });
        super.addToBottomHBox(addStudent);

        addStuToList = new AddButton();
        addStuToList.setOnAction(e -> {
            addEmpToList();
        });

        changeStuOnList = new SettingsButton();
        changeStuOnList.setOnAction(e -> {
            System.out.println(studentView.getSelectionModel().getSelectedIndex());
        });

        middleContentPane.getChildren().addAll(addStuToList, changeStuOnList);

    }

    private void addEmpToList() {
        boolean noError = true;
        int id = 0;
        int module = 0;
        try {
            id = Integer.parseInt(tPersonNumber.getText());
        } catch (NumberFormatException ex) {
            exceptionPopup.display("Du skal indtaste et tal.");
            noError = false;
        }
        String fName = tName.getText();
        String lName = tLastName.getText();
        try {
            module = Integer.parseInt(tModule.getText());
        } catch (NumberFormatException ex) {
            if (noError) {
                exceptionPopup.display("Du skal indtaste et telefonnummer.");
                noError = false;
            }
        }

        if (noError) {
            students.add(new Student(id, fName, lName, module));
            studentView.setItems(students);
        }
    }

    private void initListViews() {
        studentView = new ListView<Student>();
        addStudentPane.getChildren().add(studentView);
    }

    private void initLayouts() {
        //til venstre hvor der er indtastningsfelter til at oprette ansatte
        contentStudent = new VBox(15);
        contentStudent.setPadding(new Insets(0, 0, 15, 0));
        contentStudent.setPrefWidth(COLUMN_STANDARD_WIDTH);

        //Til højre side hvor der er en liste af medarbejdere til tilføjelse
        addStudentPane = new VBox(15);
        addStudentPane.setPadding(new Insets(0, 0, 15, 0));
        addStudentPane.setPrefWidth(COLUMN_STANDARD_WIDTH);

        //I midten hvor man tilføjer personer til listen på højre side, eller
        //ændrer personer som er valgt på listen
        middleContentPane = new VBox(15);
        middleContentPane.setPadding(new Insets(0, 15, 0, 15));
    }

    private void initLabels() {
        lPersonNumber = new Label("Personnummer");
        lName = new Label("Fornavn");
        lLastName = new Label("Efternavn");
        lModule = new Label("Modul");
        lAddPersons = new Label("Tilføj personer her");
    }

    private void initTextFields() {
        tPersonNumber = new TextField();
        tName = new TextField();
        tLastName = new TextField();
        tModule = new TextField();
    }
}

