package client.gui.fxcontrol;

import client.control.Client;
import com.Contract;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainScreen {
    public Object currentController;
    public AnchorPane rootNode;
    Parent login;
    public Client client;


    public void initialize() {
        //first, display the login screen
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "LoginScreen.fxml"));
            login = fxmlLoader.load();
            ((LoginScreen)fxmlLoader.getController()).client=this.client;
            rootNode.getChildren().setAll(login);
            setAnchorsFitScreen(login);
            currentController = fxmlLoader.getController();
            // setAnchorsFitScreen(rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void setAnchorsFitScreen(Node n) {
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
    }
}
