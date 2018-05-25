package server.gui.fxcontrol;


import com.Contract;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Dashboard {

    public AnchorPane viewPane;
    public AnchorPane userPanel;
    public VBox errLog;
    public ErrLog errControl;

    public void initialize() {
        try {
            userPanel = FXMLLoader.load(getClass().getResource(Contract.serverFXML + "userPanel.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Contract.serverFXML + "errLog.fxml"));
            errLog = loader.load();
            setAnchorsFitScreen(userPanel);
            setAnchorsFitScreen(errLog);
            errControl = (ErrLog) loader.getController();
            viewPane.getChildren().add(userPanel);

        } catch (IOException e) {
            e.printStackTrace();
        }

        showError("Session Started: " + Timestamp.valueOf(LocalDateTime.now()));
    }

    public void showErrorLog(ActionEvent actionEvent) {
        viewPane.getChildren().removeAll(userPanel);
        viewPane.getChildren().add(errLog);

    }

    public void showUsers(ActionEvent actionEvent) {
        viewPane.getChildren().removeAll(errLog);
        viewPane.getChildren().add(userPanel);
    }
    private void setAnchorsFitScreen(Node n){
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);

    }

    public void showError(String str) {
        errControl.addEntry(str);
    }
}
