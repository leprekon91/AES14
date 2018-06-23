package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.data.ExamInProgress;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class EIPManagement {
    public ListView eipList;


    public void initialize() {


        eipList.setCellFactory(list -> new EipListCell());
        eipList.setItems(TeacherControl.getInstance().eips);
    }

    public void startNewExam(ActionEvent actionEvent) {
        ExamInProgress examInProgress = new ExamInProgress(null, null, null, null, TeacherControl.getInstance().teacher, null, false);
        try {
            CreateNewEIPDialog.openWindow(new Stage(), examInProgress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openExtensionRequest(ActionEvent actionEvent) {
        //TODO open extensionRequestDialog
    }

    public void lockExam(ActionEvent actionEvent) {
        //TODO lockExam
    }
}
