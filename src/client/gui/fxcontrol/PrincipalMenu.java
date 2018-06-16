package client.gui.fxcontrol;

import client.control.PrincipalControl;
import com.Contract;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PrincipalMenu {
    public AnchorPane viewPane;
    public AnchorPane requestPane;
    public AnchorPane reportPane;
    public Label requestNumIcon;

    public void initialize() {
        requestNumIcon.textProperty().bind(Bindings.convert(new SimpleIntegerProperty(PrincipalControl.getInstance().requestList.size())));
        loadReportsView();
        loadRequestsView();

    }

    public void switchToRequests(ActionEvent actionEvent) {
        viewPane.getChildren().setAll(requestPane);
    }

    public void switchToReports(ActionEvent actionEvent) {
        viewPane.getChildren().setAll(reportPane);
    }

    public void loadRequestsView() {
        requestPane = new AnchorPane();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "PrincipalMessagesView.fxml"));
        AnchorPane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestPane.getChildren().setAll(root);
        setAnchors(root);
        setAnchors(requestPane);

    }

    public void loadReportsView() {
        reportPane = new AnchorPane();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "PrincipalReportsView.fxml"));
        AnchorPane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reportPane = (root);
        setAnchors(root);
        setAnchors(reportPane);

    }


    private void setAnchors(Node root) {
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);
    }

}
