package server.gui.fxcontrol;

import com.Contract;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class Dashboard {

	public AnchorPane userPanel;
	public VBox errLog;
	public ErrLog errControl;
	@FXML
	Tab userTab;
	@FXML
	Tab errorTab;

	public void initialize() {
		try {
			userPanel = FXMLLoader.load(getClass().getResource(Contract.serverFXML + "userPanel.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource(Contract.serverFXML + "errLog.fxml"));
			errLog = loader.load();
			errControl = (ErrLog) loader.getController();
			userTab.setContent(userPanel);
			errorTab.setContent(errLog);

		} catch (IOException e) {
			e.printStackTrace();
		}

		addLogMsg("Session Started: " + Timestamp.valueOf(LocalDateTime.now()));
	}

	public void addLogMsg(String str) {
		errControl.addEntry(str);
	}
}
