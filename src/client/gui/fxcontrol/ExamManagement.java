package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.Contract;
import com.data.Exam;
import com.data.Message;
import com.jfoenix.controls.JFXListView;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class ExamManagement {

    public JFXListView examList;
    public TextField nameIdFilter;
    public TextField subjectFilter;
    public TextField teacherFilter;

    public void initialize() {
        examList.setCellFactory(listView -> new ExamListViewCell());
        FilteredList<Exam> filteredData = new FilteredList<>(
                TeacherControl.getInstance().exams,
                s -> true
        );

        nameIdFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(exam -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCase = newValue.toLowerCase();
                        if (exam.getExamCourse().getCourseName().toLowerCase().contains(lowerCase)) {
                            return true;
                        }
                return String.valueOf(exam.getExamCourse().getCourseNumber()).contains(lowerCase);
                    }

            );
        });
        subjectFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(exam -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCase = newValue.toLowerCase();
                        if (exam.getExamSubject().getSubjectName().toLowerCase().contains(lowerCase)) {
                            return true;
                        }
                return String.valueOf(exam.getExamSubject().getSubjectID()).contains(lowerCase);
                    }

            );
        });
        teacherFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(exam -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCase = newValue.toLowerCase();
                        if (exam.getExamAuthorTeacher().getFullName().toLowerCase().contains(lowerCase)) {
                            return true;
                        }
                return exam.getExamAuthorTeacher().getUsername().toLowerCase().contains(lowerCase);
                    }

            );
        });

        examList.setItems(filteredData);

        //set double click listener:
        examList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Exam exam = (Exam) examList.getSelectionModel().getSelectedItems().get(0);
                try {
                    ExamView.openWindow(new Stage(), exam);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void createNewExam(ActionEvent actionEvent) {

        try {
            Exam exam = new Exam(new ArrayList<>(), "", "", null, null, TeacherControl.getInstance().teacher, false, 0);
            CreateExamDialog.openWindow(new Stage(), exam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewSelectedExam(ActionEvent actionEvent) {
        Exam exam = (Exam) examList.getSelectionModel().getSelectedItems().get(0);
        try {
            ExamView.openWindow(new Stage(), exam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteSelectedExam(ActionEvent actionEvent) {
        if (examList.getSelectionModel().getSelectedItems().size() != 0) {
            Exam exam = (Exam) examList.getSelectionModel().getSelectedItems().get(0);
            if (exam.getExamAuthorTeacher().getUsername().equals(TeacherControl.getInstance().teacher.getUsername())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Wait!");
                alert.setHeaderText("Are You Sure?");
                alert.setContentText("You can't restore the questions you delete! " +
                        "\nAre you sure you wan't to delete this Exam (#" + exam.getExamIDStr() + ")?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    //send exam to deletion
                    try {
                        TeacherControl.getInstance().client.sendToServer(new Message(Contract.DELETE_EXAM, exam));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Can't Edit Exam!");
                alert.setContentText("You can't edit Exams you didn't create!");
                alert.showAndWait();
            }

        }
    }

}
