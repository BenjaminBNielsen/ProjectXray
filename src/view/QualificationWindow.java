/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.control.TextField;

/**
 *
 * @author Yousef
 */
public class QualificationWindow extends PopupWindow{
    private MenuButton createQualification;
    private TextField textFieldQualifications;
    
    @Override
    public void display(String title) {
        
        textFieldQualifications = new TextField();

        createQualification = new MenuButton("Dan en ny kvalifikation");
        createQualification.setOnAction(e -> {
            createQualification.setText("Wow!");
        });

        super.addToBottomHBox(createQualification);
        super.display(title);
    }
}
