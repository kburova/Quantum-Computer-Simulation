/**************************************************
 InitQubitsDialogController.java

 This file controls the output from dialog window
 that initializes qubits

 Created by: Ksenia Burova
 Parker Diamond
 Nick Kelley
 Chris Martin

 Date: 03/22/2017
 ****************************************************/

package qcs;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import qcs.model.Circuit;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;


public class InitQubitsDialogController implements Initializable{

    @FXML
    private GridPane gridPane;

    Circuit circuit;
    Stage dialogStage;

    InitQubitsDialogController (Circuit c){
        circuit = c;
    }
//    public void setCircuit (Circuit c){
//        circuit = c;
//    }
    public void setDialogStage (Stage s){
        dialogStage = s;
        s.setResizable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        int xCols = 0, yCols = 0, maxCols;

        xCols = circuit.getX().getNUmberOfQubits();

        for (int i = 1; i <= xCols; i++){
            TextField text = new TextField();
            text.setId("X"+ (i-1));
            text.prefWidth(45);
            gridPane.add(text, i ,2);
        }

        //add second label for Y, and its qubits if Y exists
        if (circuit.getY() != null){
            yCols = circuit.getY().getNUmberOfQubits();
            if (yCols != 0) {
                Label labelX = new Label("Register Y:");
                labelX.prefHeight(16.0);
                gridPane.add(labelX, 0, 3);

                for (int i = 1; i <= yCols; i++) {
                    TextField text = new TextField();
                    text.setId("Y"+ (i-1));
                    text.prefWidth(45);
                    gridPane.add(text, i, 3);
                }
            }
        }
        //need max number of qubits to decide on width
        maxCols = (xCols > yCols) ? xCols : yCols;

        // set indexes for qubits
        for (int i = 1; i <= maxCols; i++){
            Label l = new Label(Integer.toString(i-1));
            l.setTextAlignment(TextAlignment.CENTER);

            gridPane.add(l, i ,1);
        }

        //set width of the window depending on max number of qubits
        gridPane.setPrefWidth(maxCols*45 + 85);
        gridPane.setMinWidth(maxCols*45 + 85);
        gridPane.setMaxWidth(maxCols*45 + 85);
        ObservableList columnConstraints = gridPane.getColumnConstraints();

        columnConstraints.add(new ColumnConstraints(80,80,80,Priority.SOMETIMES, HPos.CENTER,false)) ;
        for (int i = 1; i < gridPane.getChildren().size(); i++){
            columnConstraints.add(new ColumnConstraints(45,45,45,Priority.SOMETIMES, HPos.CENTER,false));
        }

        ObservableList rowConstraints = gridPane.getRowConstraints();
        rowConstraints.add(new RowConstraints(15,15,15));
    }

    public boolean handleSave(){
        return false;
    }

    public void handleCancel(){

    }
}
