/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups.shift;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import model.Employee;
import model.TimeInvestment;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import view.buttons.ImageButton;
import view.buttons.SettingsButton;
import view.popups.ExceptionPopup;

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
    private Employee employee;
    private int dayOfWeek;

    //Buttons:
    private ImageButton dayShift, eveningShift, nightShift;
    private SettingsButton settingsButton;
    //CSS styles der viser nappen når den er trykket og normalt
    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
    private final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";

    //Label:
    private Label lDayName;

    private TimeInvestment shift;

    //SettingsPopup
    ShiftPanelConfig configPanel;

    private ExceptionPopup exceptionPopup;

    public ShiftPanel(int dayOfWeek, String dayName, /*Vil altid være en mandag*/ LocalDateTime startTime, Employee employee) {
        super(15);
        this.employee = employee;
        configPanel = new ShiftPanelConfig();
        lDayName = new Label(dayName);
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        exceptionPopup = new ExceptionPopup();

        initButtons();
        setup();

    }

    public TimeInvestment getShift() {
        return shift;
    }

    public void setShift(TimeInvestment shift) {
        this.shift = shift;
    }

    private void initButtons() {
        settingsButton = new SettingsButton();
        settingsButton.setDisable(true);

        settingsButton.setOnAction(e -> {

            configPanel.display("Foretag ændring");

        });

        dayShift = new ImageButton("pictures/morgen 60.png","pictures/morgen 60 dark.png");
        dayShift.setOnAction(e -> {
            eveningShift.setUnPressed();
            nightShift.setUnPressed();

            //For at finde datoen og tiden på ugen skal der lægges nogle elementer til.
            //Først lægges dayOfWeek til en ny variabel d. Så hvis det fx er onsdag er
            //dayOfWeek 3 og der lægges derfor 3-1 til mandagen som er dayOfWeek 1,
            //Dette gør at man for den dato som onsdagen ligger på.
            LocalDateTime d = startTime.plusDays(dayOfWeek - 1);
            //Dernæst defineres starttidspunktet. Dagsvagter starter per default 8:30.
            d = d.plusHours(8);
            d = d.plusMinutes(30);
            shift = new TimeInvestment(DAY_HOURS, DAY_MINUTES, d, employee, null);

            settingsButton.setDisable(false);
        });

        eveningShift = new ImageButton("pictures/aften 60.png", "pictures/aften 60 dark.png");
        eveningShift.setOnAction(e -> {
            dayShift.setUnPressed();
            nightShift.setUnPressed();
            
            LocalDateTime d = startTime.plusDays(dayOfWeek - 1);
            d = d.plusHours(15);
            d = d.plusMinutes(15);
            shift = new TimeInvestment(DAY_HOURS, DAY_MINUTES, d, employee, null);

            settingsButton.setDisable(false);
        });

        nightShift = new ImageButton("pictures/nat 60.png", "pictures/nat 60 dark.png");
        nightShift.setOnAction(e -> {
            dayShift.setUnPressed();
            eveningShift.setUnPressed();
            
            int id = 1;
            LocalDateTime d = startTime.plusDays(dayOfWeek - 1);
            d = d.plusHours(23);
            d = d.plusMinutes(30);
            shift = new TimeInvestment(DAY_HOURS, DAY_MINUTES, d, employee, null);

            settingsButton.setDisable(false);

        });

        configPanel.getChangeButton().setOnAction(e -> {
            makeChange();
        });
    }

    private void setup() {
        super.getChildren().addAll(dayShift, eveningShift, nightShift, settingsButton);
    }

    private void makeChange() {
        boolean inputError = false;

        LocalDateTime ldt = shift.getStartTime();

        //Nulstiller timer.
        ldt = ldt.minusHours(ldt.getHourOfDay());

        //Nulstiller minuttet.
        ldt = ldt.minusMinutes(ldt.getMinuteOfHour());

        shift.setStartTime(ldt);

        //Dernæst skal det som der er indtastet i configpopuppen tilføjes til 
        //den nulstillede dato.
        int modifiedStartHour = checkInput(configPanel.gettStartHour(), inputError, 23, 0);
        if (modifiedStartHour == -1) {
            inputError = true;
        }

        int modifiedStartMinute = checkInput(configPanel.gettStartMinute(), inputError, 59, 0);
        if (modifiedStartMinute == -1) {
            inputError = true;
        }

        int modifiedEndHour = checkInput(configPanel.gettEndHour(), inputError, 23, 0);
        if (modifiedEndHour == -1) {
            inputError = true;
        }

        int modifiedEndMinute = checkInput(configPanel.gettEndMinute(), inputError, 59, 0);
        if (modifiedEndMinute == -1) {
            inputError = true;
        }

        //Dernæst udregnes vagttiden, altså hvor mange timer og minutter
        //vagten tager fra starttidspunktet.
        if (!inputError) {
            shift.setHours(Hours.hours(modifiedEndHour - modifiedStartHour));
            shift.setMinutes(Minutes.minutes(modifiedEndMinute - modifiedStartMinute));
        }
    }

    private int checkInput(TextField textField, boolean inputError,
            int highestValue, int lowestValue) {
        String inputErrorMessage = "Der kan kun indtastes tal i de 4 felter";
        String wrongSizeIntError = "Der skal indtastes et tal mellem " + lowestValue
                + " og " + highestValue + ".";
        int output = 0;
        //Text fra det første intastningsfelt på :ShiftConfigPanel.
        String inputText = textField.getText();
        if (!inputError) {
            try {
                output = Integer.parseInt(inputText);
                //Man skal indtaste et gyldigt tidspunkt.
                if (output < lowestValue || output > highestValue) {
                    inputError = true;
                    exceptionPopup.display(wrongSizeIntError);
                }
            } catch (NumberFormatException ex) {
                exceptionPopup.display(inputErrorMessage);
                inputError = true;
            }
        }

        return (inputError) ? -1 : output;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
