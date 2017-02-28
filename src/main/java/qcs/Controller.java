/* Controller.java

This file is a control part of the MVC model.
It has all the methods to manipulate model

 */

package qcs;

import java.net.URL;
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
        Quantum_IO qio = new Quantum_IO();
        qio.load(new Stage());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
