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
        Circuit circuit = mainApp.showAddRegistersDialog();
        if(circuit != null)
            drawCircuitToCanvas(circuit, displayed_circuit, (int) circuit_parent.getWidth());
    }

    private void write_qbits_to_canvas(List<Qubit> qbits, Canvas canvas, Integer size
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

            Integer font_width = size * 3;

            //draw line
            canvas.getGraphicsContext2D()
              .strokeLine(x + font_width, y - size / 2, canvas.getWidth(), y - size / 2);
          });
    }

    private void drawCircuitToCanvas(Circuit circuit, Canvas canvas, Integer parent_width) {
      //loads of precomputing to figure out the size of this new canvas
      final Integer font_size = (int) 13.0;
      final Integer x_count = circuit.getX().getNumberOfQubits();
      final Integer y_count = circuit.getY().getNumberOfQubits();
      final Integer padding_top = font_size;
      final Integer line_spacing = 2;
      final Integer line_y = x_count * font_size * line_spacing + font_size + padding_top;

      //when quantum functions are added this will need to be adjusted to choose whichever is
      //greater parent width of internal width
      final Integer width = parent_width;

      //the 3 accounts for the line between x and y
      final Integer height = (x_count + y_count + 3) * font_size * line_spacing + padding_top;

      //effectively erases old contents
      canvas.setWidth(0);
      canvas.setHeight(0);
      canvas.setWidth(width);
      canvas.setHeight(height);


      //write x's
      write_qbits_to_canvas(circuit.getX().getQubits(), canvas, font_size
        , padding_top, line_spacing, font_size);

      //write horizontal line divide and labels
      Integer x_axis = font_size / 2 - 1;
      Integer top_y_axis = line_y - font_size + font_size / 2;
      Integer bottom_y_axis = line_y + font_size;

      canvas.getGraphicsContext2D().fillText("x", x_axis, top_y_axis);
      canvas.getGraphicsContext2D().strokeLine(0, line_y, canvas.getWidth(), line_y);
      canvas.getGraphicsContext2D().fillText("y", x_axis, bottom_y_axis);

      //write y's
      write_qbits_to_canvas(circuit.getY().getQubits(), canvas, font_size
        , padding_top, line_spacing, line_y + font_size * 2);
    }
}
