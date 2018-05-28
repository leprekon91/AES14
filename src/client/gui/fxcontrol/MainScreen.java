package client.gui.fxcontrol;

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


    public void initialize() {
        //first, display the login screen
        try {
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource(Contract.clientFXML + "LoginScreen.fxml"));
            login = fxmlLoader.load();
            currentController = fxmlLoader.getController();
            rootNode.getChildren().add(login);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
