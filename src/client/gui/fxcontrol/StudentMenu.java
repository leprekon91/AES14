package client.gui.fxcontrol;

import client.control.StudentControl;
import com.Contract;
import com.data.*;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

public class StudentMenu {
    public AnchorPane viewPane;

    public BorderPane welcomeView;

    public AnchorPane examView;
    public JFXListView eipList;

    public AnchorPane gradesView;
    public JFXListView gradesList;



    public void doExamTest(ActionEvent actionEvent) {
        Subject subject = new Subject(1, "Math");
        Teacher teacher = new Teacher(new User("t", "John", "Smith", 2));
        Course course = new Course(1, "Algebra", subject);
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("Mrs. Rodger got a weekly raise of $145. If she gets paid every other week, write an integer describing how the raise will affect her paycheck.",
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


        Exam exam = new Exam(questions, "", "", course, subject, teacher, false, 0);

        //show password dialog
        String password = "123";
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
        dialog.setContentText("Please enter Exam #" + exam.getExamIDStr() + " password:");


        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().equals(password))
                ExamExecutionQuestions.openWindow(new Stage(), exam, 1, new Teacher(new User("t1", "John", "Teacher", 2)));
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
