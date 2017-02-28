package qcs;

import java.util.List;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Created by nick on 2/24/17.
 * Supports 3 IO / GUI operations
 * Save as - User is presented dialog and chooses where and what name to save file to
 * Save - User saves current version of file which overwrites previous
 * Load - User is presented dialog and chooses where to pull file from
 * All use JavaFX for native OS integration (excluding mobile OS)
 * Conversion from json to java classes and opposite direction done with gson library
 */
public class Quantum_IO {

    public void save(List<Class<?>> quantum_fun_stuff) {

    }

    public void save_as(List<Class<?>> quantum_fun_stuff) {

    }

    public void load(final Stage mainStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) mainStage.display(selectedFile);
    }
}
