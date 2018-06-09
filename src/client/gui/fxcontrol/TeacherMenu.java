package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.data.Question;
import com.data.Teacher;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherMenu {
    public TeacherControl teacherControl;
    public AnchorPane mainPane;


    public void openNewQuestionDialog(ActionEvent actionEvent) {
        Teacher teacher = teacherControl.teacher;
        Question question = new Question("text", new String[]{"ans1", "ans2", "ans3", "ans4"}, 0, 5, teacher.getUsername());
        try {
            SingleQuestion singleQuestion = SingleQuestion.openDialog(new Stage(), question, this.teacherControl);
            question = singleQuestion.getQuestion();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(question);

    }

    public void sendCreateQuestionMessage(ActionEvent actionEvent) {
        try {
            teacherControl.createQuestion(new Question("text", new String[]{"ans1", "ans2", "ans3", "ans4"}, 2, 3, teacherControl.teacher.getUsername()));
        } catch (Exception e) {
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
}
