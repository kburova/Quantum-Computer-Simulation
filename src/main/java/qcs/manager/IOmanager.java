package qcs.manager;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.io.File;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import qcs.model.Circuit;

/**
 * Created by nick on 2/24/17.
 * Supports 3 IO / GUI operations
 * Save as - User is presented dialog and chooses where and what name to save file to
 * Save - User saves current version of file which overwrites previous
 * Load - User is presented dialog and chooses where to pull file from
 * All use JavaFX for native OS integration (excluding mobile OS)
 * Conversion from json to java classes and opposite direction done with gson library
 */
public class IOmanager {
    private File last_file_saved = null;

    public void save(Circuit circuit) {
        if(last_file_saved != null)
            writeToFile(last_file_saved, circuit);
        else
            save_as(new Stage(), circuit);
    }

    public void save_as(final Stage mainStage, Circuit circuit) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        File file = fileChooser.showSaveDialog(mainStage);
        last_file_saved = file;
        writeToFile(file, circuit);
    }

    public void writeToFile(File file, Circuit circuit) {
        try {
            String content = new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(circuit);

            PrintWriter out = new PrintWriter(file);
            out.print(content);
            out.close();

            System.out.println(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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

    public Circuit load(final Stage mainStage) {
        //javaFX to choose file natively
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Circuit");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Json", "*.json"));

        //reads content of file with filescanner and string builder
        File file = fileChooser.showOpenDialog(mainStage);
        last_file_saved = file;

        String json_string = file_read(file);

        //keep for debugging purposes remove once viz is done
        //System.out.print(json_string);

        //anticipate this chunk of code causing errors, I suspect
        //it will produce a list of strings rather than a list of quantum classes as expected
        //this code is going to be the source of numerous bugs must be tested and validated
        return new Gson().fromJson(json_string, Circuit.class);
    }
}
