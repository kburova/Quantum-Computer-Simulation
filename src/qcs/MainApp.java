/***************************************************
MainApp.java

 Main file for Quantum Computer simulation.
 Loads fxml file with GUI code

 Created by: Ksenia Burova
             Parker Diamond
             Nick Kelly
             Chris Martin

 Date: 02/12/2017

****************************************************/
package qcs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;


public class MainApp extends Application {

    private Stage PrimaryStage;
    private BorderPane root;

    @Override
    public void start(Stage primaryStage) throws Exception{

        PrimaryStage = primaryStage;
        PrimaryStage.setTitle("Quantum Computer Simulator");

        try {
            //load main app layout here
            root = (BorderPane) FXMLLoader.load(getClass().getResource("view/RootMenu.fxml"));
            Scene scene = new Scene(root);
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch ( Exception e){
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }

        showMainAppLayout();
    }

    public Stage getPrimaryStae(){
        return PrimaryStage;
    }

    //TODO: add another layout for menu item with "About" dropdown menu"
    // Should be function that has similar functionality as above, and gets called
    // after catch statement above
    public void showMainAppLayout(){

        try {
            BorderPane mainApp = (BorderPane) FXMLLoader.load(getClass().getResource("view/MainView.fxml"));
            root.setCenter(mainApp);
        }catch (Exception e){
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
