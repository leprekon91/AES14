package server.gui.fxcontrol;

import com.Contract;
import com.style.icons.FontAwesome;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Dashboard {

    public AnchorPane userPanel;
    public UserPanel userPanelControl;


    @FXML
    Tab userTab;

    public void initialize() {


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Contract.serverFXML + "userPanel.fxml"));
            userPanel = loader.load();
            userPanelControl = loader.getController();
            userTab.setContent(userPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        addLogMsg("Session Started: " + Timestamp.valueOf(LocalDateTime.now()));
    }

    public void addLogMsg(String str) {
        userPanelControl.addEntry(str);
    }
}
