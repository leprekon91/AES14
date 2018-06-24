package client.control;

import com.Contract;
import com.data.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class PrincipalControl extends Application {
    public Principal principal;
    public Client client;
    private static PrincipalControl INSTANCE;
    public ObservableList<ExtensionRequest> requestList = FXCollections.observableArrayList();
    public ObservableList<Teacher> teachers = FXCollections.observableArrayList();
    public ObservableList<Student> students = FXCollections.observableArrayList();
    public ObservableList<Course> courses = FXCollections.observableArrayList();

    public static PrincipalControl getInstance() {
        if (INSTANCE == null) INSTANCE = new PrincipalControl();
        return INSTANCE;
    }


    @Override
    public void start(Stage primaryStage) {
        INSTANCE = this;
        requestAllTeachers();
        requestAllStudents();
        requestAllCourses();
        try {

            client.sendToServer(new Message(Contract.GET_PRINCIPAL_REQUESTS, new ArrayList<>()));//rewuest all messages requesting extensions
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "PrincipalMenu.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(Contract.css).toExternalForm());
            primaryStage.setTitle("AES - " + principal.getUsername());
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

    private void requestAllCourses() {
        try {
            client.sendToServer(new Message(Contract.COURSES, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestAllStudents() {
        try {
            client.sendToServer(new Message(Contract.STUDENTS, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestAllTeachers() {
        try {
            client.sendToServer(new Message(Contract.TEACHERS, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void begin() {
        launch();
    }

    public void receiveNewRequests(ArrayList<ExtensionRequest> requests) {
        System.out.println(requests);
        requestList.setAll(requests);
    }
}
