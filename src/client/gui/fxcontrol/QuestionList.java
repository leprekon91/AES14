package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.Contract;
import com.data.Question;
import com.data.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class QuestionList {
    public TeacherControl teacherControl;
    public ObservableList<Question> questions = FXCollections.observableArrayList();

    public ListView questionListView;

    public void initialize() {

        questionListView.setCellFactory(param -> new QuestionListViewCell());

        questionListView.setItems(questions);
    }

    public static QuestionList load(AnchorPane root) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(QuestionList.class.getResource(Contract.clientFXML + "QuestionList.fxml"));
        AnchorPane content = fxmlLoader.load();
        setAnchorsFitScreen(content);
        root.getChildren().setAll(content);
        QuestionList controller = fxmlLoader.getController();
        return controller;
    }

    public void openNewQuestionDialog(ActionEvent actionEvent) {
        Teacher teacher = teacherControl.teacher;
        Question question = new Question("", new String[]{"", "", "", ""}, 0, 0, teacher.getUsername());
        try {
            SingleQuestion singleQuestion = SingleQuestion.openDialog(new Stage(), question, this.teacherControl);
            question = singleQuestion.getQuestion();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(question);
        if (!question.getQuestionText().equals("")) {
            //  sendCreateQuestionMessage(question);
        }
        questionListView.getItems().add(question);
        questionListView.refresh();
    }

    public void sendCreateQuestionMessage(Question question) {
        try {
            teacherControl.createQuestion(question);
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


    private static void setAnchorsFitScreen(Node n) {
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
    }
}
