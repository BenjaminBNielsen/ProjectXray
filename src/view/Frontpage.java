package view;

import view.popups.RoomPopup;
import view.popups.QualificationPopup;
import view.popups.EmployeePopup;
import view.popups.DatabasePopup;
import control.Xray;
import dbc.DatabaseConnection;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.*;
import static javafx.application.Application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import view.buttons.PopupMenuButton;
import view.popups.QualificationTypePopup;
import view.popups.StudentPopup;
import view.popups.shift.ShiftPopup;

public class Frontpage extends Application {

    public static final String TITLE = "Planlægning";

    private Stage window;

    //scener:
    private Scene frontPageScene;
    private Scene noConnectionScene;

    //layout-panes:
    private VBox vMainLayout;
    private HBox hMenuLayout;

    //buttons:
    private PopupMenuButton createEmployee, createQualificationButton, createRoomButton,
            createStudent, createShift;
    
    
    public static void main(String[] args) {
        launch(args);

    }

    //Hovedmetoden der bliver kørt i gui'en.
    @Override
    public void start(Stage window) {
        
        try {
            Xray.getInstance().createConnection();
        } catch (SQLException ex) {
            DatabasePopup dbp = new DatabasePopup();
            dbp.display("Ingen forbindelse til database");
        } catch (ClassNotFoundException ex) {
            DatabasePopup dbp = new DatabasePopup();
            dbp.display("Database library skal tilføjes");
        } catch (FileNotFoundException ex) {
            DatabasePopup dbp = new DatabasePopup();
            dbp.display("Databasefil mangler");
        }
        if (DatabaseConnection.getInstance().hasConnection()) {
            initNodes(window);
        }
        
    }

    private void initNodes(Stage window) {
        //initialiser felter:
        this.window = window;
        vMainLayout = new VBox(20);
        hMenuLayout = new HBox(15);
        frontPageScene = new Scene(vMainLayout, 1024, 768);
        
        window.setTitle(TITLE);

        //initialiser knapper:
        initButtons();

        //Sætter padding på hboxen sådan at den ikke placeres direkte op af vinduekanten.
        hMenuLayout.setPadding(new Insets(15, 15, 15, 15));

        //Tilføj knapper til hMainLayout:
        hMenuLayout.setAlignment(Pos.CENTER_LEFT);

        vMainLayout.setAlignment(Pos.TOP_LEFT);
        vMainLayout.getChildren().add(hMenuLayout);

        window.setScene(frontPageScene);
        window.show();
        
        
    }

    private void initButtons() {

        ArrayList<PopupMenuButton> menuButtons = new ArrayList<>();

        createEmployee = new PopupMenuButton("Opret ansat");
        createEmployee.setOnAction(e -> {
            EmployeePopup personPopup = new EmployeePopup();
            personPopup.display("Opret ansat");
        });
        menuButtons.add(createEmployee);
        
        createStudent = new PopupMenuButton("Opret studerende");
        createStudent.setOnAction(e -> {
        });
        menuButtons.add(createStudent);
        
        createQualificationButton = new PopupMenuButton("Opret kvalifikation");
        createQualificationButton.setOnAction(e -> {
            QualificationTypePopup qualificationTypeWindow = new QualificationTypePopup();
            qualificationTypeWindow.display("Kvalifikationer");

        });
        menuButtons.add(createQualificationButton);

        createRoomButton = new PopupMenuButton("Opret rum");
        createRoomButton.setOnAction(e -> {
            RoomPopup roomWindow = new RoomPopup();
            roomWindow.display("Rum");
        });
        menuButtons.add(createRoomButton);
        for (PopupMenuButton menuButton : menuButtons) {
            hMenuLayout.getChildren().add(menuButton);
        }
    }
}
