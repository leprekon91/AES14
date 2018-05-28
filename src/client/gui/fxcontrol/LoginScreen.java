package client.gui.fxcontrol;


import client.control.Client;
import client.control.UserAuth;
import com.Contract;
import com.data.Message;
import com.data.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018 FXController for the login form.
 * 
 */
public class LoginScreen {

	public TextField usernameTextfield;
	public javafx.scene.control.PasswordField passwordField;
	public Button loginBtn;
	public Button cnclBtn;
public Client client;


	public void initialize() {

		loginBtn.setDisable(true);
		usernameTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
			loginBtn.setDisable(newValue.trim().isEmpty());
		});
		passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
			loginBtn.setDisable(newValue.trim().isEmpty());
		});
	}



    public void authorize(boolean ans) {
		if(ans){
			//user is authorized!
		}
		else{
			//user isn't authorized!
		}
    }

	public void loginBtnHandler(ActionEvent actionEvent) {
		User user = new User(usernameTextfield.getText(),passwordField.getText());
		System.out.println(user);
		//TODO: send user data to client for server authentication
		UserAuth uAuth = new UserAuth(user,client);
		//TODO: close window and wait for authorization
	}

	public void cancelBtnHandler(ActionEvent actionEvent) {
		Stage stage = (Stage) loginBtn.getScene().getWindow();
		stage.close();
	}
}
