package client.control;

import com.Contract;
import com.data.Message;
import com.data.Teacher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherControl extends Application {
    public Teacher teacher;
    public Client client;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "TeacherMenu.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(Contract.css).toExternalForm());
            primaryStage.setTitle("AES - " + teacher.getId());
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();
            //primaryStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
//Set Close event handle
        primaryStage.setOnCloseRequest(event -> {
            try {
                //Close connection to server.
                client.sendToServer(new Message(Contract.LOG_OFF, null));
                client.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
