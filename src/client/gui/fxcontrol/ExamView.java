package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.Contract;
import com.data.Exam;
import com.data.Question;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ExamView {
    public Text examViewTitle;
    public Label authorLbl;
    public Label subjectLbl;
    public Label course;
    public JFXListView questionList;
    public Label assignedTimeLbl;
    public Label studentNoteLbl;
    public Label teacherNoteLbl;
    private ObservableList<Question> questions = FXCollections.observableArrayList();

    public static void openWindow(Stage stage, Exam exam) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingleQuestion.class.getResource(Contract.clientFXML + "ExamView.fxml"));
        Parent root = fxmlLoader.load();
        ExamView dialogController = fxmlLoader.getController();
        dialogController.setExam(exam);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(QuestionView.class.getResource(Contract.css).toExternalForm());
        stage.setTitle("Exam #" + exam.getExamIDStr());
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void initialize() {
        questionList.setItems(questions);
        questionList.setCellFactory(list -> new QuestionListViewCell());
        questionList.setOnMouseClicked(click -> {

            if (click.getClickCount() == 2) {
                //open question view
                try {
                    QuestionView.openWindow(new Stage(), (Question) questionList.getSelectionModel()
                            .getSelectedItem());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void setExam(Exam exam) {

        examViewTitle.setText("Exam #" + exam.getExamIDStr());
        authorLbl.setText("Written by:" + exam.getExamAuthorTeacher().getFullName());
        subjectLbl.setText(exam.getExamSubject().getSubjectName() + " (" + exam.getExamSubject().getSubjectID() + ')');
        course.setText(exam.getExamCourse().getCourseName() + " (" + exam.getExamCourse().getCourseNumber() + ')');
        assignedTimeLbl.setText(" " + exam.getAssignedTime() + " minutes.");
        studentNoteLbl.setText("For Students:\n" + exam.getStudentNotes());
        teacherNoteLbl.setText("For Teachers:\n" + exam.getTeacherNotes());
        questions.setAll(exam.getExamQuestions());
        System.out.println(TeacherControl.getInstance().exams);
    }
}
