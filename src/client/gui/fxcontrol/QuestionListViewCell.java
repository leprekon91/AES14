package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.Contract;
import com.data.Question;
import com.style.icons.FontAwesome;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class QuestionListViewCell extends ListCell<Question> {
    public Label qIcon;
    public Label subjectIcon;
    public Label qidLbl;
    public Label qTextLbl;
    public Label authorLbl;
    public Label subjectLbl;
    public GridPane grid;
    private FXMLLoader mlLoader;

    @Override
    protected void updateItem(Question item, boolean empty) {
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
                String thisUser = TeacherControl.getInstance().teacher.getUsername();
                if (item.getAuthor().getUsername().equals(thisUser)) this.qIcon.setTextFill(Color.GREEN);
                else this.qIcon.setTextFill(Color.RED);
                qidLbl.setText(item.getQIDString());
                qTextLbl.setText(item.getQuestionText());
                authorLbl.setText(item.getAuthor().getFullName());
                subjectIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
                subjectLbl.setText("(" + item.getSubject().getSubjectID() + ") " + item.getSubject().getSubjectName());
                setText(null);
                setGraphic(grid);
            }
        }
    }
}
