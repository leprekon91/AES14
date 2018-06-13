package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.Contract;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;


public class TeacherMenu {


    public TeacherControl teacherControl = TeacherControl.getInstance();
    public AnchorPane questionPane;
    public AnchorPane examPane;
    public BorderPane welcomeScreen;


    public void initialize() {
        //load question pane
        loadQuestionPane();
        loadExamPane();
        hideAll();
    }


    public void loadQuestionPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "QuestionListView.fxml"));
            Parent root = fxmlLoader.load();
            questionPane.getChildren().setAll(root);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadExamPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "ExamManagement.fxml"));
            Parent root = fxmlLoader.load();
            examPane.getChildren().setAll(root);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void hideAll() {
        questionPane.setVisible(false);
        examPane.setVisible(false);
        welcomeScreen.setVisible(true);
    }

    public void switchToQuestionView(ActionEvent actionEvent) {

        examPane.setVisible(false);
        welcomeScreen.setVisible(false);
        questionPane.setVisible(true);

    }

    public void switchToExamView(ActionEvent actionEvent) {

        questionPane.setVisible(false);
        welcomeScreen.setVisible(false);
        examPane.setVisible(true);

    }
}
