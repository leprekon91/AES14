package client.control;

import client.gui.fxcontrol.LoginFXControl;
import com.Contract;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018
 * <p>
 * Here, the client will start it's process. This class will run the first
 * javaFX form and will initialize the client.
 */
public class Start extends Application {

    public String host = "localhost";

    CommunicationControl cc;
    Client client;


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "Login.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add(getClass().getResource(Contract.css).toExternalForm());
            primaryStage.setTitle("Test Title");

            primaryStage.setScene(scene);


            this.cc = new CommunicationControl(root);
            this.client = null;
            // get host:
            hostConfigDlg();

            ((LoginFXControl)fxmlLoader.getController()).cc=this.cc;

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
            //start Client
            try {
                System.out.println("host:" + host + " port:" + Contract.DEFAULT_PORT);
                client = new Client(host, Contract.DEFAULT_PORT, cc);
                cc.setClient(this.client);
            } catch (Exception exception) {
                System.out.println("Error: Can't setup connection!"
                        + " Terminating client.");
                exception.printStackTrace();
                System.exit(1);
            }

        } else {
            System.exit(0);
        }
    }
}
