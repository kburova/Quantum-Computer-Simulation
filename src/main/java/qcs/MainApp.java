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
import qcs.controller.*;
import qcs.model.Circuit;


public class MainApp extends Application {

    private Stage PrimaryStage;
    private BorderPane root;
    private Circuit circuit;


    //create new circuit
    public MainApp() { circuit = new Circuit(); }

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

    /*** The main app layout that is loaded from fxml. It is loaded into main
         frame with drop down menu
     ***/
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

    public void showHelpDialog(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Help.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));
            dialogStage.setTitle("Help");
            dialogStage.showAndWait();

        }catch(Exception e){
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

    public void showAboutDialog(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/About.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));
            dialogStage.setTitle("About");
            dialogStage.showAndWait();

        }catch(Exception e){
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }
    /*** This method loads Stage and View for Dialog window that initializes circuit
     and number of registers in a circuit
     ***/
    public boolean showAddRegistersDialog(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/InitCircuitDialog.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));
            dialogStage.setTitle("Circuit Data");

            InitCircuitDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCircuit(circuit);

            dialogStage.showAndWait();
            return controller.isAdd();
        }catch(Exception e){
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
            return false;
        }
    }

    /*** This method loads Stage and View for Dialog window that initializes Qubit values
         We call constructor first, because we need to pass circuit data to it before
         controller initializes fxml(we need data to initialize number of TextFields)***/
    public boolean showAddQubitValuesDialog(){
        try{
            FXMLLoader loader = new FXMLLoader();
            InitQubitsDialogController controller = new InitQubitsDialogController(circuit);
            loader.setController(controller);

            loader.setLocation(getClass().getResource("/view/InitQubitsDialog.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();

            dialogStage.setTitle("Q-bit Values");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));

            //pass dialog stage to controller
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return true;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int showUnaryGateDialog(String id){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UnaryGateDialog.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));
            dialogStage.setTitle("Add Gate");

            UnaryGateController controller = loader.getController();
            controller.setDialogStage(dialogStage, id);
            controller.setCircuit(circuit);

            dialogStage.showAndWait();

            return controller.isAdd();

        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public int showBinaryGateDialog(String id){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/BinaryGateDialog.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));
            dialogStage.setTitle("Add Gate");

            BinaryGateController controller = loader.getController();
            controller.setDialogStage(dialogStage, id);
            controller.setCircuit(circuit);

            dialogStage.showAndWait();

            return controller.isAdd();

        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public int showRotateGateDialog(String id) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/RotateGateDialog.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));
            dialogStage.setTitle("Add Gate");

            RotateGateController controller = loader.getController();
            controller.setDialogStage(dialogStage, id);
            controller.setCircuit(circuit);

            dialogStage.showAndWait();

            return controller.isAdd();

        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public int showTernaryGateDialog(String id){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/TernaryGateDialog.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));
            dialogStage.setTitle("Add Gate");

            TernaryGateController controller = loader.getController();
            controller.setDialogStage(dialogStage, id);
            controller.setCircuit(circuit);

            dialogStage.showAndWait();

            return controller.isAdd();

        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public int showVarGateDialog(String id) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/VarQbitDialog.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));
            dialogStage.setTitle("Add Gate");

            VarQbitController controller = loader.getController();
            controller.setDialogStage(dialogStage, id);
            controller.setCircuit(circuit);

            dialogStage.showAndWait();

            return controller.isAdd();

        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public int showErrorDialog(String id) {
        return showVarGateDialog(id);
    }

    public int showMeasurementDialog(String id) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MeasurementGateDialog.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));
            dialogStage.setTitle("Measure");

            MeasurementGateController controller = loader.getController();
            controller.setDialogStage(dialogStage, id);
            controller.setCircuit(circuit);

            dialogStage.showAndWait();

            return controller.isAdd();

        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public int showGroverOperatorDialog(String id) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/GroverDialog.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));
            dialogStage.setTitle("Grover Gate");

            GroverGateController controller = loader.getController();
            controller.setDialogStage(dialogStage, id);
            controller.setCircuit(circuit);

            dialogStage.showAndWait();

            return controller.isAdd();

        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public boolean showRemoveGateDialog(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/RemoveGateDialog.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));
            dialogStage.setTitle("Remove Operator");

            RemoveGateController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCircuit(circuit);

            dialogStage.showAndWait();

            return controller.isDelete();

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int showStepToDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/GoToDialog.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));
            dialogStage.setTitle("Grover Gate");

            GoToController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCircuit(circuit);

            dialogStage.showAndWait();

            return controller.isOk();

        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    final public Circuit getCircuit() { return circuit; }
    final public void setCircuit (Circuit circuit) {
        this.circuit = circuit;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
