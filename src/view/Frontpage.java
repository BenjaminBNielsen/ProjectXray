package view;

import view.schema.Schedule;
import view.popups.RoomPopup;
import view.popups.EmployeePopup;
import view.popups.DatabasePopup;
import control.Xray;
import dbc.DatabaseConnection;
import handlers.TimeInvestmentHandler;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.*;
import static javafx.application.Application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.joda.time.LocalDateTime;
import view.buttons.PopupMenuButton;
import view.popups.StudentPopup;
import view.popups.shift.ShiftPopup;

public class Frontpage extends Application {

    public static final String TITLE = "Planlægning";

    public static final int STANDARD_PADDING = 15;

    private double screenWidth;
    private double screenHeight;

    private Stage window;

    //scener:
    private Scene frontPageScene;

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
        //Hent skærmens størrelse.
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        screenWidth = primaryScreenBounds.getWidth();
        screenHeight = primaryScreenBounds.getHeight();

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

        try {
            hMenuLayout.setMinHeight(PopupMenuButton.PREFERRED_HEIGHT);
            Schedule schedule = new Schedule(TimeInvestmentHandler.getInstance().getAssignedTimeInvestments(), new LocalDateTime(2015, 03, 23, 0, 0));
            double minimumScheduleHeight = screenHeight - (screenHeight / 7);
            schedule.setMinHeight(minimumScheduleHeight);
            vMainLayout.getChildren().add(schedule);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("erameåwrmewaårmeawåpmr");
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
            //QualificationTypePopup qualificationTypeWindow = new QualificationTypePopup();
            //qualificationTypeWindow.display("Kvalifikationer");

        });
        menuButtons.add(createQualificationButton);

        createRoomButton = new PopupMenuButton("Opret rum");
        createRoomButton.setOnAction(e -> {
//            RoomPopup roomWindow = new RoomPopup();
//            roomWindow.display("Rum");
        });
        menuButtons.add(createRoomButton);

        createShift = new PopupMenuButton("Opret vagter");
        createShift.setOnAction(e -> {
            ShiftPopup shiftPopup = new ShiftPopup();
            shiftPopup.display("Vagter");
        });
        menuButtons.add(createShift);

        for (PopupMenuButton menuButton : menuButtons) {
            hMenuLayout.getChildren().add(menuButton);
        }

    }
}
