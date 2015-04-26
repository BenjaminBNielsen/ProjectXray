package view.popups;

import control.Xray;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.buttons.PopupMenuButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Employee;
import model.Occupation;
import view.buttons.AddButton;
import view.buttons.SettingsButton;

public class EmployeePopup extends PopupWindow {
    //Layouts
    private VBox contentEmployee, addEmployeePane, middleContentPane;

    //Labels
    private Label lPersonNumber, lName, lLastName, lPhone, lAddress, lEmail,
            lAddPersons, lOccupation;
    
    //Combobox
    private ComboBox cOccupation;

    //Textboxes
    private TextField tPersonNumber, tName, tLastName, tPhone, tAddress, tEmail;

    //Knapper
    private PopupMenuButton addEmployee;
    private AddButton addEmpToList;
    private SettingsButton changeEmpOnList;

    //Listview
    private ListView<Employee> employeeView;
    private ObservableList<Employee> employees = FXCollections.observableArrayList();

    //ExceptionPopup
    private ExceptionPopup exceptionPopup = new ExceptionPopup();

    @Override
    public void display(String title) {
        initLayouts();
        initLabels();
        initTextFields();
        initComboBoxes();
        setupEmployeeScreen();
        initListViews();
        initButtons();

        super.addToLeft(contentEmployee);
        super.addToCenter(middleContentPane);
        super.addToRight(addEmployeePane);
        super.display(title);
    }

    private void setupEmployeeScreen() {
        //Tilføj til venstre side
        contentEmployee.setAlignment(Pos.TOP_LEFT);
        contentEmployee.getChildren().addAll(lPersonNumber, tPersonNumber, lName,
                tName, lLastName, tLastName, lPhone, tPhone, lAddress,
                tAddress, lEmail, tEmail, lOccupation, cOccupation);

        //Tilføj til højre side
        addEmployeePane.setAlignment(Pos.TOP_LEFT);
        addEmployeePane.getChildren().addAll(lAddPersons);

        middleContentPane.setAlignment(Pos.CENTER);
    }

    private void initButtons() {
        addEmployee = new PopupMenuButton("Tilføj ansatte");
        addEmployee.setOnAction(e -> {
            try {
                Xray.getInstance().getPersonControl().addEmployees(employees);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        });
        super.addToBottomHBox(addEmployee);

        addEmpToList = new AddButton();
        addEmpToList.setOnAction(e -> {
            addEmpToList();
        });

        changeEmpOnList = new SettingsButton();
        changeEmpOnList.setOnAction(e -> {
            System.out.println(employeeView.getSelectionModel().getSelectedIndex());
        });

        middleContentPane.getChildren().addAll(addEmpToList, changeEmpOnList);

    }

    private void addEmpToList() {
        boolean noError = true;
        int id = 0;
        int phone = 0;
        try {
            id = Integer.parseInt(tPersonNumber.getText());
        } catch (NumberFormatException ex) {
            exceptionPopup.display("Du skal indtaste et tal.");
            noError = false;
        }
        String fName = tName.getText();
        String lName = tLastName.getText();
        try {
            phone = Integer.parseInt(tPhone.getText());
        } catch (NumberFormatException ex) {
            if (noError) {
                exceptionPopup.display("Du skal indtaste et telefonnummer.");
                noError = false;
            }
        }
        String address = tAddress.getText();
        String eMail = tEmail.getText();

        if (noError) {
            employees.add(new Employee(fName, lName, id, phone, address, eMail, 
                    (Occupation)cOccupation.getSelectionModel().getSelectedItem()));
            employeeView.setItems(employees);
        }
    }

    private void initListViews() {
        employeeView = new ListView<>();
        addEmployeePane.getChildren().add(employeeView);
    }

    private void initLayouts() {
        //til venstre hvor der er indtastningsfelter til at oprette ansatte
        contentEmployee = new VBox(15);
        contentEmployee.setPadding(new Insets(0, 0, 15, 0));
        contentEmployee.setPrefWidth(COLUMN_STANDARD_WIDTH);

        //Til højre side hvor der er en liste af medarbejdere til tilføjelse
        addEmployeePane = new VBox(15);
        addEmployeePane.setPadding(new Insets(0, 0, 15, 0));
        addEmployeePane.setPrefWidth(COLUMN_STANDARD_WIDTH);

        //I midten hvor man tilføjer personer til listen på højre side, eller
        //ændrer personer som er valgt på listen
        middleContentPane = new VBox(15);
        middleContentPane.setPadding(new Insets(0, 15, 0, 15));
    }

    private void initLabels() {
        lPersonNumber = new Label("Personnummer");
        lName = new Label("Fornavn");
        lLastName = new Label("Efternavn");
        lPhone = new Label("Telefonnummer");
        lAddress = new Label("Addresse");
        lEmail = new Label("Email addresse");
        lAddPersons = new Label("Tilføj personer her");
        lOccupation = new Label("Stilling");
    }

    private void initTextFields() {
        tPersonNumber = new TextField();
        tName = new TextField();
        tLastName = new TextField();
        tPhone = new TextField();
        tAddress = new TextField();
        tEmail = new TextField();
    }

    private void initComboBoxes() {
        try {
            cOccupation = new ComboBox(Xray.getInstance().getPersonControl().getOccupations());
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
        
        cOccupation.getSelectionModel().selectFirst();
        cOccupation.setPrefWidth(COLUMN_STANDARD_WIDTH);
    }
}
