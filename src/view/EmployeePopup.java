package view;

import view.buttons.PopupMenuButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
            lAddPersons;

    //Textboxes
    private TextField tPersonNumber, tName, tLastName, tPhone, tAddress, tEmail;

    //Knapper
    private PopupMenuButton addEmployee;
    private AddButton addEmpToList;
    private SettingsButton changeEmpOnList;

    //Listview
    private ListView<Employee> employeeView;
    private ObservableList<Employee> employees = FXCollections.observableArrayList();

    @Override
    public void display(String title) {
        initLayouts();
        initLabels();
        initTextFields();
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
        contentEmployee.setAlignment(Pos.CENTER_LEFT);
        contentEmployee.getChildren().addAll(lPersonNumber, tPersonNumber, lName,
                tName, lLastName, tLastName, lPhone, tPhone, lAddress,
                tAddress, lEmail, tEmail);

        //Tilføj til højre side
        addEmployeePane.setAlignment(Pos.CENTER_LEFT);
        addEmployeePane.getChildren().addAll(lAddPersons);
        
        middleContentPane.setAlignment(Pos.CENTER);
    }

    private void initButtons() {
        addEmployee = new PopupMenuButton("Tilføj ansatte");
        addEmployee.setOnAction(e -> {

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
        
        middleContentPane.getChildren().addAll(addEmpToList,changeEmpOnList);

    }
    
    private void addEmpToList(){
        int id = Integer.parseInt(tPersonNumber.getText());
        String fName = tName.getText();
        String lName = tLastName.getText();
        int phone = Integer.parseInt(tPhone.getText());
        String address = tAddress.getText();
        String eMail = tEmail.getText();
        
        employees.add(new Employee(fName, lName, id, phone, address, eMail, new Occupation()));
        employeeView.setItems(employees);
    }

    private void initListViews() {
        employeeView = new ListView<Employee>();
        addEmployeePane.getChildren().add(employeeView);
    }

    private void initLayouts() {
        //til venstre hvor der er indtastningsfelter til at oprette ansatte
        contentEmployee = new VBox(15);
        contentEmployee.setPadding(new Insets(15, 0, 15, 0));

        //Til højre side hvor der er en liste af medarbejdere til tilføjelse
        addEmployeePane = new VBox(15);
        addEmployeePane.setPadding(new Insets(15, 0, 15, 15));

        //I midten hvor man tilføjer personer til listen på højre side, eller
        //ændrer personer som er valgt på listen
        middleContentPane = new VBox(15);
        middleContentPane.setPadding(new Insets(0, 0, 0, 15));
    }

    private void initLabels() {
        lPersonNumber = new Label("Personnummer");
        lName = new Label("Fornavn");
        lLastName = new Label("Efternavn");
        lPhone = new Label("Telefonnummer");
        lAddress = new Label("Addresse");
        lEmail = new Label("Email addresse");
        lAddPersons = new Label("Tilføj personer her");
    }

    private void initTextFields() {
        tPersonNumber = new TextField();
        tName = new TextField();
        tLastName = new TextField();
        tPhone = new TextField();
        tAddress = new TextField();
        tEmail = new TextField();
    }
}
