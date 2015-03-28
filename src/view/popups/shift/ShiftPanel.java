/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups.shift;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.Shift;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import view.buttons.SettingsButton;

/**
 *
 * @author Benjamin
 */
public class ShiftPanel extends HBox {

    public static final Hours DAY_HOURS = Hours.hours(8);
    public static final Minutes DAY_MINUTES = Minutes.minutes(0);
    public static final Hours EVENING_HOURS = Hours.hours(8);
    public static final Minutes EVENING_MINUTES = Minutes.minutes(0);
    public static final Hours NIGHT_HOURS = Hours.hours(8);
    public static final Minutes NIGHT_MINUTES = Minutes.minutes(0);

    private LocalDateTime startTime;
    private int dayOfWeek;

    //Buttons:
    private Button dayShift, eveningShift, nightShift;
    private SettingsButton settingsButton;

    //Label:
    private Label lDayName;

    private Shift shift;

    //SettingsPopup
    ShiftPanelConfig configPanel;

    public ShiftPanel(int dayOfWeek, String dayName, /*Vil altid være en mandag*/ LocalDateTime startTime) {
        super(15);
        configPanel = new ShiftPanelConfig();
        lDayName = new Label(dayName);
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        
        initButtons();
        setup();

    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    private void initButtons() {
        settingsButton = new SettingsButton();
        settingsButton.setDisable(true);

        settingsButton.setOnAction(e -> {

            configPanel.display("Foretag ændring");

        });

        dayShift = new Button("Dagvagt");
        dayShift.setOnAction(e -> {

            int id = 1;
            //For at finde datoen og tiden på ugen skal der lægges nogle elementer til.
            //Først lægges dayOfWeek til en ny variabel d. Så hvis det fx er onsdag er
            //dayOfWeek 3 og der lægges derfor 3-1 til mandagen som er dayOfWeek 1,
            //Dette gør at man for den dato som onsdagen ligger på.
            LocalDateTime d = startTime.plusDays(dayOfWeek - 1);
            //Dernæst defineres starttidspunktet. Dagsvagter starter per default 8:30.
            d = d.plusHours(8);
            d = d.plusMinutes(30);
            shift = new Shift(id, DAY_HOURS, DAY_MINUTES, d);

            settingsButton.setDisable(false);
        });
        eveningShift = new Button("Aftenvagt");
        eveningShift.setOnAction(e -> {
            int id = 1;
            LocalDateTime d = startTime.plusDays(dayOfWeek - 1);
            d = d.plusHours(15);
            d = d.plusMinutes(15);
            shift = new Shift(id, DAY_HOURS, DAY_MINUTES, d);

            settingsButton.setDisable(false);
        });
        nightShift = new Button("Nattevagt");
        nightShift.setOnAction(e -> {
            int id = 1;
            LocalDateTime d = startTime.plusDays(dayOfWeek - 1);
            d = d.plusHours(23);
            d = d.plusMinutes(30);
            shift = new Shift(id, DAY_HOURS, DAY_MINUTES, d);

            settingsButton.setDisable(false);

        });

        configPanel.getChangeButton().setOnAction(e -> {
            LocalDateTime ld = shift.getLocalDate();
            
            //Nulstiller timer.
            ld = ld.minusHours(ld.getHourOfDay());
            
            //Nulstiller minuttet.
            ld = ld.minusMinutes(ld.getMinuteOfHour());
            
            //Dernæst skal det som der er indtastet i configpopuppen tilføjes til 
            //den nulstillede dato.
            int modifiedHour = Integer.parseInt(configPanel.gettStartHour().getText());
            
            ld = ld.plusHours(modifiedHour);
            
            int modifiedMinute = Integer.parseInt(configPanel.gettStartMinute().getText());
            
            ld = ld.plusMinutes(modifiedMinute);
            
            shift.setLocalDate(ld);
        });
    }

    private void setup() {
        super.getChildren().addAll(dayShift, eveningShift, nightShift, settingsButton);
    }
}
