package client.gui.fxcontrol;

import com.Contract;
import com.data.ExamInProgress;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ExtensionRequestWindow {
    public HTMLEditor messageEditor;
    private ExamInProgress eip;
    private final String INITIAL_TEXT = "<html><body><h1 style=\"color: #5e9ca0;\">Extension Request</h1>\n" +
            "<p style=\"color: #5e9ca0;\"><span style=\"color: #000000;\">Hello! I Am An Example Extension Request!</span></p></body></html>";


    public static void openWindow(ExamInProgress eip) {
        try {
            Stage primaryStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Report.class.getResource(Contract.clientFXML + "ExtensionRequestWindow.fxml"));
            Parent root = null;
            root = fxmlLoader.load();
            ExtensionRequestWindow control = fxmlLoader.getController();
            control.setEip(eip);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(ExtensionRequestWindow.class.getResource(Contract.css).toExternalForm());
            primaryStage.setTitle("Request Extension For Exam #" + eip.getExam().getExamIDStr());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        messageEditor.setHtmlText(INITIAL_TEXT);
    }

    public void setEip(ExamInProgress eip) {
        this.eip = eip;
    }

    public ExamInProgress getEip() {
        return eip;
    }

    public void sendRequest(ActionEvent actionEvent) {
        Dialog<Object> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Look, a Custom Login Dialog");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        webEngine.loadContent(messageEditor.getHtmlText());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Preview:"), 0, 0);
        grid.add(browser, 0, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        //send to server
                    }
                    return null;
                }
        );

        Optional<Object> result = dialog.showAndWait();
    }
}
