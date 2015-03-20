/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import view.buttons.PopupMenuButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Jonas
 */
public class ExceptionPopup {
    private Stage window;
    private Scene exceptionScene;
    private PopupMenuButton button;
    private BorderPane borderPane;
    private HBox hBox;
    
    public ExceptionPopup() {
        window = new Stage();
        borderPane = new BorderPane();
        exceptionScene = new Scene(borderPane);
        button = new PopupMenuButton("Forstået");
        window.initModality(Modality.APPLICATION_MODAL);
        hBox = new HBox(15);
        hBox.getChildren().add(button);
    }
    
    public void display(String message){
        Label errorMessage = new Label(message);
        window.setTitle("Fejlbesked");
        borderPane.setPadding(new Insets(15,15,15,15));
        borderPane.setBottom(hBox);
        borderPane.setCenter(errorMessage);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(15,15,15,15));
        button.setOnAction(e -> {
            window.close();
        });
        window.setScene(exceptionScene);
        window.showAndWait();
    }        
}
