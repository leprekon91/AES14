package client.gui.fxcontrol;

import com.Contract;
import com.data.Exam;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ExamListViewCell extends ListCell<Exam> {
    public Label eidLbl;
    public Label subjectLbl;
    public Label teacherLbl;
    public Label courseLbl;
    public Label statusLbl;
    public GridPane grid;
    private FXMLLoader mlLoader;

    @Override
    protected void updateItem(Exam item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {

            if (mlLoader == null) {
                mlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "ExamListViewCell.fxml"));
                mlLoader.setController(this);
                try {
                    mlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            eidLbl.setText(item.getExamIDStr());
            subjectLbl.setText(item.getExamSubject().getSubjectName());
            teacherLbl.setText(item.getExamAuthorTeacher().getFullName());
            courseLbl.setText(item.getExamCourse().getCourseName());
            if (item.isUsed()) {
                statusLbl.setText("Was Used");
            } else {
                statusLbl.setText("Never Used");
            }
            setText(null);
            setGraphic(grid);
        }


    }
}
