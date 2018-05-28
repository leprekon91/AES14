package client.gui.fxcontrol;

import client.control.CommunicationControl;
import com.Contract;
import com.data.Message;
import com.style.icons.FontAwesome;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018 FXController for the login form.
 * 
 */
public class LoginFXControl {
	public CommunicationControl cc;

	public void initialize() {


	}

	public void testSendMessage(ActionEvent actionEvent) {
		try {
			cc.getClient().sendToServer(new Message(Contract.ABSTRACT,"Hello!"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
