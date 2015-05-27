/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.popups;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Yousef
 */
public class PrintApplication extends Application{

    private Stage primaryStage;
    private PrintReviewPopup printReviewPopup;
    
    public PrintApplication(PrintReviewPopup prp){
        printReviewPopup = prp;
        try {
            this.start(printReviewPopup.getStage());
        } catch (Exception ex) {
            System.out.println("");
        }
        
        printReviewPopup.getStage().setOnCloseRequest(e->{
            try {
                this.stop();
            } catch (Exception ex) {
                ExceptionPopup ep = new ExceptionPopup();
                ep.display(ex.getMessage());
            }
        });
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        printReviewPopup = new PrintReviewPopup();
    }

    public PrintReviewPopup getPrintReviewPopup() {
        return printReviewPopup;
    }

    public void setPrintReviewPopup(PrintReviewPopup printReviewPopup) {
        this.printReviewPopup = printReviewPopup;
    }
    
}
