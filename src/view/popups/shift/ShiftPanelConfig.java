/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups.shift;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.buttons.PopupMenuButton;
import view.popups.ExceptionPopup;
import view.popups.PopupWindow;

/**
 *
 * @author Benjamin
 */
public class ShiftPanelConfig extends PopupWindow {

    //Laoyouts
    private HBox mainContent, leftTextboxes, rightTextboxes;
    private VBox leftPane, rightPane;

    //Labels
    private Label lStartTime, lEndTime;

    //Textboxes
    private TextField tStartHour, tStartMinute, tEndHour, tEndMinute;

    //Knapper
    private PopupMenuButton changeButton;

    //ExceptionPopup
    private ExceptionPopup exceptionPopup = new ExceptionPopup();

    public ShiftPanelConfig() {
        initLayouts();
        initLabels();
        initTextFields();
        initButtons();
        setup();

        exceptionPopup = new ExceptionPopup();
    }

    private void initLayouts() {
        mainContent = new HBox(15);
        leftTextboxes = new HBox(7.5);
        leftTextboxes.setPadding(new Insets(0, 0, 15, 0));
        rightTextboxes = new HBox(7.5);
        rightTextboxes.setPadding(new Insets(0, 0, 15, 0));

        leftPane = new VBox(25);
        rightPane = new VBox(25);
    }

    private void initLabels() {
        lStartTime = new Label("Starttidspunkt");
        lEndTime = new Label("Sluttidspunkt");
    }

    private void initTextFields() {
        tStartHour = new TextField("HH");
        tStartMinute = new TextField("MM");

        tEndHour = new TextField("HH");
        tEndMinute = new TextField("MM");
    }

    private void initButtons() {
        changeButton = new PopupMenuButton("Foretag ændring");
    }

    public HBox getMainContent() {
        return mainContent;
    }

    public void setMainContent(HBox mainContent) {
        this.mainContent = mainContent;
    }

    public HBox getLeftTextboxes() {
        return leftTextboxes;
    }

    public void setLeftTextboxes(HBox leftTextboxes) {
        this.leftTextboxes = leftTextboxes;
    }

    public HBox getRightTextboxes() {
        return rightTextboxes;
    }

    public void setRightTextboxes(HBox rightTextboxes) {
        this.rightTextboxes = rightTextboxes;
    }

    public VBox getLeftPane() {
        return leftPane;
    }

    public void setLeftPane(VBox leftPane) {
        this.leftPane = leftPane;
    }

    public VBox getRightPane() {
        return rightPane;
    }

    public void setRightPane(VBox rightPane) {
        this.rightPane = rightPane;
    }

    public Label getlStartTime() {
        return lStartTime;
    }

    public void setlStartTime(Label lStartTime) {
        this.lStartTime = lStartTime;
    }

    public Label getlEndTime() {
        return lEndTime;
    }

    public void setlEndTime(Label lEndTime) {
        this.lEndTime = lEndTime;
    }

    public TextField gettStartHour() {
        return tStartHour;
    }

    public void settStartHour(TextField tStartHour) {
        this.tStartHour = tStartHour;
    }

    public TextField gettStartMinute() {
        return tStartMinute;
    }

    public void settStartMinute(TextField tStartMinute) {
        this.tStartMinute = tStartMinute;
    }

    public TextField gettEndHour() {
        return tEndHour;
    }

    public void settEndHour(TextField tEndHour) {
        this.tEndHour = tEndHour;
    }

    public TextField gettEndMinute() {
        return tEndMinute;
    }

    public void settEndMinute(TextField tEndMinute) {
        this.tEndMinute = tEndMinute;
    }

    public PopupMenuButton getChangeButton() {
        return changeButton;
    }

    public void setChangeButton(PopupMenuButton changeButton) {
        this.changeButton = changeButton;
    }

    public ExceptionPopup getExceptionPopup() {
        return exceptionPopup;
    }

    public void setExceptionPopup(ExceptionPopup exceptionPopup) {
        this.exceptionPopup = exceptionPopup;
    }

    private void setup() {
        super.addToTop(mainContent);
        super.addToBottomHBox(changeButton);

        mainContent.getChildren().addAll(leftPane, rightPane);
        leftPane.getChildren().addAll(lStartTime, leftTextboxes);
        rightPane.getChildren().addAll(lEndTime, rightTextboxes);

        leftTextboxes.getChildren().addAll(tStartHour, tStartMinute);
        rightTextboxes.getChildren().addAll(tEndHour, tEndMinute);
    }
}
