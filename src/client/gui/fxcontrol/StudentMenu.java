package client.gui.fxcontrol;

import client.control.StudentControl;
import com.Contract;
import com.data.ExamInProgress;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Optional;

public class StudentMenu {
    public AnchorPane viewPane;

    public BorderPane welcomeView;

    public AnchorPane examView;
    public JFXListView eipList;

    public AnchorPane gradesView;
    public JFXListView gradesList;


    public void doExamTest(ExamInProgress EIP) {


        //show password dialog
        String password = EIP.getPassword();
        TextInputDialog dialog = new TextInputDialog();
        Label icon = new Label("\u2753");
        icon.setMinWidth(50);
        icon.setMinHeight(50);
        icon.setAlignment(Pos.CENTER);
        icon.setStyle("-fx-font-weight: bold;" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-font-size: 20pt ;" +
                "-fx-border-color: #55ACEE;" +
                "-fx-border-radius:100;" +
                "-fx-background-color: #3B5998;" +
                "-fx-background-radius: 100;");
        dialog.setGraphic(icon);
        dialog.getDialogPane().getStylesheets().add(ExamExecutionQuestions.class.getResource(Contract.css).toExternalForm());
        dialog.getEditor().setPromptText("Password");
        dialog.setTitle("Password is required");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter Exam #" + EIP.getExam().getExamIDStr() + " password:");


        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().equals(password)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Teacher' Note");
                alert.setHeaderText("A Note from " + EIP.getExaminingTeacher().getFullName());
                alert.setContentText(EIP.getExam().getStudentNotes());
                alert.showAndWait();

                ExamExecutionQuestions.openWindow(new Stage(), EIP.getExam(), EIP.getExam().getAssignedTime(), EIP.getExaminingTeacher());
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Wrong Password!");
                alert.setContentText(null);
                alert.showAndWait();
            }
        }
    }

    public void initialize() {
        hideAll();
        eipList.setItems(StudentControl.getInstance().eips);
        eipList.setCellFactory(list -> new EipListCell());
        eipList.setOnMouseClicked(click -> {

            if (click.getClickCount() == 2) {
                ExamInProgress eip = (ExamInProgress) eipList.getSelectionModel()
                        .getSelectedItem();
                doExamTest(eip);

            }
        });
    }

    public void switchToExamsView(ActionEvent actionEvent) {
        welcomeView.setVisible(false);
        examView.setVisible(true);
        gradesView.setVisible(false);
    }

    public void switchToGradesView(ActionEvent actionEvent) {
        welcomeView.setVisible(false);
        examView.setVisible(false);
        gradesView.setVisible(true);
    }

    public void hideAll() {
        welcomeView.setVisible(true);
        examView.setVisible(false);
        gradesView.setVisible(false);
    }
}
