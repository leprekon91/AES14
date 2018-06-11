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
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.IOException;
import java.util.ArrayList;

public class SingleQuestion {
    public TextField qidTextField;
    public TextArea textField;
    public TextArea answer1Field;
    public TextArea answer2Field;
    public TextArea answer3Field;
    public TextArea answer4Field;
    public TextField teacherNameField;
    public ComboBox correctAnswerCmb;
    public ComboBox subjectCmb;
    public Button saveBtn;

    public Label backIcon;
    public Label saveIcon;
    public Label clearIcon;


    private Question question;
    public TeacherControl teacherControl;
    public ArrayList<Subject> subjectList;

    public static SingleQuestion openDialog(Stage primaryStage, Question q) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingleQuestion.class.getResource(Contract.clientFXML + "SingleQuestion.fxml"));

        Parent root = fxmlLoader.load();
        SingleQuestion dialogController = fxmlLoader.getController();
        dialogController.setQuestion(q);
        dialogController.teacherControl = TeacherControl.getInstance();
        dialogController.initQuestion();
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(Report.class.getResource(Contract.css).toExternalForm());
        primaryStage.setTitle("Question Dialog");
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
        return dialogController;
    }

    public void initQuestion() {
        qidTextField.setText(question.getQIDString());
        textField.setText(question.getQuestionText());
        answer1Field.setText(question.getAns(0));
        answer2Field.setText(question.getAns(1));
        answer3Field.setText(question.getAns(2));
        answer4Field.setText(question.getAns(3));
        teacherNameField.setText(question.getAuthor().getFirst_name()
                + " " + question.getAuthor().getLast_name());
        correctAnswerCmb.getItems().addAll("\u2776 Answer 1", "\u2777 Answer 2", "\u2778; Answer 3", "\u2779 Answer 4");
        if (question.getCorrectAnswer() != 0) {
            correctAnswerCmb.getSelectionModel().select(question.getCorrectAnswer() - 1);
        }
        subjectList = teacherControl.subjectList;
        subjectCmb.getItems().addAll(subjectList);
        if (question.getSubject() != null)
            for (Subject s :
                    subjectList) {
                if (s.getSubjectID() == question.getSubject().getSubjectID()) {
                    subjectCmb.getSelectionModel().select(subjectList.indexOf(s));
                }
            }

    }

    public void initialize() {
        backIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
        backIcon.setText(FontAwesome.ICON_ANGLE_RIGHT);
        saveIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
        clearIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
        ValidationSupport support = new ValidationSupport();
        support.registerValidator(textField, Validator.createEmptyValidator("Question Must Have A Text!"));
        support.registerValidator(answer1Field, Validator.createEmptyValidator("Question Must Have All 4 Answers!"));
        support.registerValidator(answer2Field, Validator.createEmptyValidator("Question Must Have All 4 Answers!"));
        support.registerValidator(answer3Field, Validator.createEmptyValidator("Question Must Have All 4 Answers!"));
        support.registerValidator(answer4Field, Validator.createEmptyValidator("Question Must Have All 4 Answers!"));
        support.registerValidator(subjectCmb, Validator.createEmptyValidator("Subject Selection Is Required!"));
        support.registerValidator(correctAnswerCmb, Validator.createEmptyValidator("Correct Answer Selection Is Required!"));
        saveBtn.disableProperty().bind(support.invalidProperty());

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
        question.setSubject(((Subject) subjectCmb.getSelectionModel().getSelectedItem()));
        closeDialog(actionEvent);
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
