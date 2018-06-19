package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.Contract;
import com.data.ExamInProgress;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateNewEIPDialog {
    public AnchorPane viewPane;
    public JFXButton studentSelectionBtn;
    public AnchorPane studentSelectionPane;
    public AnchorPane detailsPane;
    public JFXListView examList;
    public JFXDatePicker startDatePicker;
    public JFXTimePicker startTimePicker;
    public JFXTimePicker endTimePicker;
    public JFXDatePicker endDatePicker;
    public JFXTextField pswdField;
    private ExamInProgress examInProgress;

    public static void openWindow(Stage stage, ExamInProgress eip) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingleQuestion.class.getResource(Contract.clientFXML + "CreateNewEIPDialog.fxml"));
        Parent root = fxmlLoader.load();
        CreateNewEIPDialog dialogController = fxmlLoader.getController();
        dialogController.setExamInProgress(eip);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(QuestionView.class.getResource(Contract.css).toExternalForm());
        stage.setTitle("Create Exam");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void initialize() {
        examList.setItems(TeacherControl.getInstance().exams);
        examList.setCellFactory(exams -> new ExamListViewCell());
    }

    private void setExamInProgress(ExamInProgress eip) {
        this.examInProgress = eip;
    }

    public void switchToStudentSelection(ActionEvent actionEvent) {
        detailsPane.setVisible(false);
        studentSelectionPane.setVisible(true);
        studentSelectionBtn.setDisable(true);
    }
}
