package server.gui.fxcontrol;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrLog {
    public TextArea textArea;

    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
            addEntry("log file Created");
        } catch (IOException ex) {
            Logger.getLogger(ErrLog.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void saveLog(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            SaveFile(textArea.getText(), file);
            addEntry("Log File:"+file.getAbsolutePath()+" Created.");
        }
    }

    public void addEntry(String entry){
        textArea.setText(textArea.getText()+"\n***\n"+entry);
    }
}
