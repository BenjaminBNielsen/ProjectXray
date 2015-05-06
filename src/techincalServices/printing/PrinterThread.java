/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package techincalServices.printing;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Benjamin
 */
public class PrinterThread extends Thread{

    /**
     * Printer det givne Node-objekt. Node objektets bredde strækkes så det passer
     * til papirets bredde. Og højden bestemmer antallet af sider. Det vil sige,
     * Node-objektet vil ikke blive strukket på højden.
     * @param node Den node der skal printes ud.
     */
    public void print(Node node){
        Rectangle clipRectangle = new Rectangle();
        node.setClip(clipRectangle);
        node.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            clipRectangle.setWidth(newValue.getWidth());
            clipRectangle.setHeight(newValue.getHeight());
        });
    }
    
}
