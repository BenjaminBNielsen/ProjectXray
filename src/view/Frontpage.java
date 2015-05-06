package view;

import view.schema.Schedule;
import view.popups.RoomPopup;
import view.popups.EmployeePopup;
import view.popups.DatabasePopup;
import control.Xray;
import dbc.DatabaseConnection;
import technicalServices.persistence.EmployeeHandler;
import technicalServices.persistence.QualificationHandler;
import technicalServices.persistence.RoomHandler;
import technicalServices.persistence.TimeInvestmentHandler;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import model.Employee;
import model.LimitQualification;
import model.Room;
import model.RoomQualification;
import model.TimeInvestment;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDateTime;
import techincalServices.printing.PrinterThread;
import view.buttons.PopupMenuButton;
import view.popups.StudentPopup;
import view.popups.shift.ShiftPopup;

public class Frontpage extends Application {

    public static final String TITLE = "Planlægning";

    public static final int STANDARD_PADDING = 15;

    private Schedule schedule;

    private PrinterThread printer = new PrinterThread();

    private double screenWidth;
    private double screenHeight;

    private Stage window;

    //scener:
    private Scene frontPageScene;

    //layout-panes:
    private VBox vMainLayout;
    private HBox hMenuLayout, hWeekPicker;

    //buttons:
    private PopupMenuButton createEmployee, createQualificationButton, createRoomButton,
            createStudent, createShift, assignRoomsButton;

    private Button jumpForwardWeek, jumpBackWeek, printButton;

    //combobox
    private ComboBox cWeek;

    //Denne her uges mandag
    private LocalDateTime today = LocalDateTime.now();
    private LocalDateTime thisMonday, chosenMonday;

    ArrayList<TimeInvestment> assigned;

    public static void main(String[] args) {
        launch(args);

    }

    public Frontpage() {

    }

    //Hovedmetoden der bliver kørt i gui'en.
    @Override
    public void start(Stage window) {
        printer.start();

        //Initialisere datoer
        thisMonday = today.withDayOfWeek(DateTimeConstants.MONDAY);

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

        //Tildeling af rum til ansatte for uge 16/2015:
        //Kør 'Røntgen projekt\DB\Script 3a - insert_shifts_week16-2015.sql'.
        try {
            assigned = Xray.getInstance().assignRooms();
        } catch (SQLException ex) {
            System.out.println("LORT1");
        } catch (ClassNotFoundException ex) {
            System.out.println("LORT2");
        }

        //tildel via assign rooms metode:
        //Opsætning af skema.
        hMenuLayout.setMinHeight(PopupMenuButton.PREFERRED_HEIGHT);
        schedule = new Schedule(assigned, today);
        vMainLayout.getChildren().add(schedule);

    }

    private void initNodes(Stage window) {
        //initialiser felter:
        this.window = window;
        vMainLayout = new VBox(STANDARD_PADDING);

        hMenuLayout = new HBox(STANDARD_PADDING);

        hWeekPicker = new HBox(STANDARD_PADDING);

        frontPageScene = new Scene(vMainLayout, screenWidth, screenHeight);
        vMainLayout.setPadding(new Insets(STANDARD_PADDING, STANDARD_PADDING, STANDARD_PADDING, STANDARD_PADDING));
        window.setTitle(TITLE);

        //initialiser knapper:
        initButtons();
        initCombobox();

        //Tilføj knapper til hMainLayout:
        hMenuLayout.setAlignment(Pos.CENTER_LEFT);

        vMainLayout.setAlignment(Pos.TOP_LEFT);
        vMainLayout.getChildren().addAll(hMenuLayout);

        //Tilføj combobox og pile til hWeekPicker:
        hWeekPicker.setAlignment(Pos.CENTER_LEFT);

        vMainLayout.getChildren().addAll(hWeekPicker);

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

        assignRoomsButton = new PopupMenuButton("Tildel vagter");
        assignRoomsButton.setOnAction(e -> {

        });

        menuButtons.add(assignRoomsButton);

        for (PopupMenuButton menuButton : menuButtons) {
            hMenuLayout.getChildren().add(menuButton);
        }

        jumpForwardWeek = new Button("Frem >");
        jumpForwardWeek.setOnAction(e -> {
            cWeek.getSelectionModel().selectNext();
            chosenMonday = (LocalDateTime) cWeek.getSelectionModel().getSelectedItem();

            vMainLayout.getChildren().remove(2);
            Schedule schedule1 = new Schedule(assigned, new LocalDateTime(chosenMonday));
            vMainLayout.getChildren().add(2, schedule1);
        });

        jumpBackWeek = new Button("< Tilbage");
        jumpBackWeek.setOnAction(e -> {
            cWeek.getSelectionModel().selectPrevious();
            chosenMonday = (LocalDateTime) cWeek.getSelectionModel().getSelectedItem();

            vMainLayout.getChildren().remove(2);
            Schedule schedule1 = new Schedule(assigned, new LocalDateTime(chosenMonday));
            vMainLayout.getChildren().add(2, schedule1);
        });

        printButton = new Button("Print skema");
        printButton.setOnAction(e -> {
            printer.print(schedule);
        });

        hWeekPicker.getChildren().addAll(jumpBackWeek, jumpForwardWeek, printButton);
    }

    private void initCombobox() {
        cWeek = new ComboBox();
        cWeek.setPrefWidth(170);

        ArrayList<LocalDateTime> mondaysHalfYearBack = getHalfYearBack(this.thisMonday);
        ArrayList<LocalDateTime> mondaysHalfYearForward = getHalfYearForwards(this.thisMonday);

        ArrayList<LocalDateTime> allMondays = new ArrayList<>();
        for (int i = 0; i < mondaysHalfYearBack.size(); i++) {
            allMondays.add(mondaysHalfYearBack.get(i));

        }
        for (int i = 0; i < mondaysHalfYearForward.size(); i++) {
            allMondays.add(mondaysHalfYearForward.get(i));
        }

        for (int i = 0; i < allMondays.size(); i++) {
            cWeek.getItems().add(allMondays.get(i));
        }
        cWeek.getSelectionModel().select((cWeek.getItems().size() - 1) >>> 1);
        hWeekPicker.getChildren().add(2, cWeek);

        cWeek.setOnAction(e -> {
            chosenMonday = (LocalDateTime) cWeek.getSelectionModel().getSelectedItem();

            vMainLayout.getChildren().remove(2);
            Schedule schedule1 = new Schedule(assigned, new LocalDateTime(chosenMonday));
            vMainLayout.getChildren().add(2, schedule1);
        });

    }

    public ArrayList<LocalDateTime> getHalfYearForwards(LocalDateTime thisMonday) {
        ArrayList<LocalDateTime> mondays = new ArrayList<>();
        LocalDateTime monday = thisMonday;

        for (int i = 0; i < 27; i++) {
            monday = thisMonday.plusWeeks(i);

            mondays.add(monday);
        }

        return mondays;
    }

    public ArrayList<LocalDateTime> getHalfYearBack(LocalDateTime thisMonday) {
        ArrayList<LocalDateTime> mondays = new ArrayList<>();
        LocalDateTime monday = thisMonday;

        for (int i = 26; i > 0; i--) {
            monday = thisMonday.minusWeeks(i);

            mondays.add(monday);
        }

        return mondays;
    }
}
