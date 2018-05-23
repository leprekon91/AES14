package client.gui.fxcontrol;

import client.gui.style.icons.FontAwesome;
import javafx.scene.text.Text;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018 FXController for the login form.
 * 
 */
public class LoginFXControl {
	public Text txt;

	public void initialize() {
		txt.setFont(FontAwesome.getFont(FontAwesome.SOLID));
		txt.setText(FontAwesome.ICON_AMBULANCE);

	}
}
