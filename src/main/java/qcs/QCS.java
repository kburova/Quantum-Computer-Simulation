/*
QCS.java

Main file for QC simulation
Created by: Ksenia Burova
02/12/2017

*/

package qcs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;



public class QCS extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/qcs.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1000,550);
        stage.setTitle("Quantum Computer Simulator");

        //CSS file may be used here for design features
        scene.getStylesheets().add(QCS.class.getResource("/QCSdesign.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
