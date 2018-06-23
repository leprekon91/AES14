package client.control;

import com.Contract;
import com.data.ExamInProgress;
import com.data.Message;
import com.data.Student;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentControl extends Application {
    public Student student;
    public Client client;
    private static StudentControl INSTANCE;
    public ObservableList<ExamInProgress> eips = FXCollections.observableArrayList();

    public static StudentControl getInstance() {
        if (INSTANCE == null) INSTANCE = new StudentControl();
        return INSTANCE;
    }

    @Override
    public void start(Stage primaryStage)  {
        INSTANCE = this;
        requestEipsByStudent();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "StudentMenu.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(Contract.css).toExternalForm());
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Set Close event handle
        primaryStage.setOnCloseRequest(event -> {
            try {
                //Close connection to server.
                client.sendToServer(new Message(Contract.LOG_OFF, client.user));
                client.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void requestEipsByStudent() {
        try {
            client.sendToServer(new Message(Contract.GET_EXAMS_IN_PROGRESS, student));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
