package client.gui.fxcontrol;

import com.Contract;
import com.data.Question;
import com.style.icons.FontAwesome;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class QuestionListViewCell extends ListCell<Question> {
    public Label textLbl;
    public Label qIDLbl;
    public Label ico;
    public Label authorLbl;
    public HBox box;
    FXMLLoader mLLoader;

    @Override
    protected void updateItem(Question item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "QuestionListViewCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ico.setText(FontAwesome.ICON_QUESTION_SIGN);
            ico.setFont(FontAwesome.getFont(FontAwesome.SOLID));
            qIDLbl.setText(String.valueOf(item.getQID()));
            textLbl.setText(item.getQuestionText());
            authorLbl.setText(item.getTeacherId());
            setText(null);
            setGraphic(box);
        }
    }
}
