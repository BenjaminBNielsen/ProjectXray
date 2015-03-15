package view;

import com.sun.javafx.geom.Shape;
import javafx.application.*;
import static javafx.application.Application.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Frontpage extends Application {

    public static final String TITLE = "Planlægning";

    private Stage window;

    //scener:
    private Scene frontPageScene;

    //buttons:
    private MenuButton createPersonButton, createQualificationButton;

    public static void main(String[] args) {
        launch(args);
    }

    //Hovedmetoden der bliver kørt i gui'en.
    @Override
    public void start(Stage window) {
        initNodes(window);
    }

    private void initNodes(Stage window) {
        initButtons();
        this.window = window;

        window.setTitle(TITLE);

        //VBox layout som standard.
        VBox vMainLayout = new VBox(20);
        HBox hMainLayout = new HBox(10);

        //Tilføj knapper til hMainLayout:
        hMainLayout.getChildren().addAll(createPersonButton, createQualificationButton);

        vMainLayout.setAlignment(Pos.TOP_LEFT);
        vMainLayout.getChildren().addAll(hMainLayout);

        frontPageScene = new Scene(vMainLayout, 1024, 768);
        
        window.setScene(frontPageScene);
        window.show();
    }

    private void initButtons() {
        createPersonButton = new MenuButton("Opret ansat");
        createPersonButton.setOnAction(e -> {
            System.out.println("hej1");
        });

        createQualificationButton = new MenuButton("Opret kvalifikation");
        createQualificationButton.setOnAction(e -> {
            System.out.println("hej2");
        });
        
    }
}
