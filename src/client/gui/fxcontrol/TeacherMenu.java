package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.Contract;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 *
 */
public class TeacherMenu {


    public TeacherControl teacherControl = TeacherControl.getInstance();
    public AnchorPane questionPane;
    public AnchorPane examPane;
    public BorderPane welcomeScreen;
    public AnchorPane examInProgressPane;
    public AnchorPane gradesForApproval;

    /**
     * initialize the window
     */
    public void initialize() {
        //load question pane
        loadQuestionPane();
        loadExamPane();
        loadEIPView();
        hideAll();
    }

    /**
     * Load Question Manager View
     */
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

    /**
     * Load Exam Manager View
     */
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

    public void loadEIPView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "EIPManagement.fxml"));
            Parent root = fxmlLoader.load();
            examInProgressPane.getChildren().setAll(root);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * hide all viewes. and show the welcome screen.
     */
    private void hideAll() {
        questionPane.setVisible(false);
        examPane.setVisible(false);
        examInProgressPane.setVisible(false);
        welcomeScreen.setVisible(true);
        gradesForApproval.setVisible(false);
    }

    /**
     * switch to Question Manager View
     *
     * @param actionEvent actionEvent for the javaFX Button
     */
    public void switchToQuestionView(ActionEvent actionEvent) {

        hideAll();
        welcomeScreen.setVisible(false);
        questionPane.setVisible(true);

    }

    /**
     * switch to Exam Manager View
     * @param actionEvent actionEvent for the javaFX Button
     */
    public void switchToExamView(ActionEvent actionEvent) {

        hideAll();
        welcomeScreen.setVisible(false);
        examPane.setVisible(true);

    }

    public void switchToEIPView(ActionEvent actionEvent) {
        hideAll();
        welcomeScreen.setVisible(false);
        examInProgressPane.setVisible(true);
    }

    public void gradesForApprovalView(ActionEvent actionEvent) {
        hideAll();
        welcomeScreen.setVisible(false);
        gradesForApproval.setVisible(true);
    }
}
