/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;


import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class PopupWindow {
    private Stage window;
    private Scene roomScene;
    private BorderPane roomBorderPane;
    private MenuButton tilbageTilHovedmenu;
    private HBox bottomHBox;
    
    public PopupWindow() {
        window = new Stage();
        roomBorderPane = new BorderPane();
        roomScene = new Scene(roomBorderPane, 512, 384);
        bottomHBox = new HBox(15);
    }
    
    public void display(String title) {
        bottomHBox.setAlignment(Pos.CENTER);
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        
        roomBorderPane.setPadding(new Insets(15, 15, 15, 15));
        roomBorderPane.setBottom(bottomHBox);
        
        tilbageTilHovedmenu = new MenuButton("Tilbage til forsiden");
        tilbageTilHovedmenu.setOnAction(e -> {
            window.close();
        });
        
        bottomHBox.getChildren().addAll(tilbageTilHovedmenu);
        window.setScene(roomScene);
        window.showAndWait();
    }
    /* Vi har flere metoder som kan tilføje data til vores popupwindow,
       det betyder at vi har en skabelon som alle popupwindows skal laves ud fra
       i addToBottomHBox bruger vi parametren Nodes... som er var args(variable arguments)
       det betyder at man kan tilføje så mange variable som man vil i parametrene*/
    public void addToBottomHBox(Node... nodes){
        bottomHBox.getChildren().addAll(nodes);
    }
    
    public void addToTop(Node node) {
        roomBorderPane.setTop(node);
    }
    
    public void addToCenter(Node node) {
        roomBorderPane.setCenter(node);
    }
    
    public void addToLeft(Node node) {
        roomBorderPane.setLeft(node);
    }
    
    public void addToRight(Node node) {
        roomBorderPane.setRight(node);
    }
    
    public void addToBottom(Node node) {
        roomBorderPane.setBottom(node);
    }
}
