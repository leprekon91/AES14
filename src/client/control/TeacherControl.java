package client.control;

import client.gui.fxcontrol.TeacherMenu;
import com.Contract;
import com.data.Exam;
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

/**
 * TeacherControl class controls all data streams from the teacher perspective
 */
public class TeacherControl extends Application {
    public Teacher teacher;
    public Client client;

    /**
     * Start Method: Opens the Teacher Menu Window
     *
     * @param primaryStage Stage for the window.(Supplied by AuthControl)
     */
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


    //Question Communication Methods

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
     * Request question from the database by question ID.
     *
     * @param question question id for the question that is requested.
     */
    public void requestQuestion(Question question) throws Exception {
        Message message = new Message(Contract.READ_QUESTION, question);
        client.teacherControl = this;
        client.sendToServer(message);


    }

    /**
     * Update question parameters
     *
     * @param question question to be updated
     */
    public void updateQuestion(Question question) throws Exception {
        Message message = new Message(Contract.UPDATE_QUESTION, question);
        client.teacherControl = this;
        client.sendToServer(message);

    }

    /**
     * Delete Question from the database
     *
     * @param question question to be deleted
     */
    public void deleteQuestion(Question question) throws Exception {
        if (question != null) {
            Message message = new Message(Contract.DELETE_QUESTION, question);
            client.sendToServer(message);
        } else {
            throw new Exception(this.getClass().toString() + ": Question is NULL!");
        }
    }

    /**
     * Request all question under a specific subject
     *
     * @param subjectId the id of the subject
     */
    public void requestQuestionsBySubjectId(int subjectId) throws Exception {
        Message message = new Message(Contract.GET_QUESTIONS_BY_SUBJECT, subjectId);
        client.teacherControl = this;
        client.sendToServer(message);
    }

    /**
     * Request all question in a specific Exam
     *
     * @param examId id of the Exam
     */
    public void requestQuestionsByExamId(int examId) throws Exception {
        Message message = new Message(Contract.GET_QUESTIONS_BY_EXAM, examId);
        client.teacherControl = this;
        client.sendToServer(message);
    }

    /**
     * Request all question written by the teacher that is logged in.
     */
    public void requestQuestionsByTeaacherId() throws Exception {
        String teacherID = this.teacher.getUsername();
        Message message = new Message(Contract.GET_QUESTIONS_BY_TEACHER, teacherID);
        client.teacherControl = this;
        client.sendToServer(message);
    }

    /**
     * Answer for the requestQuestion() method received from the server.
     * called by the client!
     *
     * @param question Question object sent by the server.
     */
    public void receiveQuestion(Question question) throws NullPointerException {
        if (question != null) {
            System.out.println("Question Received: \n" + question.toString());
            return;
        }
        throw new NullPointerException(this.getClass() + "NULL Question Object received from Server!");
    }

    /**
     * Receive server response for array of questions.
     *
     * @param questions arrayList of question Objects
     * @return ArrayList of question Objects
     */
    public void receiveQuestionArray(ArrayList<Question> questions) {
        if (questions != null) {
            if (questions.size() > 0) {//Check that there are questions in the array list
                System.out.println("Question Array Received: \n" + questions.toString());
                return;
            }
        }
        throw new NullPointerException(this.getClass() + "NULL or Empty Question Array received from Server!");

    }

    //Exam Communication Methods

    /**
     * Create New Exam Entry in the Database
     *
     * @param exam exam object that defines the entry
     */
    public void createExam(Exam exam) {
        //TODO stub Method
    }

    /**
     * Request an Exam Object from the server by ID
     *
     * @param examID
     */
    public void requestExam(int examID) {
        //TODO stub Method
    }

    /**
     * Request all exams written by a specific teacher
     *
     * @param teacherID
     */
    public void requestExamsByTeacher(String teacherID) {
        //TODO stub Method
    }

    /**
     * Request all exams under a specific subject
     *
     * @param subjectID id number of subject
     */
    public void requestExamsBySubjectID(int subjectID) {
        //TODO stub Method
    }

    /**
     * Request all Exams under a specific course
     *
     * @param courseID id of course
     */
    public void requestExamsByCourseID(int courseID) {

    }

    /**
     * Update existing entry in Exam table
     *
     * @param exam Exam Object to be updated
     */
    public void updateExam(Exam exam) {
        //TODO stub Method
    }

    /**
     * Delete exam entry from the database
     *
     * @param exam Exam to be deleted.
     */
    public void deleteExam(Exam exam) {
        //TODO stub Method
    }

    /**
     * Receive Exam from Server
     *
     * @param exam exam that was received
     */
    public void receiveExam(Exam exam) {
        //TODO stub Method
    }

    /**
     * Receive Exam ArrayList from Server
     *
     * @param exams exam array that was received
     */
    public void receiveExamArray(ArrayList<Exam> exams) {
        //TODO stub Method
    }
}
