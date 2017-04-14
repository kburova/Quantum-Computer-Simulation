package qcs.manager;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.io.File;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
public class IOmanager {
    private File last_file_saved = null;

    public void save(List<Class<?>> quantum_fun_stuff) {
        if(last_file_saved != null)
            save(last_file_saved, quantum_fun_stuff);
        else
            save_as(new Stage(), quantum_fun_stuff);
    }

    public void save(File file, List<Class<?>> quantum_fun_stuff) {
        try {
            String content = new Gson()
                    .toJson(quantum_fun_stuff);
            new PrintWriter(file)
                    .print(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void save_as(final Stage mainStage, List<Class<?>> quantum_fun_stuff) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        File file = fileChooser.showSaveDialog(mainStage);
        last_file_saved = file;
        save(file, quantum_fun_stuff);
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
        fileChooser.setTitle("Load Circuit");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Json", "*.json"));

        //reads content of file with filescanner and string builder
        File file = fileChooser.showOpenDialog(mainStage);
        last_file_saved = file;

        String json_string = file_read(file);

        //keep for debugging purposes remove once viz is done
        System.out.print(json_string);

        //anticipate this chunk of code causing errors, I suspect
        //it will produce a list of strings rather than a list of quantum classes as expected
        //this code is going to be the source of numerous bugs must be tested and validated
        return new Gson().fromJson(json_string, new TypeToken<List<?>>(){}.getType());
    }
}
