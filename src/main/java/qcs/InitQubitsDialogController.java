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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import qcs.model.Circuit;

import java.net.URL;
import java.util.ResourceBundle;


public class InitQubitsDialogController implements Initializable{

    @FXML
    private GridPane gridPane;

    @FXML
    private AnchorPane anchorPane;

    Circuit circuit;
    Stage dialogStage;

    InitQubitsDialogController (Circuit c){
        circuit = c;
    }

    public void setDialogStage (Stage s){
        dialogStage = s;
        s.setResizable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        int xCols = 0, yCols = 0, maxCols;
        xCols = circuit.getX().getNUmberOfQubits();

        /** add TextFields for Register X**/
        for (int i = 1; i <= xCols; i++){
            TextField text = new TextField();
            text.setId("X"+ (i-1));
            text.prefWidth(50);
            text.setText(Integer.toString( circuit.getX().getQubits().get(i-1).getValue() ));
            gridPane.add(text, i ,2);
        }

        /** add second label for Y, and its Text Fields for qubits if Y exists **/
        if (circuit.getY() != null){
            yCols = circuit.getY().getNUmberOfQubits();
            if (yCols != 0) {
                Label labelY = new Label("Register Y:");
                labelY.prefHeight(16.0);
                labelY.prefWidth(85);
                gridPane.add(labelY, 0, 3);

                for (int i = 1; i <= yCols; i++) {
                    TextField text = new TextField();
                    text.setId("Y"+ (i-1));
                    text.prefWidth(50);
                    text.setText(Integer.toString( circuit.getY().getQubits().get(i-1).getValue() ));
                    gridPane.add(text, i, 3);
                }
            }
        }
        /** need max number of qubits to decide on width **/
        maxCols = (xCols > yCols) ? xCols : yCols;

        /** set indexes for qubits that are displayed at the top of input fields **/
        for (int i = 1; i <= maxCols; i++){
            Label l = new Label(Integer.toString(i-1));
            l.setPrefWidth(50);
            l.setTextAlignment(TextAlignment.CENTER);
            gridPane.add(l, i ,1);
        }

        ObservableList columnConstraints = gridPane.getColumnConstraints();
        ObservableList rowConstraints = gridPane.getRowConstraints();

        /** Add constraints that resize and stabilize dialog window **/
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS);
;
        for (int i = 0; i < gridPane.getChildren().size(); i++){
            columnConstraints.add(cc);
        }
        for (int i = 0; i < 5; i++) {
            rowConstraints.add(rc);
        }

        anchorPane.setPrefWidth(maxCols*45);
    }

    /** Close dialog when click OK in dialog window **/
    public boolean handleOk(){
        dialogStage.close();
        return true;
    }

}
