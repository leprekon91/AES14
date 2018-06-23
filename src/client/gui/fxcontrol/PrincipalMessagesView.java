package client.gui.fxcontrol;

import client.control.PrincipalControl;
import com.Contract;
import com.data.ExtensionRequest;
import com.data.Message;
import com.jfoenix.controls.JFXListCell;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.io.IOException;

public class PrincipalMessagesView {
    public ListView messagesList;
    public Label examIdLbl;
    public Label fromTeacherLbl;
    public Label extAmntLbl;
    public AnchorPane messageDetailPane;
    public WebView content;


    public void initialize() {
        messageDetailPane.setVisible(false);
        messagesList.setCellFactory(new Callback<ListView<ExtensionRequest>, JFXListCell<ExtensionRequest>>() {
            @Override
            public JFXListCell<ExtensionRequest> call(ListView<ExtensionRequest> p) {

                JFXListCell<ExtensionRequest> cell = new JFXListCell<ExtensionRequest>() {

                    @Override
                    protected void updateItem(ExtensionRequest t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText("\t\uD83D\uDCE7" + t.getTeacher().getFullName() + " | " + "Requests Time Extension For  Exam - #" + t.getExamInProgress().getExam().getExamIDStr());
                        }
                    }

                };

                return cell;
            }
        });
        messagesList.getSelectionModel().selectedItemProperty().addListener((ChangeListener<ExtensionRequest>) (observable, oldValue, newValue) -> {
            if (newValue == null) messageDetailPane.setVisible(false);
            setRequest(newValue);
            messageDetailPane.setVisible(true);
        });
        messagesList.setItems(PrincipalControl.getInstance().requestList);

    }

    private void setRequest(ExtensionRequest extensionRequest) {
        examIdLbl.setText("#" + extensionRequest.getExamInProgress().getExam().getExamIDStr());
        fromTeacherLbl.setText(" " + extensionRequest.getTeacher().getFullName());
        extAmntLbl.setText(String.valueOf(extensionRequest.getExtAmnt()));
        WebEngine we = content.getEngine();
        we.loadContent(extensionRequest.getTextContent());
    }


    public void approveRequest(ActionEvent actionEvent) {
        messageDetailPane.setVisible(true);
        ExtensionRequest extensionRequest = (ExtensionRequest) messagesList.getSelectionModel().getSelectedItems().get(0);
        extensionRequest.setApproved(true);
        try {
            PrincipalControl.getInstance().client.sendToServer(new Message(Contract.PRINCIPAL_REQUEST_ANSWER, extensionRequest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void declineRequest(ActionEvent actionEvent) {
        messageDetailPane.setVisible(true);
        ExtensionRequest extensionRequest = (ExtensionRequest) messagesList.getSelectionModel().getSelectedItems().get(0);
        extensionRequest.setApproved(false);
        try {
            PrincipalControl.getInstance().client.sendToServer(new Message(Contract.PRINCIPAL_REQUEST_ANSWER, extensionRequest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
