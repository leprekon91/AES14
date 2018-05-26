package server.control;

import com.Contract;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import server.gui.fxcontrol.Dashboard;
import server.sql.MysqlManager;
import server.sql.SQLContract;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

public class Start extends Application implements ServerUI {
    public Server sv;
    public Dashboard dashboard;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {



        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.serverFXML + "dashboard.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 1200, 600);
            dashboard = fxmlLoader.getController();

            scene.getStylesheets().add(getClass().getResource(Contract.css).toExternalForm());
            primaryStage.setTitle("Administrator Dashboard");
            primaryStage.setMaximized(true);
            //primaryStage.setResizable(false);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sv = new Server(Contract.DEFAULT_PORT,this);

        //login();

        try {
            sv.listen(); //Start listening for connections
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Could not listen for clients! System will now Shut Down.");
            alert.setContentText(ex.getLocalizedMessage());
            alert.showAndWait();
            System.exit(1);
        }
        primaryStage.show();
    }

    private void login() {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login To DataBase");
        dialog.setHeaderText("Please Enter Data Base credentials");

        // Set the icon (must be included in the project).
        ImageView iv = new ImageView(this.getClass().getResource(Contract.graphics + "Icon.png").toString());
        iv.setFitWidth(64);
        iv.setFitHeight(64);
        dialog.setGraphic(iv);

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            if (dialogButton == ButtonType.CANCEL) {
                System.exit(0);
            }
            return null;
        });
        while (true) {
            Optional<Pair<String, String>> result = dialog.showAndWait();

            //try to connect with database:
            result.ifPresent(usernamePassword -> {
                SQLContract.user = usernamePassword.getKey();
                SQLContract.pass = usernamePassword.getValue();

            });

            Connection con = MysqlManager.ConnectToDB();
            if (MysqlManager.ConnectToDB() != null) {
                MysqlManager.closeConnection(con);
                break;
            }
        }
    }

    @Override
    public void logMsg(String str) {
        dashboard.addLogMsg(str);
    }
}
