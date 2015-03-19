package view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PersonPopup extends PopupWindow {

    //Layouts
    private VBox contentEmployee;

    //Labels
    private Label lPersonNummer, lNavn, lEfternavn, lTelefon, lAddresse, lEmail;

    //Textboxes
    private TextField tPersonNummer, tNavn, tEfternavn, tTelefon, tAddresse, tEmail;

    //Knapper
    private MenuButton addEmployee;

    @Override
    public void display(String title) {
        initLayouts();
        initLabels();
        initTextFields();
        setupEmployeeScreen();
        initButtons();

        super.addToCenter(contentEmployee);
        super.getStage().sizeToScene();
        super.display(title);
    }

    public void setupEmployeeScreen() {
        contentEmployee.setAlignment(Pos.CENTER);
        contentEmployee.getChildren().addAll(lPersonNummer, tPersonNummer, lNavn,
                tNavn, lEfternavn, tEfternavn, lTelefon, tTelefon, lAddresse,
                tAddresse, lEmail, tEmail);
    }

    public void initButtons() {
        ArrayList<MenuButton> buttons = new ArrayList<>();

        addEmployee = new MenuButton("Tilføj ansat");
        addEmployee.setOnAction(e -> {
            System.out.println("IT WORKED");
        });
        buttons.add(addEmployee);

        for (MenuButton button : buttons) {
            super.addToBottomHBox(button);
        }

    }

    public void initLayouts() {
        //Labels og textbokse i midten.
        contentEmployee = new VBox(15);
        contentEmployee.setPadding(new Insets(15, 0, 15, 0));
    }

    public void initLabels() {
        lPersonNummer = new Label("Personnummer");
        lNavn = new Label("Fornavn");
        lEfternavn = new Label("Efternavn");
        lTelefon = new Label("Telefonnummer");
        lAddresse = new Label("Addresse");
        lEmail = new Label("Email addresse");
    }

    public void initTextFields() {
        tPersonNummer = new TextField("Personnummer");
        tNavn = new TextField("Fornavn");
        tEfternavn = new TextField("Efternavn");
        tTelefon = new TextField("Telefonnummer");
        tAddresse = new TextField("Addresse");
        tEmail = new TextField("Email addresse");
    }
}
