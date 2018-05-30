package client.gui.fxcontrol;


import com.Contract;
import com.style.icons.FontAwesome;
import javafx.scene.control.Label;

/**
 * LoginScreen Controller
 */
public class LoginScreen {


    public Label ico;

    public void initialize(){
        ico.setText(FontAwesome.ICON_USER);
        ico.setFont(FontAwesome.getFont(FontAwesome.SOLID));

    }

    public void displayErrorMessage(){
        System.out.println("User Is Not Authenticated.");
    }
}
