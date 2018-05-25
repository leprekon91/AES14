package client.control;

import java.util.Optional;

import com.Contract;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018
 * 
 *       Here, the client will start it's process. This class will run the first
 *       javaFX form and will initialize the client.
 */
public class Start extends Application {

	public String host = "localhost";

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.fxml + "Login.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource(Contract.css).toExternalForm());
			primaryStage.setTitle("Test Title");
			primaryStage.setMaximized(true);
			primaryStage.setScene(scene);
			
			// get host:
			hostConfigDlg();
			
			// TODO: set client
			
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void hostConfigDlg() {
		// "Configure Host" Dialog
		TextInputDialog hostConfig = new TextInputDialog(host);
		hostConfig.setTitle("Host Server Configuration");
		ImageView iv = new ImageView(this.getClass().getResource(Contract.graphics + "Icon.png").toString());
		iv.setFitWidth(50);
		iv.setFitHeight(50);
		hostConfig.setGraphic(iv);
		hostConfig.setHeaderText(null);
		hostConfig.setContentText("Enter Host IP address: ");
		Optional<String> res = hostConfig.showAndWait();
		if (res.isPresent()) {
			host = res.get();
			//TODO: check if the client can connect, if it cant show dialog again
		} else {
			System.exit(0);
		}
	}
}
