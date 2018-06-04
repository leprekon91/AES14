package server.gui.fxcontrol;


import com.graphics.StatusLine;
import com.style.icons.FontAwesome;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import server.sql.AuthorizeUser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class UserPanel {


    public AnchorPane statusPane;
    public StatusLine statusLine;
    public TextArea textArea;


    public int ClientNum = 0; //number of connected clients
    public Label icon;
    public SplitPane splitPane;

    public void initialize() {


        icon = FontAwesome.setAsIcon(FontAwesome.ICON_WRENCH, icon);
        //status line:
        statusLine = new StatusLine(0);
        statusPane.getChildren().add(statusLine);
        setAnchorsFitScreen(statusLine);

        Timeline updateStatusLine = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    statusLine.addValue(AuthorizeUser.getInstance().getLoggedinUsertCount());

                })
        );
        updateStatusLine.setCycleCount(Timeline.INDEFINITE);
        updateStatusLine.play();


    }


    private void SaveFile(String content, File file) {
        try {
            FileWriter fileWriter;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
            addEntry("log file Created");
        } catch (IOException ex) {
            ex.printStackTrace();
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

        if (file != null) {
            SaveFile(textArea.getText(), file);
            addEntry("Log File:" + file.getAbsolutePath() + " Created.");
        }
    }

    public void addEntry(String entry) {
        textArea.setText(textArea.getText() + "\n***\n" + entry);
    }

    private void setAnchorsFitScreen(Node n) {
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
    }
}
