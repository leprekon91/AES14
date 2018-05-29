package client.control;

import com.Contract;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018
 * <p>
 * Here, the client-side will start it's process. This class will run the first
 * javaFX form and will initialize the client.
 * </p>
 */
public class Start extends Application {

    public String host = "localhost";//initial host value

    public Client client;


    @Override
    public void start(Stage primaryStage) {
        try {
            //Set MainScreen
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "MainScreen.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 800, 400);
            scene.getStylesheets().add(getClass().getResource(Contract.css).toExternalForm());
            primaryStage.setTitle("AES Client");
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);

            // get host and start the client:
            this.client = null;
            hostConfigDlg();
            //show MainScreen
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Set Close event handle
        primaryStage.setOnCloseRequest(event -> {
            try {
                //Close connection to server.
                client.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Host Configuration Dialog - this configures the host location of the server (IP addr.)
     */
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
