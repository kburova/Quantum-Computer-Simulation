/* Controller.java

This file is a control part of the MVC model.
It has all the methods to manipulate model

 */

package qcs;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Controller implements Initializable{
    private QIO qio = new QIO();

    @FXML
    private Label label;

    @FXML
    private Label label1;

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    @FXML
    private void open() {
        new QIO()
        .load(new Stage());
    }

    @FXML
    private void save_as() {
        //when there is a stage for the visualization to be loaded
        //from (first q function) expects an arraylist
        qio
        .save_as(new Stage(), new ArrayList<>());
    }

    @FXML
    private void save() {
        qio.save(new ArrayList<>());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
