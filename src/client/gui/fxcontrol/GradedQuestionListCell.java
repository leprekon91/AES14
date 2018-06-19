package client.gui.fxcontrol;

import com.Contract;
import com.data.Question;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class GradedQuestionListCell extends JFXListCell<Question> {
    public Label qidLbl;
    public Label qTextLabel;
    public TextField grade;
    public GridPane grid;
    private FXMLLoader mlLoader;

    @Override
    protected void updateItem(Question item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mlLoader == null) {
                mlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "QuestionListViewCell.fxml"));
                mlLoader.setController(this);
                try {
                    mlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            qidLbl.setText(item.getQIDString());
            qTextLabel.setText(item.getQuestionText());
            grade.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d{0,3}?")) {
                    grade.setText(oldValue);
                }
            });

            setText(null);
            setGraphic(grid);

        }
    }
}
