/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups;

import control.Xray;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import view.popups.PopupWindow;
import view.buttons.PopupMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Qualification;
import model.QualificationType;
import model.Room;
import view.buttons.AddButton;
import view.buttons.RemoveButton;

/**
 *
 * @author Yousef
 */
public class QualificationPopup extends PopupWindow {

    private VBox vBoxLeft, vBoxMid, vBoxRight, vQName, vTraining, vRoom;
    private PopupMenuButton createQualification;
    private TextField textFQName;
    private Label lQType, lQRoom, lQualifications, lQualificationsNote, lEmpty;
    private CheckBox trainingCb;
    private ObservableList<Room> rItems;
    private ObservableList<Qualification> qItems;
    private ListView<Qualification> qList;
    private ListView<Room> rList;
    private ArrayList<Room> rooms;
    private AddButton addToListView;
    private RemoveButton removeFromListView;

    @Override
    public void display(String title) {

        initLabels();
        initTextFields();
        getRooms();
        initListViews();
        initButtons();
        initLayouts();

        super.addToLeft(vBoxLeft);
        super.addToCenter(vBoxMid);
        super.addToRight(vBoxRight);
        super.addToBottomHBox(createQualification);
        super.display(title);

    }

    public void addQualifictionToList() {
        QualificationType type = new QualificationType(textFQName.getText());
        Boolean training = trainingCb.isSelected();
        Room room = rList.getSelectionModel().getSelectedItem();

        Qualification qualification = new Qualification(type, training, null, room);
        qItems.add(qualification);
        qList.setItems(qItems);
    }

    public void removeQualifictionFromList() {
        Qualification qualification = qList.getSelectionModel().getSelectedItem();

        QualificationType type = qualification.getType();
        textFQName.setText(type.getType());
        Boolean training = qualification.isTraining();
        trainingCb.setSelected(training);
        Room room = qualification.getRoom();

        qItems.remove(qualification);
        qList.setItems(qItems);
    }

    public void createQualifications(Qualification qualification) {

        QualificationType type = qualification.getType();
        Boolean training = qualification.isTraining();
        Room room = qualification.getRoom();

        try {
            Xray.getInstance().getQualificationControl().createQualification(type, training, room);
        } catch (SQLException ex) {
            Logger.getLogger(QualificationPopup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QualificationPopup.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initLayouts() {

        vQName = new VBox(10);
        vQName.setAlignment(Pos.CENTER_LEFT);
        vQName.getChildren().addAll(lQType, textFQName);

        vTraining = new VBox(20);
        vTraining.setAlignment(Pos.CENTER_LEFT);
        vTraining.getChildren().addAll(trainingCb);

        vRoom = new VBox(10);
        vRoom.setAlignment(Pos.CENTER_LEFT);
        vRoom.getChildren().addAll(lQRoom, rList);

        vBoxLeft = new VBox(20);
        vBoxLeft.setPadding(new Insets(5, 0, 5, 0));
        vBoxLeft.setAlignment(Pos.CENTER_LEFT);
        vBoxLeft.getChildren().addAll(vQName, vTraining, vRoom);

        vBoxMid = new VBox(20);
        vBoxMid.setPadding(new Insets(5, 0, 5, 5));
        vBoxMid.setAlignment(Pos.CENTER_LEFT);
        vBoxMid.getChildren().addAll(addToListView, removeFromListView);

        vBoxRight = new VBox(20);
        vBoxRight.setPadding(new Insets(0, 0, 5, 5));
        vBoxRight.setAlignment(Pos.CENTER_LEFT);
        vBoxRight.getChildren().addAll(lQualifications, qList);
    }

    private void initLabels() {
        lQType = new Label("Skriv venligst navnet på den nye kvalifikation");
        trainingCb = new CheckBox("Kvalifikation til medarbejdere under træning");
        lQRoom = new Label("Vælg tilhørende rum (vælg ingen hvis kvalifikationen er uafhængigt af rum)");
        lQualifications = new Label("Valgte kvalifikationer");
        //lQualifications.setTextFill(Color.web("#ff0000"));
        //lQualifications.setFont(new Font("Cambria", 30));
        lEmpty = new Label("");
    }

    private void initTextFields() {
        textFQName = new TextField();
        textFQName.setMaxWidth(200);
    }

    private void getRooms() {

//        try {
//            rooms = Xray.getInstance().getRoomControl().getRooms(rooms);
//            rItems.addAll(rooms);
//            rList.setItems(rItems);
//
//        } catch (SQLException ex) {
//            Logger.getLogger(QualificationPopup.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(QualificationPopup.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    private void initListViews() {
        /*TestRum*/
        Room room1 = new Room(000, "Intet rum", 000);

        rList = new ListView<>();
        rItems = FXCollections.observableArrayList();
        rList.setPrefSize(400, 400);
        rList.setItems(rItems);

        qList = new ListView<>();
        qItems = FXCollections.observableArrayList();
        qList.setPrefSize(400, 550);
        qList.setItems(qItems);
    }

    private void initButtons() {
        addToListView = new AddButton();
        addToListView.setOnAction(e -> {
            addQualifictionToList();
        });

        removeFromListView = new RemoveButton();
        removeFromListView.setOnAction(e -> {
            removeQualifictionFromList();
        });
        createQualification = new PopupMenuButton("Dan kvalifikationer");
        createQualification.setOnAction(e -> {
            createQualification.setText("Wow!");
            ArrayList<Qualification> createdQualifications = new ArrayList<>();
            createdQualifications.addAll(qItems);
            System.out.println(createdQualifications);
            for (int i = 0; i < createdQualifications.size(); i++) {
                createQualifications(createdQualifications.get(i));
            }
        });
    }

}
