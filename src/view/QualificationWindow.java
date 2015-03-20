/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.Xray;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Qualification;
import model.QualificationType;
import model.Room;

/**
 *
 * @author Yousef
 */
public class QualificationWindow extends PopupWindow {

    private MenuButton createQualification;
    private TextField textFQName;
    private Label lQType, lQTraining, lQRoom, errorMsg;
    private CheckBox trainingCb;
    private ObservableList<Room> rItems;
    private ObservableList<Qualification> qItems;
    private ListView<Qualification> qList;
    private ListView<Room> rList;
    private ArrayList<Room> rooms;
    private Button addToListView;

    @Override
    public void display(String title) {
//        try {
//            rooms = Xray.getInstance().getRoomControl().getRooms(rooms);
//        } catch (SQLException ex) {
//            Logger.getLogger(QualificationWindow.class.getName()).log(Level.SEVERE, null, ex);
//            errorMsg.setText("Der er opstået en fejl:"+ex.getMessage());
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(QualificationWindow.class.getName()).log(Level.SEVERE, null, ex);
//              errorMsg.setText(errorMsg.getText() + "Der er opstået en fejl:"+ex.getMessage());
//            
//        };

        /*TestRum*/
        Room room1 = new Room(7728662, "JarlFriisNielsensRum", 2434);
        Room room2 = new Room(14443, "TheUndertaker", 1222);
        Room room3 = new Room(123, "Ostemaden", 123);

        lQType = new Label("Skriv venligst navnet på den nye kvalifikation");
        textFQName = new TextField();
        textFQName.setMaxWidth(200);
        lQTraining = new Label("Vælg om kvalifikationen er for medarbejdere under træning");
        trainingCb = new CheckBox("Kvalifikation til træning");
        lQRoom = new Label("Vælg tilhørende rum (vælg ingen hvis kvalifikationen er uafhængigt af rum)");

        rList = new ListView<>();
        rItems = FXCollections.observableArrayList(room1, room2, room3 /*rooms*/);
        rList.setItems(rItems);

        qList = new ListView<>();
        qItems = FXCollections.observableArrayList();
        qList.setItems(qItems);
//        errorMsg.setText("Error");
//        while(errorMsg != null){
//            errorMsg = new Label();
//            errorMsg.setTextFill(Color.web("#ff0000"));
//                }

        addToListView = new Button(">>>");
        addToListView.setPrefSize(80, 50);
        addToListView.setOnAction(e -> {
            addQualifictionToList();
        });

        VBox vBoxLeft = new VBox(20);
        vBoxLeft.setAlignment(Pos.CENTER);
        super.addToLeft(vBoxLeft);
        vBoxLeft.getChildren().addAll(lQType, textFQName, lQTraining, trainingCb, lQRoom, rList);

        VBox vBoxMid = new VBox(20);
        vBoxMid.setAlignment(Pos.CENTER);
        super.addToCenter(vBoxMid);
        vBoxMid.getChildren().addAll(addToListView);

        VBox vBoxRight = new VBox(20);
        vBoxRight.setAlignment(Pos.TOP_RIGHT);
        super.addToRight(vBoxRight);
        vBoxRight.getChildren().addAll(qList);

        createQualification = new MenuButton("Dan skrevne kvalifikationer");
        createQualification.setOnAction(e -> {
            createQualification.setText("Wow!");
            ArrayList<Qualification> createdQualifications = new ArrayList<>();
            createdQualifications.addAll(qItems);
            System.out.println(createdQualifications);
            for (int i = 0; i < createdQualifications.size(); i++) {
            createQualifications(createdQualifications.get(i));    
            }
            
        });

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

    public void createQualifications(Qualification qualification) {

        QualificationType type = qualification.getType();
        Boolean training = qualification.isTraining();
        Room room = qualification.getRoom();

        try {
            Xray.getInstance().getQualificationControl().createQualification(type, training, room);
        } catch (SQLException ex) {
            Logger.getLogger(QualificationWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QualificationWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
