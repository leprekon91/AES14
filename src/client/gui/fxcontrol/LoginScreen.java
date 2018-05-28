package client.gui.fxcontrol;


import com.data.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018 FXController for the login form.
 * 
 */
public class LoginScreen {


	public Button loginBtn;
	public Button cnclBtn;
	public TextField usernameEditText;
	public javafx.scene.control.PasswordField passwordField;

	public void initialize() {


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
		//TODO: validate the form

		User user = new User(usernameEditText.getText(),passwordField.getText());
		//TODO: send user data to client for server authentication
		//TODO: close window and wait for authorization
	}

	public void cancelBtnHandler(ActionEvent actionEvent) {
	}
}
