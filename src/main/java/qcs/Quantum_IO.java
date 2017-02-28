package qcs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.io.File;
import java.util.Scanner;

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

    public String file_read(File file) {

        StringBuilder json_builder = new StringBuilder();

        {
            Scanner scanner = null;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while (scanner.hasNext()) {
                json_builder
                        .append(scanner.next())
                        .append(System.getProperty("line.separator"));
            }
            scanner.close();
        }

        return json_builder.toString();
    }

    public String load(final Stage mainStage) {
        //javaFX to choose file natively
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Quantum File from json");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Json", "*.json"));

        //reads content of file with filescanner and string builder
        String json_string = file_read(fileChooser.showOpenDialog(mainStage));

        System.out.print(json_string);
        return json_string;
    }
}
