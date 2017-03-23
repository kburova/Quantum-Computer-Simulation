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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import qcs.model.Circuit;


public class MainApp extends Application {

    private Stage PrimaryStage;
    private BorderPane root;
    private Circuit circuit;


    //create new circuit
    public MainApp(){
        circuit = new Circuit();
    }

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

    public boolean showAddRegistersDialog(){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/InitCircuitDialog.fxml"));
            AnchorPane dialog = loader.load();

            //GridPane grid = dialog.getChildren();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));

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

   // sill working

    // http://stackoverflow.com/questions/30814258/javafx-pass-parameters-while-instantiating-controller-class
    public boolean showAddQubitValuesDialog(){

//        int xCols = 0, yCols = 0, maxCols;

        try{
            FXMLLoader loader = new FXMLLoader();
            InitQubitsDialogController controller = new InitQubitsDialogController(circuit);
            loader.setController(controller);

            loader.setLocation(getClass().getResource("/view/InitQubitsDialog.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();

//            GridPane gridPane = (GridPane) dialog.getChildren().get(0);
//            xCols = circuit.getX().getNUmberOfQubits();
//
//            for (int i = 1; i <= xCols; i++){
//                TextField text = new TextField();
//                text.setId("X"+ (i-1));
//                text.prefWidth(45);
//                gridPane.add(text, i ,2);
//            }
//
//            //add second label for Y, and its qubits if Y exists
//            if (circuit.getY() != null){
//                yCols = circuit.getY().getNUmberOfQubits();
//                if (yCols != 0) {
//                    Label labelX = new Label("Register Y:");
//                    labelX.prefHeight(16.0);
//                    gridPane.add(labelX, 0, 3);
//
//                    for (int i = 1; i <= yCols; i++) {
//                        TextField text = new TextField();
//                        text.setId("Y"+ (i-1));
//                        text.prefWidth(45);
//                        gridPane.add(text, i, 3);
//                    }
//                }
//            }
//            //need max number of qubits to decide on width
//            maxCols = (xCols > yCols) ? xCols : yCols;
//
//            // set indexes for qubits
//            for (int i = 1; i <= maxCols; i++){
//                Label l = new Label(Integer.toString(i-1));
//                l.setTextAlignment(TextAlignment.CENTER);
//
//                gridPane.add(l, i ,1);
//            }
//
//            //set width of the window depending on max number of qubits
//            gridPane.setPrefWidth(maxCols*45 + 85);
//            gridPane.setMinWidth(maxCols*45 + 85);
//            gridPane.setMaxWidth(maxCols*45 + 85);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(PrimaryStage);
            dialogStage.setScene(new Scene(dialog));

            controller.setDialogStage(dialogStage);
//            controller.setCircuit(circuit);

            dialogStage.showAndWait();

            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    final public Stage getPrimaryStage(){
        return PrimaryStage;
    }
    final public Circuit getCircuit() { return circuit; }

    public static void main(String[] args) {
        launch(args);
    }
}
