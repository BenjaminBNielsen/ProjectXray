/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups;

import view.buttons.PopupMenuButton;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class PopupWindow {

    public static final int COLUMN_STANDARD_WIDTH = 200;

    private Stage window;
    private Scene roomScene;
    private BorderPane roomBorderPane;
    private PopupMenuButton tilbageTilHovedmenu;
    private HBox bottomHBox;

    public PopupWindow() {
        window = new Stage();
        roomBorderPane = new BorderPane();
        roomScene = new Scene(roomBorderPane);
        bottomHBox = new HBox(15);
        window.initModality(Modality.APPLICATION_MODAL);
        bottomHBox.setAlignment(Pos.CENTER);

        roomBorderPane.setPadding(new Insets(15, 15, 15, 15));
        roomBorderPane.setBottom(bottomHBox);

        tilbageTilHovedmenu = new PopupMenuButton("Tilbage til forsiden");
        tilbageTilHovedmenu.setOnAction(e -> {
            window.close();
        });

        bottomHBox.getChildren().addAll(tilbageTilHovedmenu);
        window.setScene(roomScene);
    }

    public void display(String title) {

        window.setTitle(title);
        window.showAndWait();
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

    public Scene getRoomScene() {
        return roomScene;
    }

    public void setScene(Scene scene) {
        window.setScene(scene);
    }

    public Stage getStage() {
        return window;
    }

    public void setBottomHBoxPadding(int first, int second, int third, int fourth) {
        bottomHBox.setPadding(new Insets(first, second, third, fourth));
    }

    public void maximizeScreen() {
        window.setMaximized(true);
    }
    
    public HBox getBottomHBox() {
        return bottomHBox;
    }
}
