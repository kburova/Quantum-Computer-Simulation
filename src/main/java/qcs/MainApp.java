/***************************************************
MainApp.java

 Main file for Quantum Computer simulation.
 Loads fxml file with GUI code

 Created by: Ksenia Burova
             Parker Diamond
             Nick Kelley
             Chris Martin

 Date: 02/12/2017

****************************************************/
package qcs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import qcs.model.Circuit;


public class MainApp extends Application {

    private Stage PrimaryStage;
    private BorderPane root;
    private CircuitsController circuit_controller;

    @Override
    public void start(Stage primaryStage) throws Exception{

        PrimaryStage = primaryStage;
        PrimaryStage.setTitle("Quantum Computer Simulator");

        try {
            //load main app layout here
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/RootMenu.fxml"));
            root = loader.load();

            Scene scene = new Scene(root);
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch ( Exception e){
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }

        showMainAppLayout();
    }

    // add The main app layout into frame with drop down menu
    public void showMainAppLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MainView.fxml"));
            BorderPane mainApp = loader.load();
            root.setCenter(mainApp);

            //controller access to main app
            MainAppController controller = loader.getController();
            controller.setMainApp(this);
        }catch (Exception e){
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

    public void showAddRegistersDialog(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/InitRegistersWindow.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));

            NewCircuitDialog dialogHandler = loader.getController();
            dialogHandler.setDialogStage(dialogStage);
            dialogStage.showAndWait();

            //right now new action is handled by dialog not controller, TODO fix that
            CircuitsController controller = dialogHandler.getController();

            if(dialogHandler.isAdd())
              circuit_controller = controller;
        }catch(Exception e){
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

    public CircuitsController getCircuitController(){ return circuit_controller; }

    public static void main(String[] args) {
        launch(args);
    }
}
