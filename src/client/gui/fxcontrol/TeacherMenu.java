package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.data.Question;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TeacherMenu {


    public TeacherControl teacherControl;
    public AnchorPane questionPane;
    public QuestionList questionListControl;

    public void initialize() {
        try {
            questionListControl = QuestionList.load(questionPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendReadQuestionMessage(ActionEvent actionEvent) {
        try {
            teacherControl.requestQuestion(new Question(300, 4));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUpdateQuestion(ActionEvent actionEvent) {
        try {
            Question q = new Question("text", new String[]{"ans1", "ans2", "ans3", "ans4"}, 2, 3, teacherControl.teacher.getUsername());
            q.setQID(1);
            q.setSubjectId(6);
            teacherControl.updateQuestion(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendDeleteQuestionMessage(ActionEvent actionEvent) {
        try {
            Question q = new Question("text", new String[]{"ans1", "ans2", "ans3", "ans4"}, 2, 3, teacherControl.teacher.getUsername());
            q.setQID(1);
            q.setSubjectId(6);
            teacherControl.deleteQuestion(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendQuestionsBySubjectIDMessage(ActionEvent actionEvent) {
        try {
            teacherControl.requestQuestionsBySubjectId(9);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendQuestionsByExamIDMessage(ActionEvent actionEvent) {
        try {
            teacherControl.requestQuestionsByExamId(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendQuestionByTeacherMessage(ActionEvent actionEvent) {
        try {
            teacherControl.requestQuestionsByTeaacherId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TeacherControl getTeacherControl() {
        return teacherControl;
    }

    public void setTeacherControl(TeacherControl teacherControl) {
        this.teacherControl = teacherControl;
        questionListControl.teacherControl = teacherControl;
        questionListControl.questions = FXCollections.observableArrayList(teacherControl.questions);
        questionListControl.questionListView.setItems(questionListControl.questions);
    }
}
