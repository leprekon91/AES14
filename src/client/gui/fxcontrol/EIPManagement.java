package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.data.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EIPManagement {
    public ListView eipList;
    //TEST:
    public ArrayList<ExamInProgress> eips = new ArrayList<>();

    public void initialize() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(new User("s1", "john", "student", 1)));
        Subject subject = new Subject(1, "Mathematics");
        Teacher teacher = new Teacher(new User("t", "John", "Smith", 2));
        Course course = new Course(2, "Algebra", subject);
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("1. Mrs. Rodger got a weekly raise of $145. If she gets paid every other week, write an integer describing how the raise will affect her paycheck.",
                new String[]{"85", "257", "145", "14"}, 3, subject, teacher));
        questions.add(new Question("2. The value of x + x(x^x) when x = 2 is:",
                new String[]{"16", "10", "18", "64"},
                2, subject, teacher));
        questions.add(new Question(" Mr. Jones sold two pipes at $1.20 each. Based on the cost, his profit one was 20% and his loss on the other was 20%. On the sale of the pipes, he:",
                new String[]{" gained 10 cents", " lost 4 cents.", " gained 4 cents", " broke even."}, 4, subject, teacher));
        questions.add(new Question("The distance light travels in one year is approximately 5,870,000,000,000 miles. The distance light travels in 100 years is:",
                new String[]{" 587 × 10^12 miles", " 587 × 10^8 miles", " 587 × 10^(-12) miles ", " 587 × 10-10 miles"}, 1, subject, teacher));
        questions.add(new Question(" A man has $ 10,000 to invest. He invests $ 4000 at 5 % and $ 3500 at 4 %. In order to have a yearly income of $ 500, he must invest the remainder at:",
                new String[]{" 6 %", " 6.4 %", "6.2 %,", "6.1 %"}, 2, subject, teacher));


        Exam exam = new Exam(questions, "", "", course, subject, teacher, false, 0, false);

        eips.add(
                new ExamInProgress(
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(50),
                        students,
                        "123",
                        TeacherControl.getInstance().teacher,
                        exam
                )
        );

        eipList.setCellFactory(
                new Callback<ListView, ListCell>() {
                    @Override
                    public ListCell call(ListView param) {
                        ListCell<ExamInProgress> cell = new ListCell<ExamInProgress>() {
                            @Override
                            public void updateItem(ExamInProgress item, boolean empty) {
                                super.updateItem(item, empty);
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                                if (item != null) {
                                    String str = "\u270d|";
                                    str += item.getExam().getExamIDStr() + "|\t\t > ";
                                    str += "Start: " + item.getDateTimeStart().format(formatter) + " - ";
                                    str += "End: " + item.getDateTimeEnd().format(formatter);
                                    setText(str);
                                }
                            }
                        };
                        return cell;

                    }
                }
        );
        eipList.setItems(FXCollections.observableArrayList(eips));
    }

    public void startNewExam(ActionEvent actionEvent) {
        ExamInProgress examInProgress = new ExamInProgress(null, null, null, null, TeacherControl.getInstance().teacher, null);
        try {
            CreateNewEIPDialog.openWindow(new Stage(), examInProgress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openExtensionRequest(ActionEvent actionEvent) {
        //TODO open extensionRequestDialog
    }

    public void lockExam(ActionEvent actionEvent) {
        //TODO lockExam
    }
}
