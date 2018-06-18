package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.Contract;
import com.data.Exam;
import com.data.Message;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ExamManagement {

    public ListView examList;
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
            Exam exam = new Exam(new ArrayList<>(), "", "", null, null, TeacherControl.getInstance().teacher, false, 0, false);
            CreateExamDialog.openWindow(new Stage(), exam);
            TeacherControl.getInstance().client.sendToServer(new Message(Contract.CREATE_EXAM, exam));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewSelectedExam(ActionEvent actionEvent) {
        //TODO Stub
    }

    public void deleteSelectedExam(ActionEvent actionEvent) {
        //TODO Stub
    }

    public void editSelectedExam(ActionEvent actionEvent) {
        //TODO Stub
    }
}
