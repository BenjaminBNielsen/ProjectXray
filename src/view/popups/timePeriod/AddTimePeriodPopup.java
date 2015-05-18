/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups.timePeriod;

import com.sun.javafx.Utils;
import control.Xray;
import exceptions.DatabaseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.Employee;
import model.Room;
import model.TimePeriod;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import view.buttons.PopupMenuButton;
import view.popups.ExceptionPopup;
import view.popups.PopupWindow;
import view.schema.ScheduleHeader;

/* @author Benjamin */
public class AddTimePeriodPopup extends PopupWindow {

    private ComboBox<LocalDateTime> cStart, cEnd;
    private ComboBox<Employee> cEmp;
    private ComboBox<Room> cRoom;
    private ListView<Employee> lEmp;
    private RadioButton rbRepeat;
    private TextField amountRepeat, minAmount, maxAmount;
    private Button empAddButton, empRemoveButton;
    private PopupMenuButton addButton;
    private int min = 0, max = 0, repeat = 0;

    @Override
    public void display(String title) {
        initNodes();
        fillContent();
        initListeners();
        super.getStage().setResizable(false);
        super.display(title);
    }

    private void initNodes() {
        //Vis dette indhold.
        GridPane gridPane = new GridPane();
        addGridConstraints(gridPane);
        HBox leftHboxRepeat = new HBox(5);
        HBox minMax = new HBox(15);

        cStart = new ComboBox<>();
        cEnd = new ComboBox<>();
        cRoom = new ComboBox<>();
        cEmp = new ComboBox<>();

        lEmp = new ListView<>();

        rbRepeat = new RadioButton();
        amountRepeat = new TextField();
        minAmount = new TextField();
        minAmount.setText("Minimum");
        minAmount.setPrefWidth(80);
        maxAmount = new TextField();
        maxAmount.setText("Maximum");
        maxAmount.setPrefWidth(80);

        empAddButton = new Button("Tilføj");
        empRemoveButton = new Button("Fjern");

        amountRepeat.setDisable(true);

        addButton = new PopupMenuButton("Tilføj regel");

        //leftVbox;
        minMax.getChildren().addAll(maxAmount, minAmount);

        leftHboxRepeat.getChildren().addAll(rbRepeat, amountRepeat);
        Label startDateLabel = new Label("Vælg startdato");
        Label endDateLabel = new Label("Vælg slutdato");
        Label roomLabel = new Label("Vælg rum");
        Label minMaxLabel = new Label("Maksimum & minimum");
        Label repeatLabel = new Label("Gentag regel?");

        GridPane.setRowSpan(lEmp, 6);
        gridPane.setPadding(new Insets(0, 0, 10, 0));
        gridPane.addColumn(0, startDateLabel, cStart, endDateLabel, cEnd,
                roomLabel, cRoom, minMaxLabel, minMax, repeatLabel, leftHboxRepeat);
        gridPane.addColumn(1, new Label("Vælg medarbejder"), cEmp, empAddButton, empRemoveButton, lEmp);

        cStart.setMaxWidth(Double.MAX_VALUE);
        cEnd.setMaxWidth(Double.MAX_VALUE);
        cRoom.setMaxWidth(Double.MAX_VALUE);
        cEmp.setMaxWidth(Double.MAX_VALUE);
        empAddButton.setMaxWidth(Double.MAX_VALUE);
        empRemoveButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(cStart, Priority.ALWAYS);
        HBox.setHgrow(cEnd, Priority.ALWAYS);
        HBox.setHgrow(cRoom, Priority.ALWAYS);
        HBox.setHgrow(cEmp, Priority.ALWAYS);
        HBox.setHgrow(empAddButton, Priority.ALWAYS);
        HBox.setHgrow(empRemoveButton, Priority.ALWAYS);

        super.addToTop(gridPane);

        super.addToBottomHBox(addButton);
    }

    private void fillContent() {
        try {
            ArrayList<Employee> employees = Xray.getInstance().getPersonControl().getEmployees();

            for (int i = 0; i < employees.size(); i++) {
                cEmp.getItems().add(employees.get(i));
            }
            cEmp.getSelectionModel().selectFirst();
        } catch (DatabaseException ex) {
            new ExceptionPopup().display(ex.getMessage());
        }

        try {
            ArrayList<Room> rooms = Xray.getInstance().getRoomControl().getRooms();

            for (int i = 0; i < rooms.size(); i++) {
                cRoom.getItems().add(rooms.get(i));
            }
            cRoom.getSelectionModel().selectFirst();
        } catch (DatabaseException ex) {
            new ExceptionPopup().display(ex.getMessage());
        }

        //Fyld datoer ind i comboboks til start og slutdato:
        LocalDateTime now = new LocalDateTime();
        LocalDateTime oneWeekBack = now.minusDays(7);
        LocalDateTime oneMonthForward = now.plusMonths(1);
        ArrayList<LocalDateTime> startDates = Xray.getInstance().getDatesInPeriod(oneWeekBack, oneMonthForward);
        for (int i = 0; i < startDates.size(); i++) {
            cStart.getItems().add(startDates.get(i));
        }
        cStart.getSelectionModel().selectFirst();

        //Lav et cellfactory på comboboksen så datoerne vises overskueligt.
        Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>> cellFactory = new Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>>() {
            @Override
            public ListCell<LocalDateTime> call(ListView<LocalDateTime> param) {

                return new ListCell<LocalDateTime>() {
                    @Override
                    public void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            String dayName = ScheduleHeader.WEEK_DAY_NAMES[item.getDayOfWeek() - 1];
                            setText(item.toString("dd/MM") + " " + dayName.substring(0, 1) + dayName.substring(1).toLowerCase());
                        }
                    }

                };
            }
        };

        Xray.getInstance().fillDatesInEndDate(cEnd, cStart, 1);

        cStart.setButtonCell(cellFactory.call(null));
        cStart.setCellFactory(cellFactory);
        cEnd.setButtonCell(cellFactory.call(null));
        cEnd.setCellFactory(cellFactory);

    }

    private void initListeners() {
        rbRepeat.setOnAction(e -> {
            if (rbRepeat.isSelected()) {
                amountRepeat.setDisable(false);
            } else {
                amountRepeat.setDisable(true);
            }
        });

        cStart.setOnAction(e -> {
            Xray.getInstance().fillDatesInEndDate(cEnd, cStart, 1);
        });

        empAddButton.setOnAction(e -> {
            lEmp.getItems().add(cEmp.getValue());
            cEmp.getItems().remove(cEmp.getValue());
            cEmp.getSelectionModel().selectFirst();
        });

        empRemoveButton.setOnAction(e -> {
            Employee employee = lEmp.getSelectionModel().getSelectedItem();
            if (employee != null) {
                cEmp.getItems().add(employee);
                lEmp.getItems().remove(employee);
                lEmp.getSelectionModel().clearSelection();
            }
        });

        addButton.setOnAction(e -> {
            ArrayList<TimePeriod> timePeriods = getRepeatedTimeInvestments();

            if (!timePeriods.isEmpty()) {
                boolean succes = false;
                try {
                    Xray.getInstance().getTimePeriodControl().addTimePeriods(timePeriods);
                    succes = true;
                } catch (DatabaseException ex) {
                    ExceptionPopup ep = new ExceptionPopup();
                    ep.display(ex.getMessage());
                }
                if (succes) {
                    ExceptionPopup ep = new ExceptionPopup();
                    ep.display("Reglen er oprettet.");
                }
            } else {
                ExceptionPopup ep = new ExceptionPopup();
                ep.display("Reglen er ikke oprettet da der ikke er valgt nogen medarbejdere.");
            }

        });

        minAmount.setOnMouseClicked(e -> {
            if (minAmount.isFocused()) {
                minAmount.selectAll();
            }
        });

        minAmount.setOnKeyReleased(e -> {
            boolean failed = false;
            try {
                //Absolut værdi for at sikre et positivt tal.
                min = Math.abs(Integer.parseInt(minAmount.getText()));
                minAmount.setStyle("");
            } catch (NumberFormatException ex) {
                failed = true;
            }

            if (failed || min > max) {
                minAmount.setStyle("-fx-border-color: red;"
                        + "-fx-focus-color: transparent;"
                        + "-fx-faint-focus-color: transparent;");
            }
        });

        maxAmount.setOnKeyReleased(e -> {
            boolean failed = false;
            try {
                //Absolut værdi for at sikre et positivt tal.
                max = Math.abs(Integer.parseInt(maxAmount.getText()));
                maxAmount.setStyle("");
            } catch (NumberFormatException ex) {
                failed = true;
            }

            if (failed || min > max) {
                maxAmount.setStyle("-fx-border-color: red;"
                        + "-fx-focus-color: transparent;"
                        + "-fx-faint-focus-color: transparent;");
            }
        });

        amountRepeat.setOnKeyReleased(e -> {
            boolean failed = false;
            try {
                //Absolut værdi for at sikre et positivt tal.
                repeat = Math.abs(Integer.parseInt(amountRepeat.getText()));
                amountRepeat.setStyle("");
            } catch (NumberFormatException ex) {
                failed = true;
            }

            if (failed || repeat > 100) {
                amountRepeat.setStyle("-fx-border-color: red;"
                        + "-fx-focus-color: transparent;"
                        + "-fx-faint-focus-color: transparent;");
            }
        });

        maxAmount.setOnMouseClicked(e -> {
            if (maxAmount.isFocused()) {
                maxAmount.selectAll();
            }
        });

        amountRepeat.setOnMouseClicked(e -> {
            if (amountRepeat.isFocused()) {
                amountRepeat.selectAll();
            }
        });
    }

    /**
     * Tilføj de nødvendige constratints på grid feltet. Sådan at den kolonne længst mod venstre, er 160 px lang, og de sidste 5 kolonner udnytter resten af pladsen ved brug af setHgrow
     */
    private void addGridConstraints(GridPane grid) {
        for (int i = 0; i < 2; i++) {
            ColumnConstraints tempCon = new ColumnConstraints(200, 200, Double.MAX_VALUE);
            tempCon.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(tempCon);
        }
        grid.setHgap(15);

        for (int i = 0; i < 10; i++) {
            double rowHeight = 40;
            RowConstraints tempCon = new RowConstraints(rowHeight, rowHeight, rowHeight * 6);
            tempCon.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(tempCon);
        }

    }

    /**
     * Henter timeperiods, og tager højde for hvad der er valgt i forbindelse
     * med om den oprettede regel skal gentages eller ej.
     * @return en liste af tidsperioder.
     */
    private ArrayList<TimePeriod> getRepeatedTimeInvestments() {
        LocalDateTime startTime = cStart.getValue();
        LocalDateTime endTime = cEnd.getValue();
        Room room = cRoom.getValue();
        ObservableList<Employee> employees = lEmp.getItems();
        ArrayList<TimePeriod> timePeriods = new ArrayList<>();
        
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            if (!cEmp.isDisabled()) {
                if (rbRepeat.isSelected()) {
                    if (repeat > 0) {
                        for (int j = 0; j < repeat; j++) {
                            TimePeriod tpToAdd = new TimePeriod(startTime, endTime,
                                    min, max, room, employee);
                            int daysBetween = Days.daysBetween(startTime, endTime).getDays();
                            startTime = endTime.plusDays(1);
                            endTime = startTime.plusDays(daysBetween);

                            timePeriods.add(tpToAdd);
                        }
                    }

                } else {
                    TimePeriod tpToAdd = new TimePeriod(startTime, endTime,
                            min, max, room, employee);

                    timePeriods.add(tpToAdd);
                }
            } else {
                break;
            }
        }
        return timePeriods;
    }
}
