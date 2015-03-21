package view;

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
    private MenuButton createEmployee, createQualificationButton, createRoomButton,
            createStudent;
    
    
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
            dbp.display("1");
        } catch (ClassNotFoundException ex) {
            DatabasePopup dbp = new DatabasePopup();
            dbp.display("2");
        } catch (FileNotFoundException ex) {
            DatabasePopup dbp = new DatabasePopup();
            dbp.display("3");
            dbp.getStage().initStyle(StageStyle.UTILITY);
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

        createEmployee = new MenuButton("Opret ansat");
        createEmployee.setOnAction(e -> {
            EmployeePopup personPopup = new EmployeePopup();
            personPopup.display("Opret ansat");
        });
        menuButtons.add(createEmployee);
        
        createStudent = new MenuButton("Opret studerende");
        createStudent.setOnAction(e -> {
            System.out.println("hey");
        });
        menuButtons.add(createStudent);
        
        createQualificationButton = new MenuButton("Opret kvalifikation");
        createQualificationButton.setOnAction(e -> {
            QualificationWindow qualificationWindow = new QualificationWindow();
            qualificationWindow.display("Kvalifikationer");

        });
        menuButtons.add(createQualificationButton);

        createRoomButton = new PopupMenuButton("Opret rum");
        createRoomButton.setOnAction(e -> {
            RoomWindow roomWindow = new RoomWindow();
            roomWindow.display("Rum");
        });
        menuButtons.add(createRoomButton);

        for (PopupMenuButton menuButton : menuButtons) {
            hMenuLayout.getChildren().add(menuButton);
        }
    }
}
