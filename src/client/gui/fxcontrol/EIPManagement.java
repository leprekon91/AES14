package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.Contract;
import com.data.ExamInProgress;
import com.data.Message;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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
        ExtensionRequestWindow.openWindow((ExamInProgress) eipList.getSelectionModel().getSelectedItems().get(0));
    }

    public void lockExam(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Lock");
        alert.setContentText("Are you sure you want to lock this exam?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ExamInProgress eip = (ExamInProgress) eipList.getSelectionModel().getSelectedItems().get(0);
            try {
                TeacherControl.getInstance().client.sendToServer(new Message(Contract.LOCK_EXAM, eip));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
