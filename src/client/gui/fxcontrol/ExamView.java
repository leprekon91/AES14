package client.gui.fxcontrol;

import com.Contract;
import com.data.Exam;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ExamView {
    public Text examViewTitle;

    public static void openWindow(Stage stage, Exam exam) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingleQuestion.class.getResource(Contract.clientFXML + "ExamView.fxml"));
        Parent root = fxmlLoader.load();
        ExamView dialogController = fxmlLoader.getController();
        dialogController.setExam(exam);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(QuestionView.class.getResource(Contract.css).toExternalForm());
        stage.setTitle("Exam #" + exam.getExamIDStr());
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void setExam(Exam exam) {
        examViewTitle.setText("Exam #" + exam.getExamIDStr());
    }
}
