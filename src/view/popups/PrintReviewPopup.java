/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Window;
import view.buttons.PopupMenuButton;
import view.schema.Schedule;

/**
 *
 * @author Yousef
 */
public class PrintReviewPopup extends PopupWindow{

    private VBox contentPane;
    private PopupMenuButton printButton;
    private Schedule schedule = null;
    private PrinterJob job = PrinterJob.createPrinterJob();
    private ExceptionPopup exceptionPopup;

    public PrintReviewPopup() {
        super.maximizeScreen();
    }

    @Override
    public void display(String title) {
        initLayouts();
        initSchedule();
        initButtons();
        
        super.getStage().initModality(Modality.NONE);

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
        super.getBottomHBox().getChildren().add(0, printButton);
    }

    public void printSchema() {

        GridPane scheduleGrid = schedule.getGrid();
        scheduleGrid.setStyle("-fx-border-color: black;"
                + "-fx-border-width: 0.5px;");
        PrinterJob job = PrinterJob.createPrinterJob();

        Window window;

        if (scheduleGrid.getScene() != null) {
            window = scheduleGrid.getScene().getWindow();
        } else {
            window = null;
            System.out.println("ostemad");
        }

        if (job.showPrintDialog(window)) {
            //Få pagelayout og udform skemaet efter det
            PageLayout pageLayout = job.getJobSettings().getPageLayout();
            double pagePrintableHeight = pageLayout.getPrintableHeight();
            double pagePrintableWidth = pageLayout.getPrintableWidth();

            //Faktor til nodeskalering
            double scaleX = pagePrintableWidth / scheduleGrid.getBoundsInParent().getWidth();

            scheduleGrid.getTransforms().add(new Scale(scaleX, scaleX));

            Translate grid = new Translate();
            scheduleGrid.getTransforms().add(grid);
            double rows = Math.ceil((scheduleGrid.getLayoutBounds().getHeight() * scaleX) / pagePrintableHeight);
            boolean success = true;
            double computedHeight = 0;
            double heightCounter = 0;
            Rectangle r = new Rectangle(0, 0,
                    scheduleGrid.getLayoutBounds().getWidth(), computedHeight);
            scheduleGrid.setClip(r);
            for (int row = 0; row < rows; row++) {
                //Håndter højde på det der skal printes på en side, sådan at der ikke
                //forekommer afskårne rækker.

                computedHeight = computeHeight(schedule, heightCounter + (pagePrintableHeight / scaleX), heightCounter);

                grid.setY(-(heightCounter));

                r.setHeight(computedHeight);

                if (success) {
                    success &= job.printPage(pageLayout, scheduleGrid);
                }
                r.setY(heightCounter + computedHeight);
                heightCounter += computedHeight;

            }

            job.endJob();
        }   
        
        super.getStage().close();
    }

    private double computeHeight(Schedule schedule, double printRange, double startingPoint) {
        ArrayList<Node[]> gridLayoutNodes = schedule.getGridLayoutlist();
        GridPane pane = schedule.getGrid();

        double computedHeight = 0;
        double tempHeight = 0;
        int index = 0;
        int rows = getRowCount(pane);
        for (int i = 0; i < rows + 1; i++) {

            Node child = pane.getChildren().get(index);

                double rowHeight = child.getLayoutBounds().getHeight();
                if (tempHeight + rowHeight < printRange) {
                    if (tempHeight + rowHeight > startingPoint) {
                        computedHeight += rowHeight;
                    }
                    tempHeight += rowHeight;

                } else {
                    return computedHeight;
                }

            index += gridLayoutNodes.get(i).length;

        }
        return computedHeight;
    }

    private int getRowCount(GridPane pane) {
        int rowIndex = 0;
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                rowIndex = GridPane.getRowIndex(child);
            }
        }
        return rowIndex;
    }

}
