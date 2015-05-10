/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Window;
import view.buttons.PopupMenuButton;
import view.schema.Schedule;

/**
 *
 * @author Yousef
 */
public class PrintReviewPopup extends PopupWindow {

    private VBox contentPane;
    private PopupMenuButton printButton;
    private Schedule schedule = null;
    private PrinterJob job = PrinterJob.createPrinterJob();

    public PrintReviewPopup() {
        super.maximizeScreen();
    }

    @Override
    public void display(String title) {
        initLayouts();
        initSchedule();
        initButtons();

        super.display(title);

    }

    private void initLayouts() {
        contentPane = new VBox(15);
        contentPane.setPadding(new Insets(15, 15, 15, 15));
    }

    private void initSchedule() {
        super.addToCenter(schedule);
    }

    public void setNode(Schedule schedule) {
        this.schedule = schedule;
    }

    private void initButtons() {
        printButton = new PopupMenuButton("Print");
        printButton.setOnAction(e -> {
            printSchema();
        });
        super.addToBottomHBox(printButton);
    }

    private void printSchema() {

        Node node = schedule.getGrid();
        node.setStyle("-fx-border-color: black;"
                + "-fx-border-width: 0.5px;");
        PrinterJob job = PrinterJob.createPrinterJob();

        Window window;

        if (node.getScene() != null) {
            window = node.getScene().getWindow();
        } else {
            window = null;
            System.out.println("ostemad");
        }

        if (job.showPrintDialog(window)) {
            //Få pagelayout og udform skemaet efter det
            PageLayout pageLayout = job.getJobSettings().getPageLayout();
            double pagePrintableHeight = pageLayout.getPrintableHeight();
            double pagePrintableWidth = pageLayout.getPrintableWidth();

            Node clip = node.getClip();
            ArrayList<Transform> transformations = new ArrayList<>(node.getTransforms());

            //Clipping rektangel
            Rectangle clipRect = new Rectangle();

            clipRect.setWidth(pagePrintableWidth);
            clipRect.setHeight(pagePrintableHeight);
            //node.setClip(new Rectangle(clipRect.getX(), clipRect.getY(),
            //clipRect.getWidth(), pagePrintableHeight));
            //Faktor til nodeskalering
            double scaleX = pagePrintableWidth / node.getBoundsInParent().getWidth();
            double scaleY = pagePrintableHeight / node.getBoundsInParent().getHeight();

            node.getTransforms().add(new Scale(scaleX, scaleX));
            node.getTransforms().add(new Translate(-clipRect.getX(), -clipRect.getY()));

            Translate grid = new Translate();
            node.getTransforms().add(grid);

            int rows = (int) (node.getLayoutBounds().getHeight() / pagePrintableHeight);
            boolean success = true;
            for (int row = 0; row < rows; row++) {
                grid.setY(-row * pagePrintableHeight / scaleX);

                if (success) {
                    success &= job.printPage(pageLayout, node);
                }

            }

            job.endJob();

            node.getTransforms().clear();
        }
    }
}
