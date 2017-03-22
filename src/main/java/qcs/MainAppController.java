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

import com.sun.xml.internal.ws.util.StreamUtils;
import javafx.scene.canvas.Canvas;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import qcs.model.Circuit;
import qcs.model.Qubit;
import qcs.model.Register;

import static com.codepoetics.protonpack.StreamUtils.windowed;
import static com.codepoetics.protonpack.StreamUtils.zipWithIndex;

public class MainAppController implements Initializable{
    //reference to main application
    private MainApp mainApp;

    //reference used by save / load to remember where to save to
    private QIO qio = new QIO();

    @FXML
    private Label label;

    @FXML
    private Label label1;

    @FXML
    private Canvas displayed_circuit;

    @FXML
    private SplitPane circuit_parent;

    @FXML
    private void initializeRegisters(ActionEvent event) {

    }

    @FXML
    private void open() {
        new QIO().load(new Stage());
    }

    @FXML
    private void save_as() {
        //when there is a stage for the visualization to be loaded
        //from (first q function) expects an arraylist
        qio.save_as(new Stage(), new ArrayList<>());
    }

    @FXML
    private void save() {
        qio.save(new ArrayList<>());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: init canvas here?? Probably. Bind its view to data
        // TODO:set up listener if we want to select qubits and delete - changed() method
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    @FXML
    private void handleInitRegisters() {
        //lets dialog replace the circuit model etc...
        mainApp.showAddRegistersDialog();

        CircuitsController circuit = mainApp.getCircuitController();

        if(circuit != null)
            circuit.show(displayed_circuit, (int) circuit_parent.getWidth());
    }
}
