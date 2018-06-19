package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.Contract;
import com.data.Course;
import com.data.Exam;
import com.data.Question;
import com.data.Subject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.ListSelectionView;

import java.io.IOException;

public class CreateExamDialog {
    private Exam exam;

    //step 1
    public AnchorPane step1Pane;
    public JFXComboBox subjectCmb;
    public JFXComboBox courseCmb;
    public Label authorLbl;
    public Label step1Lbl;

    //step 2
    public AnchorPane step2Pane;
    public ListSelectionView questionSelectionList;
    public Label step2Lbl;

    //step 3
    public AnchorPane step3Pane;
    public JFXListView questionsGradesListView;
    public Label totalGradeCnt;
    public Label step3Lbl;

    //Main Pane
    public JFXButton nextBtn;
    public JFXButton backBtn;
    public AnchorPane mainPane;
    private int step = 1;//current step

    private Subject selectedSubject;
    private Course selectedCourse;
    private ObservableList<Question> selectedQuestions = FXCollections.observableArrayList();
    private ObservableList<Question> unSelectedQuestions = FXCollections.observableArrayList();

    public static void openWindow(Stage stage, Exam e) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SingleQuestion.class.getResource(Contract.clientFXML + "CreateExamDialog.fxml"));
        Parent root = fxmlLoader.load();
        CreateExamDialog dialogController = fxmlLoader.getController();
        dialogController.setExam(e);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(QuestionView.class.getResource(Contract.css).toExternalForm());
        stage.setTitle("Create Exam");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void initialize() {

        authorLbl.setText(TeacherControl.getInstance().teacher.getFullName());
        subjectCmb.setItems(FXCollections.observableArrayList(TeacherControl.getInstance().teacher.getTeacherSubjectList()));

        FilteredList<Course> filteredCourses = new FilteredList<>(
                FXCollections.observableArrayList(TeacherControl.getInstance().teacher.getTeacherCourses()),
                s -> true
        );


        subjectCmb.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Subject>) (ov, oldVal, newVal) -> {
            if (newVal != null) selectedSubject = newVal;
            filteredCourses.setPredicate(course -> {
                if (newVal == null) {
                    return false;
                }
                return course.getCourseSubject().getSubjectID() == newVal.getSubjectID();
            });
        });
        courseCmb.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Course>) (ov, oldVal, newVal) -> {
            if (newVal != null) selectedCourse = newVal;
        });

        courseCmb.setItems(filteredCourses);
        questionSelectionList.setCellFactory(questionListView -> new QuestionListViewCell());
        questionSelectionList.setSourceItems(unSelectedQuestions);
        questionSelectionList.setTargetItems(selectedQuestions);

        questionsGradesListView.setCellFactory(list -> new GradedQuestionListCell());

    }


    private void setExam(Exam e) {
        this.exam = e;
    }

    public void nextStep(ActionEvent actionEvent) {
        hideAll();
        if (step == 1) {
            step++;
            step2Pane.setVisible(true);
            backBtn.setDisable(false);
            step1Lbl.setStyle("-fx-border-color:#555555;-fx-border-radius: 15;");
            step2Lbl.setStyle("-fx-border-color:LIGHTBLUE;-fx-border-radius: 15;");
            for (Question q :
                    TeacherControl.getInstance().questions) {
                if (q.getSubject().getSubjectID() == selectedSubject.getSubjectID()) {
                    unSelectedQuestions.add(q);
                } else {
                    unSelectedQuestions.remove(q);
                }
            }
            questionSelectionList.setSourceItems(unSelectedQuestions);
        } else if (step == 2) {
            step++;
            step3Pane.setVisible(true);
            step2Lbl.setStyle("-fx-border-color:#555555;-fx-border-radius: 15;");
            step3Lbl.setStyle("-fx-border-color:LIGHTBLUE;-fx-border-radius: 15;");
            nextBtn.setText("\u2714 Finish");
            questionsGradesListView.setItems(questionSelectionList.getTargetItems());
        } else {
            //finish
        }
    }

    public void prevStep(ActionEvent actionEvent) {
        hideAll();
        if (step == 2) {
            step--;
            backBtn.setDisable(true);
            step1Pane.setVisible(true);
            step2Lbl.setStyle("-fx-border-color:#555555;-fx-border-radius: 15;");
            step1Lbl.setStyle("-fx-border-color:LIGHTBLUE;-fx-border-radius: 15;");
        }
        if (step == 3) {
            step--;
            step2Pane.setVisible(true);
            nextBtn.setText("\u2770 Next");
            step3Lbl.setStyle("-fx-border-color:#555555;-fx-border-radius: 15;");
            step2Lbl.setStyle("-fx-border-color:LIGHTBLUE;-fx-border-radius: 15;");
        }
    }

    public void hideAll() {
        step1Pane.setVisible(false);
        step2Pane.setVisible(false);
        step3Pane.setVisible(false);
    }
}
