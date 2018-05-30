
package client.control;

import client.gui.fxcontrol.LoginScreen;
import com.Contract;
import com.data.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This Class Will Run Authentication for The User
 */
public class AuthControl {
    public Client client;
    public LoginScreen loginScreen;

    /**
     * Display Login Screen
     *
     * @param primaryStage Stage to set the login screen to
     * @param client       Client object through whitch the authentication process will be done.
     */
    public void displayLogin(Stage primaryStage, Client client) {
        this.client = client;
        //Set LoginScreen and Display it
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "LoginScreen.fxml"));
            Parent root = fxmlLoader.load();
            loginScreen = fxmlLoader.getController();
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(getClass().getResource(Contract.css).toExternalForm());
            primaryStage.setTitle("AES Client");
            //primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //For testing purposes:_____________________
        sendUserForAuthentication(new User("teacher","teacherpass"));

    }

    public void sendUserForAuthentication(User user) {
        client.requestAuth(user, this);
    }

    /**
     * This method will recieive the user sent from server through the client.
     * If the type of the user is 0, it means that the User is not authenticated.
     * <p>
     * 1 - Student
     * 2 - Teacher
     * 3 - Principal
     *
     * @param user
     */
    public void receiveAuthenticationAnswer(User user) {

        switch (user.getType()) {
            case 1:
                //Open Student Menu
                System.out.println("User is a student and he is logged in!");
                break;
            case 2:
                //Open Teacher Menu
                System.out.println("User is a teacher and he is logged in!");
                break;
            case 3:
                //Open Principal Menu
                System.out.println("User is a principal and he is logged in!");
                break;
            default:
                loginScreen.displayErrorMessage("User Could not be Authenticated...");
                break;
        }
    }
}
