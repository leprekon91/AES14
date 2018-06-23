package client.gui.fxcontrol;


import com.Contract;
import com.data.ExamInProgress;
import com.jfoenix.controls.JFXListCell;
import com.style.icons.FontAwesome;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class EipListCell extends JFXListCell<ExamInProgress> {
    public Label icon;
    public Label eidLbl;
    public Label teacherLbl;
    public Label startIcon;
    public Label startLabel;
    public Label endIcon;
    public Label endLabel;
    public GridPane grid;
    private FXMLLoader mlLoader;

    @Override
    protected void updateItem(ExamInProgress item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {

            if (mlLoader == null) {
                mlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "EipListCell.fxml"));
                mlLoader.setController(this);
                try {
                    mlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            icon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
            startIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
            endIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));

            icon.setText(FontAwesome.ICON_FILE);
            icon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
            eidLbl.setText("#" + item.getExam().getExamIDStr());
            teacherLbl.setText(item.getExaminingTeacher().getFullName());
            startIcon.setText("\uf251");
            startLabel.setText(item.getDateTimeStart().format(formatter));
            endIcon.setText("\uf253");
            endLabel.setText(item.getDateTimeEnd().format(formatter));
            setText(null);
            setGraphic(grid);
        }


    }
}
