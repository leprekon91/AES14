package client.gui.fxcontrol;

import com.Contract;
import com.data.Solved_Exam;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class SolvedExamListViewCell extends JFXListCell<Solved_Exam> {
    public Label examDetailsLbl;
    public Label gradeLbl;
    public GridPane grid;
    private FXMLLoader mlLoader;


    @Override
    protected void updateItem(Solved_Exam item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mlLoader == null) {
                mlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "SolvedExamListViewCell.fxml"));
                mlLoader.setController(this);
                try {
                    mlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            examDetailsLbl.setText("#" + item.getExam().getExamIDStr() + " " + item.getExam().getExamSubject().getSubjectName() + " " + item.getExam().getExamCourse().getCourseSubject());
            gradeLbl.setText(String.valueOf(item.getExamGrade()));
            setText(null);
            setGraphic(grid);
        }

    }

}
