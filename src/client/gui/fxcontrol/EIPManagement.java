package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.data.ExamInProgress;
import javafx.event.ActionEvent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class EIPManagement {
    public ListView eipList;


    public void initialize() {




        eipList.setCellFactory(
                new Callback<ListView, ListCell>() {
                    @Override
                    public ListCell call(ListView param) {
                        ListCell<ExamInProgress> cell = new ListCell<ExamInProgress>() {
                            @Override
                            public void updateItem(ExamInProgress item, boolean empty) {
                                super.updateItem(item, empty);
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                                if (item != null) {
                                    String str = "\u270d|";
                                    str += item.getExam().getExamIDStr() + "|\t\t > ";
                                    str += "Start: " + item.getDateTimeStart().format(formatter) + " - ";
                                    str += "End: " + item.getDateTimeEnd().format(formatter);
                                    setText(str);
                                }
                            }
                        };
                        return cell;

                    }
                }
        );
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
