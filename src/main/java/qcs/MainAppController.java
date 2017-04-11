/**************************************************
 Controller.java

 This file is a control part of the MVC model.
 It has all the methods to manipulate model

 Created by: Ksenia Burova
             Parker Diamond
             Nick Kelley
             Chris Martin

 Date: 02/12/2017
 ****************************************************/
package qcs;

import javafx.scene.Node;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import qcs.model.Circuit;
import qcs.model.operator.BinaryOperator;
import qcs.model.operator.ToffoliGate;
import qcs.model.operator.UnaryOperator;

public class MainAppController implements Initializable{

    //reference to main application
    private MainApp mainApp;
    private CanvasManager canvasManager;

    //reference used by save / load to remember where to save to
    private QIO qio = new QIO();
    @FXML
    private Pane circuitCanvas;
    @FXML
    private Pane xCanvas;
    @FXML
    private Pane yCanvas;
    @FXML
    private SplitPane splitPane;

    @FXML
    private void open() {
        new QIO().load(new Stage());
    }

    @FXML
    private void save_as() {
        //when there is a dialogStage for the visualization to be loaded
        //from (first q function) expects an arraylist
        qio.save_as(new Stage(), new ArrayList<>());
    }

    @FXML
    private void save() {
        qio.save(new ArrayList<>());
    }

    @FXML
    private void stepForward() {
        Circuit c = mainApp.getCircuit();
        /** check if circuit was initialized **/
        if (c.getX() != null) {
            int step = c.getCurrentStep();
            if (step < c.getNumberOfOperators()) {
                //TODO: add execution of the function here
                mainApp.getCircuit().setCurrentStep(++step);
                canvasManager.stepThrough(step);
            }
        }
    }

    @FXML
    private void stepBack() {
        Circuit c = mainApp.getCircuit();
        /** check if circuit was initialized **/
        if (c.getX() != null) {
            int step = c.getCurrentStep();
            if (step > 0) {
                //TODO: add execution of the function here
                mainApp.getCircuit().setCurrentStep(--step);
                canvasManager.stepThrough(step);
            }
        }
    }

    @FXML
    private void restart() {
        Circuit c = mainApp.getCircuit();
        /** check if circuit was initialized **/
        if (c.getX() != null) {
            //TODO: erase all values and restart
            mainApp.getCircuit().setCurrentStep(0);
            canvasManager.stepThrough(0);
        }
    }

    @FXML
    private void runAll() {
        Circuit c = mainApp.getCircuit();
        /** check if circuit was initialized **/
        if (c.getX() != null) {
            //TODO: add execution of the function here
            mainApp.getCircuit().setCurrentStep(c.getNumberOfOperators());
            canvasManager.stepThrough(c.getNumberOfOperators());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    @FXML
    private void handleInitCircuitDialog() {
        boolean registersInitialized = mainApp.showAddRegistersDialog();
        if (registersInitialized){

            /**  erase all previois drawing **/
            if (circuitCanvas.getChildren().size() != 0) {
                circuitCanvas.getChildren().clear();
                xCanvas.getChildren().clear();
                if (yCanvas.getChildren().size() != 0)
                    yCanvas.getChildren().clear();
            }

            canvasManager = new CanvasManager(mainApp.getCircuit(), circuitCanvas, xCanvas, yCanvas);
            canvasManager.drawInitState();
            canvasManager.drawXGrid(mainApp.getCircuit().getX().getState());
            if (mainApp.getCircuit().getY().getNumberOfQubits() != 0) {
                canvasManager.drawYGrid(mainApp.getCircuit().getY().getState());
            }
        }
    }

    @FXML
    private void handleInitQubitsDialog(){

        if (mainApp.getCircuit().getX() == null) {
            showErrorMessage();
        }else {
            boolean OkClicked = mainApp.showAddQubitValuesDialog();
            if (OkClicked) {
                canvasManager.changeQubitVals();
                mainApp.getCircuit().setCurrentStep(0);
                canvasManager.stepThrough(0);
            }
        }
    }

    @FXML
    private void addUnaryGateDialog(ActionEvent event){
        if (mainApp.getCircuit().getX() == null) {
            showErrorMessage();
        }else {
            //pass to function what operator we add
            Node node = (Node) event.getSource();
            String data = (String) node.getUserData();
            boolean OkClicked = mainApp.showUnaryGateDialog(data);
            if (OkClicked) {
                canvasManager.drawUnaryOperator(mainApp.getCircuit().getNumberOfOperators() - 1, (UnaryOperator) mainApp.getCircuit().getLastOperator());
            }
        }
    }

    @FXML
    public void addBinaryGateDialog(ActionEvent event) {
        if (mainApp.getCircuit().getX() == null) {
            showErrorMessage();
        }else {
            //pass to function what operator we add
            Node node = (Node) event.getSource();
            String data = (String) node.getUserData();
            boolean OkClicked = mainApp.showBinaryGateDialog(data);
            if (OkClicked) {
                canvasManager.drawBinaryOperator(mainApp.getCircuit().getNumberOfOperators() - 1, (BinaryOperator) mainApp.getCircuit().getLastOperator());
            }
        }
    }

    @FXML
    public void addTernaryGateDialog(ActionEvent event) {
        if (mainApp.getCircuit().getX() == null) {
            showErrorMessage();
        }else {
            //pass to function what operator we add
            Node node = (Node) event.getSource();
            String data = (String) node.getUserData();
            boolean OkClicked = mainApp.showTernaryGateDialog(data);
            if (OkClicked) {
                //System.out.println(mainApp.getCircuit().getNumberOfOperators());
                canvasManager.drawTernaryOperator(mainApp.getCircuit().getNumberOfOperators() - 1, (ToffoliGate) mainApp.getCircuit().getLastOperator());
            }
        }
    }

    @FXML
    public void addVarGateDialog(ActionEvent event) {
        if (mainApp.getCircuit().getX() == null) {
            showErrorMessage();
        }else {
            //pass to function what operator we add
            Node node = (Node) event.getSource();
            String data = (String) node.getUserData();
            boolean OkClicked = mainApp.showVarGateDialog(data);
            if (OkClicked) {
                canvasManager.drawBigOperator(mainApp.getCircuit().getNumberOfOperators() - 1, mainApp.getCircuit().getLastOperator());
            }
        }
    }
    @FXML
    public void addErrorDialog(ActionEvent event) {
        if (mainApp.getCircuit().getX() == null) {
            showErrorMessage();
        }else {
            //pass to function what operator we add
            Node node = (Node) event.getSource();
            String data = (String) node.getUserData();
            boolean OkClicked = mainApp.showErrorDialog(data);
            if (OkClicked) {
                //canvasManager.drawTernaryOperator(mainApp.getCircuit().getNumberOfOperators() - 1, mainApp.getCircuit().getLastOperator());
            }
        }
    }
    @FXML
    public void addMeasurementDialog(ActionEvent event) {
        if (mainApp.getCircuit().getX() == null) {
            showErrorMessage();
        }else {
            //pass to function what operator we add
            Node node = (Node) event.getSource();
            String data = (String) node.getUserData();
            boolean OkClicked = mainApp.showMeasurementDialog(data);
            if (OkClicked) {
                canvasManager.drawBigOperator(mainApp.getCircuit().getNumberOfOperators() - 1, mainApp.getCircuit().getLastOperator());
            }
        }
    }
    @FXML
    public void addGroverOperatorDialog(ActionEvent event) {
        if (mainApp.getCircuit().getX() == null) {
            showErrorMessage();
        }else {
            //pass to function what operator we add
            Node node = (Node) event.getSource();
            String data = (String) node.getUserData();
            boolean OkClicked = mainApp.showGroverOperatorDialog(data);
            if (OkClicked) {
                canvasManager.drawBigOperator(mainApp.getCircuit().getNumberOfOperators() - 1, mainApp.getCircuit().getLastOperator());
            }
        }
    }

    private void showErrorMessage(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setContentText("Circuit was not initialized with registers!!!");
        alert.showAndWait();
    }
}
