package client.gui.fxcontrol;

import client.control.StudentControl;
import com.Contract;
import com.WordDocument;
import com.data.ExamInProgress;
import com.data.Message;
import com.data.Solved_Exam;
import com.jfoenix.controls.JFXListView;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            if (result.get().equals(password) && EIP.hasBegun()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Teacher' Note");
                alert.setHeaderText("A Note from " + EIP.getExaminingTeacher().getFullName());
                alert.setContentText(EIP.getExam().getStudentNotes());
                alert.showAndWait();

                try {
                    StudentControl.getInstance().client.sendToServer(new Message(Contract.STUDENT_STARTS_EXAM, EIP));
                    beginExam(EIP);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                receiveBeginExamDecline();
            }
        }
    }

    /**
     * @param eip
     */
    public void beginExam(ExamInProgress eip) {
        if (eip.isWordType()) {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word Files(.docx)", "*.docx");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(examView.getScene().getWindow());

            if (file != null) {
                saveFile(eip, file);
            }
            //show upload file dialog
            WordExamUpload.openWindow(new Stage(), eip);
        } else {
            ExamExecutionQuestions.openWindow(new Stage(), eip.getExam(), eip.getExam().getAssignedTime(), eip.getExaminingTeacher());
        }
    }

    /**
     *
     */
    public void receiveBeginExamDecline() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText("Can't Begin Exam!");
        alert.showAndWait();
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
        FilteredList<Solved_Exam> filteredGrades = new FilteredList<>(
                StudentControl.getInstance().grades,
                s -> s.isApproved()
        );
        gradesList.setItems(filteredGrades);
        gradesList.setCellFactory(list -> new SolvedExamListViewCell());

    }

    /**
     * @param eip
     * @param file
     */
    private void saveFile(ExamInProgress eip, File file) {
        try {
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
            String date = localDate.format(formatter);
            WordDocument.createExamDoc(eip.getExam(), eip.getExaminingTeacher(), file.toPath().toString(), date);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     *
     * @param actionEvent
     */
    public void switchToExamsView(ActionEvent actionEvent) {
        welcomeView.setVisible(false);
        examView.setVisible(true);
        gradesView.setVisible(false);
    }

    /**
     *
     * @param actionEvent
     */
    public void switchToGradesView(ActionEvent actionEvent) {
        welcomeView.setVisible(false);
        examView.setVisible(false);
        gradesView.setVisible(true);
    }

    /**
     *
     */
    public void hideAll() {
        welcomeView.setVisible(true);
        examView.setVisible(false);
        gradesView.setVisible(false);
    }
}
