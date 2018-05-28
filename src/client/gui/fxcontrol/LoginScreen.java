package client.gui.fxcontrol;


import client.control.Client;
import com.data.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018 FXController for the login form.
 */
public class LoginScreen {

    public TextField usernameTextfield;
    public javafx.scene.control.PasswordField passwordField;
    public Button loginBtn;
    public Button cnclBtn;

    public MainScreen mainScreenController;


    public void initialize() {

        loginBtn.setDisable(true);
        usernameTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            loginBtn.setDisable(newValue.trim().isEmpty());
        });
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginBtn.setDisable(newValue.trim().isEmpty());
        });
    }




    public void loginBtnHandler(ActionEvent actionEvent) {
        User user = new User(usernameTextfield.getText(), passwordField.getText());
        System.out.println(user);
        mainScreenController.client.requestAuth(user);
        //TODO: close window and wait for authorization
        mainScreenController.user=user;
        mainScreenController.switchToWait();
    }

    public void cancelBtnHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.close();
    }
}
