/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups;

import control.Xray;
import exceptions.DatabaseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.joda.time.LocalDateTime;
import view.buttons.PopupMenuButton;
import view.schema.ScheduleHeader;

/**
 *
 * @author Benjamin
 */
public class TimeInvestmentPopup extends PopupWindow {

    private ComboBox<LocalDateTime> cStart, cEnd;
    private PopupMenuButton assignButton;

    public TimeInvestmentPopup() {
        HBox mainHbox = new HBox(15);
        mainHbox.setPadding(new Insets(0,0,15,0));

        cStart = new ComboBox();
        cEnd = new ComboBox();
        assignButton = new PopupMenuButton("Tildel vagter");
        cStart.setPrefWidth(assignButton.getPrefWidth());
        cEnd.setPrefWidth(assignButton.getPrefWidth());
        
        mainHbox.getChildren().addAll(cStart, cEnd);
        super.addToTop(mainHbox);
        super.addToBottomHBox(assignButton);
        
        fillContent();
        initListeners();
    }

    private void fillContent() {
        //Fyld datoer ind i comboboks til start og slutdato:
        LocalDateTime now = new LocalDateTime();
        LocalDateTime oneWeekBack = now.minusMonths(12);
        LocalDateTime oneMonthForward = now.plusMonths(12);
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
                            setText(item.toString("dd/MM/yy") + " " + dayName.substring(0, 1) + dayName.substring(1).toLowerCase());
                        }
                    }

                };
            }
        };

        Xray.getInstance().fillDatesInEndDate(cEnd, cStart);

        cStart.setButtonCell(cellFactory.call(null));
        cStart.setCellFactory(cellFactory);
        cEnd.setButtonCell(cellFactory.call(null));
        cEnd.setCellFactory(cellFactory);

    }

    private void initListeners() {

        cStart.setOnAction(e -> {
            Xray.getInstance().fillDatesInEndDate(cEnd, cStart);
        });
        
        assignButton.setOnAction(e -> {
            LocalDateTime startDate = cStart.getValue();
            LocalDateTime endDate = cEnd.getValue();
            try {
                Xray.getInstance().getTimeInvestmentControl().assignRooms(startDate, endDate);
            } catch (DatabaseException ex) {
                ExceptionPopup ep = new ExceptionPopup();
                ep.display(ex.getMessage());
            }
        });
    }
}
