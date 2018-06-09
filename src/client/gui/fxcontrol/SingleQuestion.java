package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.Contract;
import com.data.Question;
import com.data.Subject;
import com.style.icons.FontAwesome;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SingleQuestion {
    public TextArea textField;
    public TextArea answer1Field;
    public TextArea answer2Field;
    public TextArea answer3Field;
    public TextArea answer4Field;
    public TextField teacherNameField;
    public ComboBox correctAnswerCmb;
    public ComboBox subjectCmb;

    public Label backIcon;
    public Label saveIcon;
    public Label clearIcon;


    private Question question;
    public TeacherControl teacherControl;
    public ArrayList<Subject> subjectList;

    public static SingleQuestion openDialog(Stage primaryStage, Question q, TeacherControl teacherControl) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Report.class.getResource(Contract.clientFXML + "SingleQuestion.fxml"));

        Parent root = fxmlLoader.load();
        SingleQuestion dialogController = fxmlLoader.getController();
        dialogController.setQuestion(q);
        dialogController.teacherControl = teacherControl;
        dialogController.initQuestion();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Report.class.getResource(Contract.css).toExternalForm());
        primaryStage.setTitle("Question Dialog");
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
        return dialogController;
    }

    public void initQuestion() {
        textField.setText(question.getQuestionText());
        answer1Field.setText(question.getAns(0));
        answer2Field.setText(question.getAns(1));
        answer3Field.setText(question.getAns(2));
        answer4Field.setText(question.getAns(3));
        teacherNameField.setText(teacherControl.teacher.getFirst_name()
                + " "
                + teacherControl.teacher.getLast_name());
        correctAnswerCmb.getItems().addAll("Answer 1", "Answer 2", "Answer 3", "Answer 4");
        if (question.getCorrectAnswer() != 0) {
            correctAnswerCmb.getSelectionModel().select(question.getCorrectAnswer());
        }
        subjectList = teacherControl.subjectList;
        subjectCmb.getItems().addAll(subjectList);
        for (Subject s :
                subjectList) {
            if (s.getSubjectID() == question.getSubjectId()) {
                subjectCmb.getSelectionModel().select(subjectList.indexOf(s));
            }
        }

    }

    public void initialize() {
        backIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
        backIcon.setText(FontAwesome.ICON_ANGLE_RIGHT);
        saveIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
        clearIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));


    }


    public void clearBtnHandler(ActionEvent actionEvent) {
        textField.clear();
        answer1Field.clear();
        answer2Field.clear();
        answer3Field.clear();
        answer4Field.clear();
    }


    public void closeDialog(ActionEvent actionEvent) {
        ((Stage) textField.getScene().getWindow()).close();
    }

    public void saveBtnHandler(ActionEvent actionEvent) {
        question.setQuestionText(textField.getText());
        question.setCorrectAnswer(correctAnswerCmb.getSelectionModel().getSelectedIndex() + 1);
        question.setPossibleAnswers(new String[]{
                answer1Field.getText(),
                answer2Field.getText(),
                answer3Field.getText(),
                answer4Field.getText()
        });
        question.setSubjectId(((Subject) subjectCmb.getSelectionModel().getSelectedItem()).getSubjectID());
        closeDialog(actionEvent);
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
