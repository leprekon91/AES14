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
import javafx.stage.Stage;

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


    public void openExampleReport(ActionEvent actionEvent) {
        int[] grades = new int[]{
                79, 71, 47, 83, 39, 42, 44, 49, 7, 64, 74, 83, 80, 79, 55, 55, 13, 52, 10, 21, 88, 45, 70, 31, 64, 46, 43, 19, 8, 37, 42, 44, 63, 97, 55, 59, 37, 6, 25, 4, 77, 47, 68, 35, 24, 49, 92, 81, 73, 45, 60, 7, 42, 33, 71, 83, 1, 56, 41, 44, 99, 66, 71, 52, 60, 60, 11, 44, 27, 13, 61, 36, 54, 67, 34, 87, 51, 26, 4, 70, 57, 46, 14, 39, 74, 88, 24, 72, 50, 14, 86, 97, 43, 83, 37, 97, 64, 28, 72, 46, 73, 5, 30, 97, 15, 29, 29, 44, 25, 14, 31, 90, 45, 26, 80, 44, 78, 38, 66, 57, 58, 53, 45, 27, 82, 78, 39, 94, 58, 24, 89, 57, 56, 61, 74, 15, 62, 17, 77, 97, 72, 19, 12, 60, 22, 17, 88, 85, 45, 90, 96, 62, 70, 36, 18, 47, 8, 13, 14, 56, 5, 30, 40, 58, 35, 35, 32, 7, 62, 7, 80, 92, 68, 24, 67, 74, 8, 38, 57, 20, 87, 9, 93, 63, 8, 28, 97, 37, 1, 2, 18, 67, 99, 84, 42, 9, 30, 82, 18, 8, 26, 95, 76, 28, 48, 83, 59, 45, 100, 23, 12, 91, 73, 49, 90, 97, 34, 72, 57, 51, 23, 25, 5, 87, 6, 3, 62, 77, 68, 26, 27, 81, 59, 19, 47, 69, 10, 73, 48, 73, 59, 51, 74, 55, 81, 29, 83, 21, 41, 62, 32, 69, 56, 47, 33, 58, 23, 82, 80, 9, 11, 72, 41, 54, 27, 86, 95, 9, 7, 55, 91, 40, 40, 44, 51, 92, 38, 53, 90, 57, 2, 97, 82, 37, 39, 69, 32, 64, 18, 10, 69, 7, 28, 71, 10, 25, 68, 82, 52, 90, 78, 77, 98, 65, 64, 68, 52, 39, 69, 4, 56, 82, 5, 70, 99, 4, 96, 42, 18, 57, 99, 43, 77, 85, 50, 65, 28, 39, 11, 26, 14, 49, 52, 24, 30, 27, 26, 70, 17, 24, 45, 39, 67, 49, 16, 57, 95, 45, 2, 96, 35, 84, 19, 62, 15, 37, 98, 28, 90, 35, 31, 89, 26, 20, 17, 67, 73, 7, 11, 97, 27, 23, 37, 95, 68, 41, 37, 9, 31, 65, 59, 41, 84, 52, 39, 26, 97, 31, 33, 31, 85, 54, 91, 29, 70, 45, 92, 74, 80, 16, 99, 86, 44, 15, 13, 62, 90, 45, 61, 50, 2, 84, 63, 73, 15, 8, 87, 95, 89, 54, 61, 31, 45, 91, 91, 98, 22, 60, 59, 76, 25, 79, 9, 53, 32, 54, 44, 17, 80, 26, 14, 79, 39, 100, 59, 4, 100, 67, 54, 78, 96, 39, 38, 100, 45, 5, 38, 31, 58, 33, 92, 89, 86, 79, 52, 45, 88, 81, 39, 18, 35, 4, 30, 50, 35, 80, 87, 53, 87, 61, 53, 73, 61, 32, 21, 99, 75, 4, 26, 53, 87, 41, 13, 19, 13, 94, 100, 21, 65, 31, 87, 63, 68, 22, 49, 59, 98, 19, 51, 30, 84, 50, 29, 32, 32, 19, 35, 7, 60, 38, 39, 42, 33, 64, 7, 94, 5, 31, 38, 73, 58, 37, 79, 34, 7, 70, 13, 71, 2, 5, 34, 47, 31, 54, 31, 81, 86, 58, 56, 45, 62, 10, 7, 52, 65, 21, 60, 22, 19, 96, 82, 10, 63, 44, 37, 24, 34, 21, 36, 65, 40, 3, 25, 20, 52, 31, 88, 97, 35, 93, 6, 45, 61, 81, 58, 3, 35, 75, 77, 16, 70, 40, 48, 63, 100, 81, 100, 99, 84, 55, 11, 64, 18, 75, 27, 81, 23, 12, 93, 97, 39, 56, 31, 87, 2, 89, 79, 59, 15, 42, 80, 55, 50, 72, 65, 8, 84, 70, 67, 76, 53, 98, 30, 25, 74, 25, 100, 72, 5, 47, 21, 34, 28, 44, 56, 65, 9, 69, 89, 26, 59, 33, 82, 94, 4, 59, 83, 65, 81, 47, 89, 43, 77, 82, 58, 82, 74, 42, 37, 97, 82, 78, 100, 19, 13, 79, 100, 47, 72, 63, 94, 100, 5, 64, 59, 84, 11, 89, 88, 74, 41, 49, 24, 64, 44, 90, 94, 86, 7, 69, 34, 61, 56, 21, 94, 55, 38, 6, 46, 72, 48, 61, 94, 15, 92, 95, 71, 15, 76, 37, 40, 56, 80, 71, 99, 7, 32, 91, 62, 27, 83, 81, 44, 41, 66, 21, 98, 46, 2, 40, 89, 33, 41, 67, 26, 59, 74, 25, 20, 28, 66, 71, 80, 67, 80, 46, 100, 44, 2, 14, 69, 85, 19, 36, 42, 47, 11, 88, 76, 78, 59, 86, 32, 23, 21, 86, 73, 57, 63, 77, 48, 62, 21, 90, 71, 17, 19, 92, 97, 43, 46, 57, 8, 57, 34, 84, 48, 92, 70, 95, 65, 8, 4, 36, 70, 71, 29, 93, 64, 11, 70, 35, 80, 83, 12, 64, 88, 71, 36, 4, 28, 64, 75, 35, 33, 62, 44, 49, 96, 55, 50, 2, 44, 12, 23, 77, 13, 75, 76, 90, 43, 89, 10, 90, 77, 13, 69, 2, 9, 30, 81, 6, 23, 52, 45, 41, 67, 75, 38, 92, 83, 93, 50, 95, 68, 76, 74, 86, 80, 61, 16, 28, 69, 94, 30, 36, 6, 30, 1, 89, 64, 70, 15, 48, 12, 9, 1, 45, 5, 41, 21, 3, 57, 63, 63, 40, 66, 46, 57, 57, 45, 34, 41, 52, 33, 34, 59, 30, 24, 93, 17, 76, 46, 6, 26, 12, 90, 36, 66, 98, 50, 22, 3, 100, 78, 55, 32, 23, 80, 1, 80, 60, 54, 76, 32, 100, 73, 54, 75, 18, 64, 29, 95, 75, 26, 74, 31, 58, 14, 69, 58
        };
        try {
            Report.openReport(new Stage(), grades, "Title", "x values", "y values");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setAnchors(Node root) {
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);
    }

}
