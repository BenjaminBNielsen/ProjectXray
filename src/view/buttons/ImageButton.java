/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.buttons;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Yousef
 */
public class ImageButton extends Button {
    private String pimageurl, ppressedImageurl;

    //CSS styles der viser nappen når den er trykket og normalt
    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
    private final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";
    
    public ImageButton(String imagurl, String pressedImageurl) {
        pimageurl = imagurl;
        ppressedImageurl = pressedImageurl;
        
        //Image imageIcon = new Image(imagurl);
        setGraphic(new ImageView(new Image(imagurl)));
        setStyle(STYLE_NORMAL);
        
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                setGraphic(new ImageView(new Image(pressedImageurl)));
                setStyle(STYLE_PRESSED);
            }
            
        });
        
        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                setStyle(STYLE_NORMAL);
            }
            
        });
        
    }
    public void setUnPressed(){
        setGraphic(new ImageView(new Image(pimageurl)));
    }
    
    public void setPressed(){
        setGraphic(new ImageView(new Image(ppressedImageurl)));
    }
}
