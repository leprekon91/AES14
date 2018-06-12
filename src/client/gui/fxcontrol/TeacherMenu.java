package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.Contract;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class TeacherMenu {


    public TeacherControl teacherControl = TeacherControl.getInstance();
    public AnchorPane questionPane;


    public void initialize() {
        //load question pane
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "QuestionListView.fxml"));
            Parent root = fxmlLoader.load();
            questionPane.getChildren().setAll(root);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
