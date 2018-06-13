package client.control;

import com.Contract;
import com.data.Message;
import com.data.Question;
import com.data.Subject;
import com.data.Teacher;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * TeacherControl class controls all data streams from the teacher perspective
 */
public class TeacherControl extends Application {
    public Teacher teacher;
    public Client client;

    public ObservableList<Question> questions = FXCollections.observableArrayList();
    private static TeacherControl INSTANCE;

    public static TeacherControl getInstance() {
        if (INSTANCE == null) INSTANCE = new TeacherControl();
        return INSTANCE;
    }

    /**
     * Start Method: Opens the Teacher Menu Window
     *
     * @param primaryStage Stage for the window.(Supplied by AuthControl)
     */
    @Override
    public void start(Stage primaryStage) {
        INSTANCE = this;
        //fill data from database
        requestSubjectListByTeacher();
        requestAllQuestions();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "TeacherMenu.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(Contract.css).toExternalForm());
            primaryStage.setTitle("AES - " + teacher.getFirst_name() + " " + teacher.getLast_name());
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

    //Subject communication Methods.
    public void requestSubjectListByTeacher() {
        Message message = new Message(Contract.GET_SUBJECTS_BY_TEACHER, teacher);
        try {
            client.teacherControl = this;
            client.sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveSubjectListByTeacher(ArrayList<Subject> subjects) {
        this.teacher.setTeacherSubjectList(subjects);
    }

    public void receiveAllQuestions(ArrayList<Question> questions) {
        Platform.runLater(() -> {
            this.questions.setAll(questions);
            System.out.println("Questions Were Changed in observable List - " + questions);
        });
    }

    public void requestAllQuestions() {
        try {
            this.client.sendToServer(new Message(Contract.QUESTIONS, new ArrayList<Question>()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}