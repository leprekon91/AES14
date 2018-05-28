package client.gui.fxcontrol;

import client.control.Client;
import com.Contract;
import com.data.User;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainScreen {
    public Object currentController;
    public AnchorPane rootNode;
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
            p = FXMLLoader.load(getClass().getResource(Contract.clientFXML + "Wait.fxml"));
            setAnchorsFitScreen(p);
            this.rootNode.getChildren().setAll(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(user.getAuth()==Contract.AUTHORIZE){}
        if(user.getAuth()==Contract.AUTH_YES){
            System.out.println("User is Authorized");
        }
        else{
            System.out.println("User is not Authorized");
        }
    }

}
