package client.control;

import client.gui.fxcontrol.TeacherMenu;
import com.Contract;
import com.data.Message;
import com.data.Question;
import com.data.Teacher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class TeacherControl extends Application {
    public Teacher teacher;
    public Client client;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "TeacherMenu.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            ((TeacherMenu) fxmlLoader.getController()).teacherControl = this;
            scene.getStylesheets().add(getClass().getResource(Contract.css).toExternalForm());
            primaryStage.setTitle("AES - " + teacher.getUsername());
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

    /**
     * Add Question to the database.
     *
     * @param question question to be created
     */
    public void createQuestion(Question question) throws Exception {
        if (question != null) {
            Message message = new Message(Contract.CREATE_QUESTION, question);
            client.sendToServer(message);
        } else {
            throw new Exception(this.getClass().toString() + ": Question is NULL!");
        }
    }

    /**
     * Delete Question from the database
     *
     * @param question question to be deleted
     */
    public void deleteQuestion(Question question) {
        //TODO Stub Method
    }

    /**
     * Read question from the database by question ID.
     *
     * @param QID
     * @return Question received from server
     */
    public Question readQuestion(int QID) {
        //TODO Stub Method
        return new Question(QID);
    }

    /**
     * Update question parameters
     *
     * @param question question to be updated
     */
    public void updateQuestion(Question question) {
        //TODO Stub Method

    }

    /**
     * get all question uunder a specific subject
     *
     * @param subjectId the id of the subject
     * @return Array List collection of Question Objects
     */
    public ArrayList<Question> getQuestionsBySubjectId(int subjectId) {
        //TODO Stub Method
        return new ArrayList<Question>();
    }

    /**
     * get all question in a specific Exam
     *
     * @param examId id of the Exam
     * @return Array List collection of Question Objects
     */
    public ArrayList<Question> getQuestionsByExamId(int examId) {
        //TODO Stub Method
        return new ArrayList<Question>();
    }

    /**
     * get all question written by the teacher that is logged in.
     *
     * @return
     */
    public ArrayList<Question> getQuestionsByTeaacherId() {
        String teacherID = this.teacher.getUsername();
        //TODO Stub Method
        return new ArrayList<Question>();
    }

}
