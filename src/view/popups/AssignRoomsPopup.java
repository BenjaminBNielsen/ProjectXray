/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.joda.time.LocalDateTime;
import view.buttons.PopupMenuButton;
import view.schema.ScheduleHeader;

/**
 *
 * @author Jonas
 */
public class AssignRoomsPopup extends PopupWindow {
    private ComboBox cBDateTimeStart, cBDateTimeEnd;
    private PopupMenuButton assignRooms;
    private VBox cBContainer;
    private LocalDateTime localDateTimeObject, localDateStart, localDateEnd;
    
    @Override
    public void display(String title) {
        cBDateTimeStart = new ComboBox();
        cBDateTimeEnd = new ComboBox();
        assignRooms = new PopupMenuButton("Tilføj inaktive rum i tidsperioden");
        localDateTimeObject = LocalDateTime.now();
        
        Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>> cellFactory = new Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>>() {
            @Override
            public ListCell<LocalDateTime> call(ListView<LocalDateTime> param) {

                return new ListCell<LocalDateTime>() {
                    @Override
                    public void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {

                            String value = ScheduleHeader.WEEK_DAY_NAMES[item.getDayOfWeek() - 1];
                            value = value.replaceFirst(value.substring(1, value.length()),
                                    value.substring(1, value.length()).toLowerCase());

                            setText("Uge " + item.getWeekOfWeekyear() + " den " + item.getDayOfMonth() + "/" + item.getMonthOfYear() + " - " + value);
                        }
                    }

                };
            }
        };
        
        cBDateTimeStart.setButtonCell(cellFactory.call(null));
        cBDateTimeStart.setCellFactory(cellFactory);
        
        Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>> cellFactory2 = new Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>>() {
            @Override
            public ListCell<LocalDateTime> call(ListView<LocalDateTime> param) {

                return new ListCell<LocalDateTime>() {
                    @Override
                    public void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            String value = ScheduleHeader.WEEK_DAY_NAMES[item.getDayOfWeek() - 1];
                            value = value.replaceFirst(value.substring(1, value.length()),
                                    value.substring(1, value.length()).toLowerCase());
                            setText("Uge " + item.getWeekOfWeekyear() + " den " + item.getDayOfMonth() + "/" + item.getMonthOfYear() + " - " + value);
                        }
                    }

                };
            }
        };

        cBDateTimeEnd.setButtonCell(cellFactory2.call(null));
        cBDateTimeEnd.setCellFactory(cellFactory2);
        
        setBottomHBoxPadding(15, 15, 15, 15);
        cBContainer = new VBox(20);
        cBContainer.setAlignment(Pos.CENTER);
        cBContainer.setPadding(new Insets(15,15,15,15));
        super.addToCenter(cBContainer);
        cBContainer.getChildren().addAll(cBDateTimeStart, cBDateTimeEnd);
        super.addToBottomHBox(assignRooms);
        
        assignRooms.setOnAction(e -> {
            
        });
    }
}
