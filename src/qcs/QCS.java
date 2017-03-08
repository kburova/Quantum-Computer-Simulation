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
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class QCS extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("qcs.fxml"));
        Scene scene = new Scene(root, 1000,550);
        stage.setTitle("Quantum Computer Simulator");

        //CSS file may be used here for design features
        //scene.getStylesheets().add(QCS.class.getResource("QCSdesign.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
