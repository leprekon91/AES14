package client.gui.fxcontrol;

import client.control.TeacherControl;
import javafx.scene.control.ListView;

public class ExamManagement {

    public ListView examList;

    public void initialize() {
        examList.setCellFactory(listView -> new ExamListViewCell());
        examList.setItems(TeacherControl.getInstance().exams);

    }
}
