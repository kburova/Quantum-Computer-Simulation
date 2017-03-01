/* Controller.java

This file is a control part of the MVC model.
It has all the methods to manipulate model

 */

package qcs;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller implements Initializable{

    @FXML
    private Label label;

    @FXML
    private Label label1;

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    @FXML
    private void open() {
        new Quantum_IO()
        .load(new Stage());
    }

    @FXML
    private void save_as() {
        //when there is a stage for the visualization to be loaded
        //from (first q function) expects an arraylist
        new Quantum_IO()
        .save_as(new Stage(), new ArrayList<>());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
