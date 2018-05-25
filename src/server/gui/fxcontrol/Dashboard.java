package server.gui.fxcontrol;


import com.Contract;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Dashboard {

    public AnchorPane viewPane;
    public AnchorPane userPanel;
    public AnchorPane errLog;
    public ErrLog errControl;

    public void initialize() {
        try {
            userPanel = FXMLLoader.load(getClass().getResource(Contract.serverFXML + "userPanel.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Contract.serverFXML + "errLog.fxml"));
            errLog = loader.load();
            errControl=(ErrLog) loader.getController();
            viewPane.getChildren().add(userPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        addError("Session Started: "+Timestamp.valueOf(LocalDateTime.now()));
    }

    public void showErrors(ActionEvent actionEvent) {
        viewPane.getChildren().removeAll(userPanel);
        viewPane.getChildren().add(errLog);

    }

    public void showUsers(ActionEvent actionEvent) {
        viewPane.getChildren().removeAll(errLog);
        viewPane.getChildren().add(userPanel);
    }

    public void addError(String str){
        errControl.addEntry(str);
    }
}
