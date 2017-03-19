/* Controller.java

This file is a control part of the MVC model.
It has all the methods to manipulate model

 */
package qcs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MainAppController implements Initializable{

    @FXML
    private Label label;

    @FXML
    private Label label1;

    @FXML
    private void initializeRegisters(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
