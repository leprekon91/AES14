package server.gui.fxcontrol;


import com.Contract;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Dashboard {

    public AnchorPane viewPane;
    public AnchorPane userPanel;

    public void initialize() {
        try {
            userPanel = FXMLLoader.load(getClass().getResource(Contract.serverFXML + "userPanel.fxml"));
            viewPane.getChildren().add(userPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
