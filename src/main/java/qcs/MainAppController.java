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

import javafx.scene.canvas.Canvas;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.stage.Stage;
import qcs.model.Qubit;

import static com.codepoetics.protonpack.StreamUtils.windowed;
import static com.codepoetics.protonpack.StreamUtils.zipWithIndex;

public class MainAppController implements Initializable{

    //reference to main application
    private MainApp mainApp;

    //reference used by save / load to remember where to save to
    private QIO qio = new QIO();
    @FXML
    private Canvas circuitCanvas;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: init canvas here?? Probably. Bind its view to data
        // TODO:set up listener if we want to select qubits and delete - changed() method
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    @FXML
    private void handleInitCircuitDialog() {
        boolean registersInitialized = mainApp.showAddRegistersDialog();
        if (registersInitialized){
            initCircuitCanvas(circuitCanvas, (int) splitPane.getWidth());
        }
    }

    /** Method initializes Canvas with lines for qubit operators **/
    public void initCircuitCanvas(Canvas canvas, Integer parent_width) {
        //loads of precomputing to figure out the size of this new canvas
        final Integer font_size = (int) 13.0;
        final Integer x_count = mainApp.getCircuit().getX().getNumberOfQubits();
        final Integer y_count = mainApp.getCircuit().getY().getNumberOfQubits();
        final Integer padding_top = font_size;
        final Integer line_spacing = 2;
        final Integer line_y = x_count * font_size * line_spacing + font_size + padding_top;

        //when functions are in change function_size to appropriate
        final Integer function_size = 45;
        final Integer circuit_width = mainApp.getCircuit().getNumberOfOperators() * function_size;
        final Integer width = (parent_width > circuit_width) ? (parent_width) : (circuit_width);

        //the 3 accounts for the line between x and y
        final Integer height = (x_count + y_count + 3) * font_size * line_spacing + padding_top;

        //erases old contents
        //there are cases where this is less efficiency, but I'll let the compiler figure that out
        canvas.setWidth(0);
        canvas.setHeight(0);
        canvas.setWidth(width);
        canvas.setHeight(height);


        //write x's
        writeQbitsToCanvas(mainApp.getCircuit().getX().getQubits(), canvas, font_size
                , padding_top, line_spacing, font_size);

        //write horizontal line divide and labels
        final Integer x_axis = font_size / 2 - 1;
        final Integer top_y_axis = line_y - font_size + font_size / 2;
        final Integer bottom_y_axis = line_y + font_size;

        canvas.getGraphicsContext2D().fillText("x", x_axis, top_y_axis);
        canvas.getGraphicsContext2D().strokeLine(0, line_y, canvas.getWidth(), line_y);
        canvas.getGraphicsContext2D().fillText("y", x_axis, bottom_y_axis);

        //write y's
        writeQbitsToCanvas(mainApp.getCircuit().getY().getQubits(), canvas, font_size
                , padding_top, line_spacing, line_y + font_size * 2);
    }

    private void writeQbitsToCanvas(List<Qubit> qbits, Canvas canvas, Integer size
            , Integer margin, Integer line_spacing, Integer padding_top) {
        final Stream<String> qbit_stream = qbits.stream()
                .map(qbit -> "|" + Integer.toString((int) Math.round(qbit.getState())) + ">");

        zipWithIndex(qbit_stream)
                .forEach(qbit -> {
                    final Integer y = Math.round(margin + padding_top + qbit.getIndex() * size * line_spacing);
                    final Integer x = 0 + margin;

                    //draw qbit
                    canvas.getGraphicsContext2D()
                            .fillText(qbit.getValue(), x, y);

                    final Integer font_width = size * 3;

                    //draw line
                    canvas.getGraphicsContext2D()
                            .strokeLine(x + font_width, y - size / 2, canvas.getWidth(), y - size / 2);
                });
    }

    @FXML
    private void handleInitQubitsDialog(){

        if (mainApp.getCircuit().getX() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Circuit was not initialized with registers!!!");
            alert.showAndWait();
        }else {
            boolean OkClicked = mainApp.showAddQubitValuesDialog();
        }
    }
}
