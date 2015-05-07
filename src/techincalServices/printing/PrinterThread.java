/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package techincalServices.printing;

import java.util.ArrayList;
import javafx.geometry.Bounds;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

/**
 *
 * @author Benjamin
 */
public class PrinterThread extends Thread {

    /**
     * Printer det givne Node-objekt. Node objektets bredde strækkes så det
     * passer til papirets bredde. Og højden bestemmer antallet af sider. Det
     * vil sige, Node-objektet vil ikke blive strukket på højden.
     *
     * @param node Den node der skal printes ud.
     */
    public void print(PrinterJob job, Node node) {

        PageLayout pageLayout = job.getJobSettings().getPageLayout();
        double pagePrintableHeight = pageLayout.getPrintableHeight();
        double pagePrintableWidth = pageLayout.getPrintableWidth();

        Rectangle clipRectangle = new Rectangle();
        node.setClip(clipRectangle);
        Bounds nodeSquare = node.layoutBoundsProperty().getValue();
        clipRectangle.setWidth(nodeSquare.getWidth());
        clipRectangle.setHeight(nodeSquare.getHeight());

        /*node.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
         clipRectangle.setWidth(newValue.getWidth());
         clipRectangle.setHeight(newValue.getHeight());
         });*/
        /*  //Gemmer nodens transformations og dimensions-info da en rigtig udprintning
         //andre info
         ArrayList<Transform> savedNodeTransforms = new ArrayList<>();
         savedNodeTransforms.addAll(node.getTransforms());
         Node savedNodeClip = node.getClip();*/
        //Vi ændre nodens clip dimensioner så den kommer til at passe med den
        //transformation noden kommer til at få for at den passer med printskærmen
        double clipX = clipRectangle.getX();
        double clipY = clipRectangle.getY();
        node.setClip(clipRectangle);
        node.getTransforms().add(new Translate(-clipX, -clipY));

        //Her får vi hvor mange sider der kræves for at printe den skalerede rektangel ud
        //Siden at den skalerede højde og brede divideres med den mulige printable
        // højde og bredde. Hvis height > pagePrintableHeight så bliver det 2 sider
        int rows = (int) Math.ceil(clipRectangle.getHeight() / pagePrintableHeight);

        Translate gridTransform = new Translate();
        node.getTransforms().add(gridTransform);
        for (int row = 0; row < rows; row++) {
            gridTransform.setY(-row * pagePrintableHeight);

            job.printPage(pageLayout, node);
        }
    }
}
