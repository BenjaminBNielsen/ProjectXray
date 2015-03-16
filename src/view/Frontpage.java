package view;

import java.util.ArrayList;
import javafx.application.*;
import static javafx.application.Application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Frontpage extends Application {

    public static final String TITLE = "Planlægning";

    private Stage window;

    //scener:
    private Scene frontPageScene;

    //layout-panes:
    private VBox vMainLayout;
    private HBox hMenuLayout;

    //buttons:
    private MenuButton createPersonButton, createQualificationButton, createRoomButton;

    public static void main(String[] args) {
        launch(args);
    }

    //Hovedmetoden der bliver kørt i gui'en.
    @Override
    public void start(Stage window) {
        initNodes(window);
    }

    private void initNodes(Stage window) {
        //initialiser felter:
        this.window = window;
        vMainLayout = new VBox(20);
        hMenuLayout = new HBox(15);
        frontPageScene = new Scene(vMainLayout, 1024, 768);

        window.setTitle(TITLE);

        //initialiser knapper:
        initButtons();

        //Sætter padding på hboxen sådan at den ikke placeres direkte op af vinduekanten.
        hMenuLayout.setPadding(new Insets(15, 15, 15, 15));

        //Tilføj knapper til hMainLayout:
        hMenuLayout.setAlignment(Pos.CENTER_LEFT);

        vMainLayout.setAlignment(Pos.TOP_LEFT);
        vMainLayout.getChildren().add(hMenuLayout);

        window.setScene(frontPageScene);
        window.show();
    }

    private void initButtons() {

        ArrayList<MenuButton> menuButtons = new ArrayList<>();

        createPersonButton = new MenuButton("Opret ansat");
        createPersonButton.setOnAction(e -> {
            PersonPopup personPopup = new PersonPopup();
            personPopup.display("Opret ansat");
        });
        menuButtons.add(createPersonButton);

        createQualificationButton = new MenuButton("Opret kvalifikation");
        createQualificationButton.setOnAction(e -> {
            System.out.println("hej2");
        });
        menuButtons.add(createQualificationButton);

        createRoomButton = new MenuButton("Opret rum");
        createRoomButton.setOnAction(e -> {
            RoomWindow roomWindow = new RoomWindow();
            roomWindow.display("Rum");
        });
        menuButtons.add(createRoomButton);

        for (MenuButton menuButton : menuButtons) {
            hMenuLayout.getChildren().add(menuButton);
        }
    }
}
