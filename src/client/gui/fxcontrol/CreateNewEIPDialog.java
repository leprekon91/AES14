package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.Contract;
import com.data.*;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

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
    private static CreateNewEIPDialog INSTANCE;
    public MaskerPane mask;
    private ExamInProgress examInProgress;
    public CheckListView studentCheckList;
    private ObservableList<Student> students = FXCollections.observableArrayList();
    private Exam exam;

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

    public static CreateNewEIPDialog getInstance() {
        return INSTANCE;
    }

    public void initialize() {
        INSTANCE = this;
        examList.setItems(TeacherControl.getInstance().exams);
        examList.setCellFactory(exams -> new ExamListViewCell());
        ValidationSupport support = new ValidationSupport();
        support.registerValidator(examList, Validator.createEmptyValidator("Must Pick A Test!"));
        support.registerValidator(startDatePicker, Validator.createEmptyValidator("Must Enter A Start Date!"));
        support.registerValidator(startTimePicker, Validator.createEmptyValidator("Must Enter A Start Time!"));
        support.registerValidator(endDatePicker, Validator.createEmptyValidator("Must Enter A End Date!"));
        support.registerValidator(endTimePicker, Validator.createEmptyValidator("Must Enter A End Time!"));
        support.registerValidator(pswdField, Validator.createEmptyValidator("Must Enter A End Time!"));
        studentSelectionBtn.disableProperty().bind(support.invalidProperty());
        pswdField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 4) {
                pswdField.setText(oldValue);
            } else {
                pswdField.setText(newValue);
            }
        });
    }

    private void setExamInProgress(ExamInProgress eip) {
        this.examInProgress = eip;
    }

    public void switchToStudentSelection(ActionEvent actionEvent) {
        if (examList.getSelectionModel().getSelectedItems().size() > 0) {
            this.exam = (Exam) examList.getSelectionModel().getSelectedItems().get(0);
            detailsPane.setVisible(false);
            studentSelectionPane.setVisible(true);
            mask.setVisible(true);
            try {
                TeacherControl.getInstance().client.sendToServer(
                        new Message(
                                Contract.STUDENTS_BY_COURSE,
                                this.exam.getExamCourse()
                        ));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setTitle("Exam Must Be Chosen!");
            alert.setTitle("Exam Must Be Chosen for the next step!");
            alert.showAndWait();
        }

    }

    public void receiveStudents(ArrayList<Student> studentsList) {
        students.setAll(studentsList);
        mask.setVisible(false);
        studentCheckList.setItems(students);

    }

    public void generateEIPObject(ActionEvent actionEvent) {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        LocalTime startTime = startTimePicker.getValue();
        LocalTime endTime = endTimePicker.getValue();
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        String password = pswdField.getText();
        Teacher examiningTeacher = TeacherControl.getInstance().teacher;
        Exam examObj = this.exam;
        ArrayList<Student> students = (ArrayList<Student>) studentCheckList.getCheckModel().getCheckedItems();
        ExamInProgress examInProgress = new ExamInProgress(start, end, students, password, examiningTeacher, examObj);
        try {
            TeacherControl.getInstance().client.sendToServer(new Message(Contract.START_EXAM, examInProgress));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((Stage) startDatePicker.getScene().getWindow()).close();

    }
}
