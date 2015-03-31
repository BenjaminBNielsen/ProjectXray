/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups.shift;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Employee;
import model.Shift;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
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
    private Button dayShift, eveningShift, nightShift;
    private SettingsButton settingsButton;

    //Label:
    private Label lDayName;

    private Shift shift;

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
            shift = new Shift(id, DAY_HOURS, DAY_MINUTES, d, employee);

            settingsButton.setDisable(false);
        });

        eveningShift = new Button("Aftenvagt");
        eveningShift.setOnAction(e -> {
            int id = 1;
            LocalDateTime d = startTime.plusDays(dayOfWeek - 1);
            d = d.plusHours(15);
            d = d.plusMinutes(15);
            shift = new Shift(id, DAY_HOURS, DAY_MINUTES, d, employee);

            settingsButton.setDisable(false);
        });

        nightShift = new Button("Nattevagt");
        nightShift.setOnAction(e -> {
            int id = 1;
            LocalDateTime d = startTime.plusDays(dayOfWeek - 1);
            d = d.plusHours(23);
            d = d.plusMinutes(30);
            shift = new Shift(id, DAY_HOURS, DAY_MINUTES, d, employee);

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

        LocalDateTime ld = shift.getLocalDate();

        //Nulstiller timer.
        ld = ld.minusHours(ld.getHourOfDay());

        //Nulstiller minuttet.
        ld = ld.minusMinutes(ld.getMinuteOfHour());
        
        shift.setLocalDate(ld);

        //Dernæst skal det som der er indtastet i configpopuppen tilføjes til 
        //den nulstillede dato.
        int modifiedStartHour = checkInput(configPanel.gettStartHour(), inputError, "HH");
        if(modifiedStartHour == -1){
            inputError = true;
        }

        int modifiedStartMinute = checkInput(configPanel.gettStartMinute(), inputError, "MM");
        if(modifiedStartMinute == -1){
            inputError = true;
        }
        
        int modifiedEndHour = checkInput(configPanel.gettEndHour(), inputError, "HH");
        if(modifiedEndHour == -1){
            inputError = true;
        }

        int modifiedEndMinute = checkInput(configPanel.gettEndMinute(), inputError, "MM");
        if(modifiedEndMinute == -1){
            inputError = true;
        }

        //Dernæst udregnes vagttiden, altså hvor mange timer og minutter
        //vagten tager fra starttidspunktet.
        if (!inputError) {
            shift.setHours(Hours.hours(modifiedEndHour - modifiedStartHour));
            shift.setMinutes(Minutes.minutes(modifiedEndMinute - modifiedStartMinute));
        }
    }

    private int checkInput(TextField textField, boolean inputError, String showText) {
        String inputErrorMessage = "Der kan kun indtastes tal i de 4 felter";
        String wrongSizeIntError = "Der skal indtastes et tal mellem 0 og 23";
        int output = 0;
        //Text fra det første intastningsfelt på :ShiftConfigPanel.
        String inputText = textField.getText();
        if (!inputText.equals(showText) && inputError == false) {
            try {
                output = Integer.parseInt(inputText);
                //Man skal indtaste et gyldigt tidspunkt.
                if(output < 0 || output > 23){
                    inputError = true;
                    exceptionPopup.display(wrongSizeIntError);
                }
            } catch (NumberFormatException ex) {
                exceptionPopup.display(inputErrorMessage);
                inputError = true;
            }
        } else if (!inputError) {
            inputError = true;
            exceptionPopup.display(inputErrorMessage);
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
