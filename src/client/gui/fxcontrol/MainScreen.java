package client.gui.fxcontrol;

import client.control.Client;
import com.Contract;
import com.data.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;

public class MainScreen {
    public Object currentController;
    public AnchorPane rootNode;
    public Wait wait;
    Parent login;
    public Client client;
    public User user;


    public void initialize() {
        //first, display the login screen
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "LoginScreen.fxml"));
            login = fxmlLoader.load();
            ((LoginScreen) fxmlLoader.getController()).mainScreenController = this;
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

    public void switchToWait() {

        Parent p = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "Wait.fxml"));
            p = loader.load();
            Wait wait = loader.getController();
            setAnchorsFitScreen(p);
            this.rootNode.getChildren().setAll(p);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
