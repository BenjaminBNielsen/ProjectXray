package view;

import java.util.ArrayList;

public class PersonPopup extends PopupWindow {

    private MenuButton addEmployee, addStudent;

    @Override
    public void display(String title) {
        
        initButtons();
        
        super.display(title);
    }

    public void initButtons() {
        ArrayList<MenuButton> buttons = new ArrayList<>();

        addEmployee = new MenuButton("Tilføj ansat");
        addEmployee.setOnAction(e -> {

        });
        buttons.add(addEmployee);

        addStudent = new MenuButton("Tilføj student");
        addStudent.setOnAction(e -> {

        });
        buttons.add(addStudent);

        for (MenuButton button : buttons) {
            super.addToBottomHBox(button);
        }

    }
}
