package client.gui.fxcontrol;

import com.Contract;
import com.data.Question;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.controlsfx.tools.Borders;

import java.io.IOException;

public class QuestionView {
    public Label subjectLbl;
    public Label questionText;
    public Label qidLbl;

    public static void openWindow(Stage stage, Question q) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingleQuestion.class.getResource(Contract.clientFXML + "QuestionView.fxml"));
        Parent root = fxmlLoader.load();
        QuestionView dialogController = fxmlLoader.getController();
        dialogController.setQuestion(q);
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(QuestionView.class.getResource(Contract.css).toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void initialize() {
        Node wrappedQuestionText = Borders.wrap(questionText).lineBorder().buildAll();

    }

    private void setQuestion(Question q) {
        qidLbl.setText(q.getQIDString());
        subjectLbl.setText(q.getSubject().getSubjectName());
        questionText.setText(q.getQuestionText());

    }
}
