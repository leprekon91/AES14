package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.data.Exam;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

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
                        if (String.valueOf(exam.getExamCourse().getCourseNumber()).contains(lowerCase)) {
                            return true;
                        }
                        return false;
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
                        if (String.valueOf(exam.getExamSubject().getSubjectID()).contains(lowerCase)) {
                            return true;
                        }
                        return false;
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
                        if (exam.getExamAuthorTeacher().getUsername().toLowerCase().contains(lowerCase)) {
                            return true;
                        }
                        return false;
                    }

            );
        });

        examList.setItems(filteredData);

        //set double click listener:
        examList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    Exam exam = (Exam) examList.getSelectionModel().getSelectedItems().get(0);
                    try {
                        ExamView.openWindow(new Stage(), exam);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


}
