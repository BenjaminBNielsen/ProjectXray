package view;

import view.schema.Schedule;
import view.popups.RoomPopup;
import view.popups.EmployeePopup;
import view.popups.DatabasePopup;
import control.Xray;
import dbc.DatabaseConnection;
import exceptions.DatabaseException;
import handlers.EmployeeHandler;
import handlers.QualificationHandler;
import handlers.RoomHandler;
import handlers.TimeInvestmentHandler;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.*;
import static javafx.application.Application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.*;
import model.Employee;
import model.LimitQualification;
import model.Room;
import model.RoomQualification;
import model.TimeInvestment;
import org.joda.time.LocalDateTime;
import view.buttons.PopupMenuButton;
import view.popups.RoomQualificationPopup;
import view.popups.StudentPopup;
import view.popups.shift.ShiftPopup;

public class Frontpage extends Application {

    public static final String TITLE = "Planlægning";

    public static final int STANDARD_PADDING = 15;

    private Schedule schedule;

    private double screenWidth;
    private double screenHeight;
    
    private ArrayList<Room> rooms;
    private ArrayList<Employee> employees;

    private Stage window;

    //scener:
    private Scene frontPageScene;

    //layout-panes:
    private VBox vMainLayout;
    private HBox hMenuLayout;

    //buttons:
    private PopupMenuButton createEmployee, createQualificationButton, createRoomButton,
            createStudent, createShift, assignRoomsButtons;

    public static void main(String[] args) {
        launch(args);

    }

    //Hovedmetoden der bliver kørt i gui'en.
    @Override
    public void start(Stage window) {
        //Hent skærmens størrelse.
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        screenWidth = primaryScreenBounds.getWidth();
        screenHeight = primaryScreenBounds.getHeight();

        try {
            Xray.getInstance().createConnection();
        } catch (DatabaseException ex) {
            DatabasePopup dbp = new DatabasePopup();
            dbp.display(ex.getMessage());
        } 
        if (DatabaseConnection.getInstance().hasConnection()) {
            initNodes(window);
        }

        try {
            //Tildeling af rum til ansatte for uge 16/2015:
            //Kør 'Røntgen projekt\DB\Script 3a - insert_shifts_week16-2015.sql'.
            ArrayList<TimeInvestment> unAssigned = TimeInvestmentHandler.getInstance()
                    .getUnassignedTimeInvestments();

            ArrayList<RoomQualification> roomQuals2 = QualificationHandler.getInstance().getRoomQualifications();

            ArrayList<LimitQualification> limitQuals = QualificationHandler.getInstance().getLimitQualifications();

            //tildel via assign rooms metode:
            ArrayList<TimeInvestment> assigned = Xray.getInstance().assignRooms(unAssigned, roomQuals2, limitQuals);

            //Opsætning af skema.
            hMenuLayout.setMinHeight(PopupMenuButton.PREFERRED_HEIGHT);
            Schedule schedule = new Schedule(assigned, new LocalDateTime(2015, 04, 13, 0, 0));
            vMainLayout.getChildren().add(schedule);

        } catch (DatabaseException ex) {
            System.out.println(ex.getMessage());
        } 
    }

    private void initNodes(Stage window) {
        //initialiser felter:
        this.window = window;
        vMainLayout = new VBox(STANDARD_PADDING);

        hMenuLayout = new HBox(STANDARD_PADDING);

        frontPageScene = new Scene(vMainLayout, screenWidth, screenHeight);
        vMainLayout.setPadding(new Insets(STANDARD_PADDING, STANDARD_PADDING, STANDARD_PADDING, STANDARD_PADDING));
        window.setTitle(TITLE);
        
        rooms = new ArrayList<>();
        employees = new ArrayList<>();
        
        initArrayLists();
        
        //initialiser knapper:
        initButtons();

        //Tilføj knapper til hMainLayout:
        hMenuLayout.setAlignment(Pos.CENTER_LEFT);

        vMainLayout.setAlignment(Pos.TOP_LEFT);
        vMainLayout.getChildren().addAll(hMenuLayout);

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
            StudentPopup studentPopup = new StudentPopup();
            studentPopup.display("Opret studerende");
        });
        menuButtons.add(createStudent);

        createQualificationButton = new PopupMenuButton("Opret kvalifikation");
        createQualificationButton.setOnAction(e -> {
            RoomQualificationPopup roomQualificationWindow = 
                    new RoomQualificationPopup(rooms, employees);
            roomQualificationWindow.display("Kvalifikationer");

        });
        menuButtons.add(createQualificationButton);

        createRoomButton = new PopupMenuButton("Opret rum");
        createRoomButton.setOnAction(e -> {
            RoomPopup roomWindow = new RoomPopup();
            roomWindow.display("Rum");
        });
        menuButtons.add(createRoomButton);

        createShift = new PopupMenuButton("Opret vagter");
        createShift.setOnAction(e -> {
            ShiftPopup shiftPopup = new ShiftPopup();
            shiftPopup.display("Vagter");
        });
        menuButtons.add(createShift);

        assignRoomsButtons = new PopupMenuButton("Tildel vagter");
        assignRoomsButtons.setOnAction(e -> {

        });
        menuButtons.add(assignRoomsButtons);

        for (PopupMenuButton menuButton : menuButtons) {
            hMenuLayout.getChildren().add(menuButton);
        }

    }
    
    public void initArrayLists() {
        
        //Der skal skrives færdigt på de her try and catches!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        try {
        rooms = Xray.getInstance().getRoomControl().getRooms();
        } catch (DatabaseException ex) {
            
        } 
        
        try {
        employees = Xray.getInstance().getPersonControl().getEmployees();
        } catch (DatabaseException ex) {
            
        } 
        
        
        
    }
}
