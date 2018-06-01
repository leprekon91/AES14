package client.gui.fxcontrol;


import client.control.AuthControl;
import com.Contract;
import com.data.User;
import com.style.icons.FontAwesome;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.text.Text;

/**
 * LoginScreen Controller
 */
public class LoginScreen {

    public AuthControl authControl;

    public Label lockIcon;
    public Label userIcon;
    public Label keyIcon;

    public TextField txtUserName;
    public PasswordField txtPassword;
    public Button btnLogin;

    public Label lblStatus;

    public ProgressIndicator pIndicator;

    public void initialize() {
        lockIcon.setText(FontAwesome.ICON_LOCK);
        userIcon.setText(FontAwesome.ICON_USER);
        keyIcon.setText(FontAwesome.ICON_KEY);
        lockIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
        userIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
        keyIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
    }

    public void displayErrorMessage() {
        pIndicator.setVisible(false);
        lblStatus.setVisible(true);

    }

    public void loginBtnHandler(ActionEvent actionEvent) {
        User u = new User(txtUserName.getText(), txtPassword.getText());
        pIndicator.setVisible(true);
        authControl.sendUserForAuthentication(u);
    }
}
