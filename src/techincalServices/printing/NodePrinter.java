/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package techincalServices.printing;

import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Window;

/**
 *
 * @author Yousef
 */
public class NodePrinter {
    
        public boolean print(PrinterJob job, Node node) {
            System.out.println("Ostemad");
            
            Window window;

        if (node.getScene() != null) {
            window = node.getScene().getWindow();
        } else {
            window = null;
        }

        PageLayout pageLayout = job.getJobSettings().getPageLayout();
        double pagePrintableHeight = pageLayout.getPrintableHeight();
        double pagePrintableWidth = pageLayout.getPrintableWidth();
        
        //Skalering af nodens højde (altså den lange side på et A4 ark) så den passer
        //til pagePrintableHeight som er max højde. Først finder vi en faktor:
        double nodeWidth = node.getLayoutBounds().getWidth();
        double NODE_TO_PRINTABLE_SCALE = nodeWidth / pagePrintableHeight;
            
        

        Rectangle clipRectangle = new Rectangle();
        node.setClip(new Rectangle(clipRectangle.getX(), clipRectangle.getY(),
        clipRectangle.getWidth(), pagePrintableHeight));
        node.getTransforms().add(new Scale(NODE_TO_PRINTABLE_SCALE, NODE_TO_PRINTABLE_SCALE));
        
        
        /*Bounds nodeSquare = node.layoutBoundsProperty().getValue();
        clipRectangle.setWidth(nodeSquare.getWidth());
        clipRectangle.setHeight(nodeSquare.getHeight());

        node.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
         clipRectangle.setWidth(newValue.getWidth());
         clipRectangle.setHeight(newValue.getHeight());
         });*/
        /*  //Gemmer nodens transformations og dimensions-info da en rigtig udprintning
         //andre info
         ArrayList<Transform> savedNodeTransforms = new ArrayList<>();
         savedNodeTransforms.addAll(node.getTransforms());
         Node savedNodeClip = node.getClip();
        //Vi ændre nodens clip dimensioner så den kommer til at passe med den
        //transformation noden kommer til at få for at den passer med printskærmen
        double clipX = clipRectangle.getX();
        double clipY = clipRectangle.getY();*/
        node.getTransforms().add(new Translate(0, 0));

        //Her får vi hvor mange sider der kræves for at printe den skalerede rektangel ud
        //Siden at den skalerede højde og brede divideres med den mulige printable
        // højde og bredde. Hvis height > pagePrintableHeight så bliver det 2 sider
        int cols = (int) Math.ceil(clipRectangle.getHeight() / pagePrintableHeight);
        //Laver en gridTransform der tager udklip ud fra noden der er på størrelse
        //med printableHeight og Width
        Translate gridTransform = new Translate();
        node.getTransforms().add(gridTransform);
        boolean print = true;
        for (int col = 0; col < cols; col++) {
            gridTransform.setX(-col * pagePrintableHeight);
            gridTransform.setY(0);

            print &= job.printPage(pageLayout, node);
        }
        return print;
    }
    
}
