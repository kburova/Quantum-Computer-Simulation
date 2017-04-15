/**************************************************
 controller.java

 This file is a control part of the MVC model.
 It has all the methods to manipulate model

 Created by: Ksenia Burova
             Parker Diamond
             Nick Kelley
             Chris Martin

 Date: 02/12/2017
 ****************************************************/
package qcs.controller;

import javafx.scene.Node;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import qcs.manager.CanvasManager;
import qcs.MainApp;
import qcs.manager.IOmanager;
import qcs.model.Circuit;
import qcs.model.operator.BinaryOperator;
import qcs.model.operator.GroverOperator;
import qcs.model.operator.ToffoliGate;
import qcs.model.operator.UnaryOperator;

public class MainAppController implements Initializable{

    //reference to main application
    private MainApp mainApp;
    private CanvasManager canvasManager;
    private Circuit circuit;

    //reference used by save / load to remember where to save to
    private IOmanager IOmanager = new IOmanager();
    @FXML
    private Pane circuitCanvas;
    @FXML
    private Pane xCanvas;
    @FXML
    private Pane yCanvas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
        circuit = mainApp.getCircuit();
        canvasManager = new CanvasManager(circuit, circuitCanvas, xCanvas, yCanvas);
    }

    private void showErrorMessage(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setContentText("Circuit was not initialized with registers!!!");
        alert.showAndWait();
    }

    @FXML
    private void open() {
        new IOmanager().load(new Stage());
    }

    @FXML
    private void save_as() {
        //when there is a dialogStage for the visualization to be loaded
        //from (first q function) expects an ArrayList
        IOmanager.save_as(new Stage(), new ArrayList<>());
    }

    @FXML
    private void save() {
        IOmanager.save(new ArrayList<>());
    }

    /** Do 1 step forward in a circuit:
     * check if circuit was initialized and if there are any operators left to execute,
     * do the operation and increment step, then redraw 'step' line and recolor states
     * **/
    @FXML
    private void stepForward() {
        if (circuit.getX() != null) {
            int step = circuit.getCurrentStep();
            if (step < circuit.getNumberOfOperators()) {
                circuit.getOperator(step).doOperation();
                circuit.incrementStep();
                canvasManager.stepThrough(++step);
            }
        }
    }

    /** Do 1 step backwards in a circuit:
     * check if circuit was initialized and if we are not at the very beginning,
     * decrement step, then do previous operation to negate last result,
     * then redraw 'step' line and recolor states
     * **/
    @FXML
    private void stepBack() {
        if (circuit.getX() != null) {
            int step = circuit.getCurrentStep();
            if (step > 0) {
                circuit.decrementStep();
                circuit.getOperator(--step).undoOperation();
                canvasManager.stepThrough(step);
            }
        }
    }

    /** Restart execution of a circuit:
     * reinitialize amplitudes to initial state and
     * redraw 'step'
     **/
    @FXML
    private void restart() {
        if (circuit.getX() != null) {
            int step = circuit.getCurrentStep();
            if (step != 0) {
                circuit.reInitializeRegisterQubits();
                canvasManager.stepThrough(0);
            }
        }
    }

    /** Run through all the gates that are left at once:
     * do operations starting with current step all the way to the end,
     * move 'step' line to the end and recolor amplitudes**/
    @FXML
    private void runAll() {
        if (circuit.getX() != null) {
            int step = circuit.getCurrentStep();
            int numOfSteps = circuit.getNumberOfOperators();
            if (step != numOfSteps) {
                for ( int i = step ; i < numOfSteps; i++) {
                    circuit.getOperator(step).doOperation();
                }
                circuit.setCurrentStep(numOfSteps);
                canvasManager.stepThrough(numOfSteps);
            }
        }
    }

    /** All functions below are in charge of dialog windows that read in users data like
     * target, control qubits or register ro operate on
     */

    @FXML
    private void handleInitCircuitDialog() {
        boolean registersInitialized = mainApp.showAddRegistersDialog();
        if (registersInitialized){
            canvasManager.resetCanvasManager();
            canvasManager.drawInitState();
        }
    }

    @FXML
    private void handleInitQubitsDialog(){
        if (mainApp.getCircuit().getX() == null) {
            showErrorMessage();
        }else {
            // change amplitudes and current step inside of handleOk() in controller class for initQubits
            // then redraw initial state after OK is hit
            boolean OkClicked = mainApp.showAddQubitValuesDialog();
            if (OkClicked) {
                canvasManager.changeQubitVals();
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
            int OkClicked = mainApp.showUnaryGateDialog(data);
            addAndDraw(OkClicked, "Unary");
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
            int OkClicked = mainApp.showBinaryGateDialog(data);
            addAndDraw(OkClicked, "Binary");
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
            int OkClicked = mainApp.showTernaryGateDialog(data);
            addAndDraw(OkClicked, "Ternary");
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
            int OkClicked = mainApp.showVarGateDialog(data);
            addAndDraw(OkClicked, "VarQbit");
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
            int OkClicked = mainApp.showMeasurementDialog(data);
            addAndDraw(OkClicked, "Measurement");
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
            int OkClicked = mainApp.showGroverOperatorDialog(data);
            addAndDraw(OkClicked, "Grover");
        }
    }

    @FXML
    public void handleRemoveGateDialog(ActionEvent event) {
        if (mainApp.getCircuit().getX() == null) {
            showErrorMessage();
        }else {
               boolean OkDelete = mainApp.showRemoveGateDialog();
            if (OkDelete) {
                circuit.reInitializeRegisterQubits();
                circuitCanvas.getChildren().remove( 2, circuitCanvas.getChildren().size());
                canvasManager.redrawOperatorsOnly(0);
            }
        }

    }

    /** Helper function to avoid code duplication **/
    private void addAndDraw(int OkClicked, String type){
        if ((OkClicked == circuit.getNumberOfOperators() - 1)  && (OkClicked != -1)) {
            if (type.equals("Unary")) {
                canvasManager.drawUnaryOperator(mainApp.getCircuit().getNumberOfOperators() - 1, (UnaryOperator) mainApp.getCircuit().getLastOperator());
            }else if (type.equals("Binary")){
                canvasManager.drawBinaryOperator(mainApp.getCircuit().getNumberOfOperators() - 1, (BinaryOperator) mainApp.getCircuit().getLastOperator());
            }else if (type.equals("Ternary") ) {
                canvasManager.drawTernaryOperator(mainApp.getCircuit().getNumberOfOperators() - 1, (ToffoliGate) mainApp.getCircuit().getLastOperator());
            }else if (type.equals("Grover")){
                canvasManager.drawGroverOperator(mainApp.getCircuit().getNumberOfOperators() - 1,  (GroverOperator) mainApp.getCircuit().getLastOperator());
            }else {
                canvasManager.drawBigOperator(circuit.getNumberOfOperators() - 1, circuit.getLastOperator());
            }
        }else if (OkClicked != -1){
            circuit.reInitializeRegisterQubits();
            circuitCanvas.getChildren().remove( OkClicked + 2, circuitCanvas.getChildren().size());
            canvasManager.redrawOperatorsOnly(OkClicked);
        }
    }
}
