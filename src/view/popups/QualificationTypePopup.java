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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Qualification;
import model.QualificationType;
import view.buttons.AddButton;
import view.buttons.PopupMenuButton;
import view.buttons.RemoveButton;

/**
 *
 * @author Yousef
 */
public class QualificationTypePopup extends PopupWindow {

    private VBox vBoxLeft, vBoxMid, vBoxRight;
    private PopupMenuButton createQualificationType;
    private TextField textQTName;
    private Label lQTType;
    private ObservableList<QualificationType> qTItems;
    private ListView<QualificationType> qTList;
    private AddButton addToListView;
    private RemoveButton removeFromListView;
    private ExceptionPopup exceptionPopup;

    public QualificationTypePopup() {
        exceptionPopup = new ExceptionPopup();
    }
    @Override
    public void display(String title) {

        initLabels();
        initTextFields();
        initListViews();
        initButtons();
        initLayouts();

        super.addToLeft(vBoxLeft);
        super.addToCenter(vBoxMid);
        super.addToRight(vBoxRight);
        super.addToBottomHBox(createQualificationType);
        super.display(title);
        

    }

    public void addQualifictionTypeToList() {
        try {
            System.out.println(""+Xray.getInstance().getQualificationControl().getQTRow() +1);
//            String qTName = textQTName.getText();
//            int id = 1;
//            
//           // id = Xray.getInstance().getQualificationControl().getQualificationTypes().size() + 1;
//
//            QualificationType qualificationType = new QualificationType(id, qTName);
//            qTItems.add(qualificationType);
//            qTList.setItems(qTItems);
        } catch (SQLException ex) {
            exceptionPopup.display("");
        } catch (ClassNotFoundException ex) {
            exceptionPopup.display("");
        }
            
    }

//    public void removeQualifictionFromList() {
//        QualificationType qualification = qList.getSelectionModel().getSelectedItem();
//
//        QualificationType type = qualification.getType();
//        textFQName.setText(type.getType());
//        Boolean training = qualification.isTraining();
//        trainingCb.setSelected(training);
//        Room room = qualification.getRoom();
//
//        qItems.remove(qualification);
//        qList.setItems(qItems);
//    }
    private void initLabels() {
        lQTType = new Label("Skriv navnet på den nye kvalifikation");
    }

    private void initTextFields() {
        textQTName = new TextField();
    }

    private void initListViews() {
        qTList = new ListView<>();
        qTItems = FXCollections.observableArrayList();
        qTList.setPrefSize(200, 300);
        qTList.setItems(qTItems);
    }

    private void initButtons() {
        addToListView = new AddButton();
        addToListView.setOnAction(e -> {
            addQualifictionTypeToList();
        });

//        removeFromListView = new RemoveButton();
//        removeFromListView.setOnAction(e -> {
//            removeQualifictionFromList();
//        });
        createQualificationType = new PopupMenuButton("Opret kvalifikationer");
        createQualificationType.setOnAction(e -> {
            try {
                Xray.getInstance().getQualificationControl().addQualificationTypes(qTItems);
            } catch (SQLException ex) {
                exceptionPopup.display("Kvalifikationen kunne ikke indsættes, kontakt systemadministrator.");

            } catch (ClassNotFoundException ex) {
                exceptionPopup.display("Der kunne ikke oprettes forbindelse til databasen, "
                        + "kontakt venligst din systemadministrator.");
            }
        });
    }

    private void initLayouts() {
        vBoxLeft = new VBox(20);
        vBoxLeft.setPadding(new Insets(15, 15, 15, 0));
        vBoxLeft.setAlignment(Pos.CENTER_LEFT);
        vBoxLeft.getChildren().addAll(lQTType, textQTName);

        vBoxMid = new VBox(20);
        vBoxMid.setPadding(new Insets(15, 15, 15, 15));
        vBoxMid.setAlignment(Pos.CENTER_LEFT);
        vBoxMid.getChildren().addAll(addToListView);

        vBoxRight = new VBox(20);
        vBoxRight.setPadding(new Insets(15, 0, 15, 15));
        vBoxRight.setAlignment(Pos.CENTER_LEFT);
        vBoxRight.getChildren().addAll(qTList);
    }
}
