package client.control;

import com.Contract;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
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

    Client client;


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "MainScreen.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add(getClass().getResource(Contract.css).toExternalForm());
            primaryStage.setTitle("AES Client");
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);


            this.client = null;
            // get host and start the client:
            hostConfigDlg();

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    client.closeConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
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
                client = new Client(host, Contract.DEFAULT_PORT, client);
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
