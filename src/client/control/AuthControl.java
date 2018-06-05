
package client.control;

import client.gui.fxcontrol.LoginScreen;
import com.Contract;
import com.data.Principal;
import com.data.Student;
import com.data.Teacher;
import com.data.User;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This Class Will Run Authentication for The User
 */
public class AuthControl {
    public Client client;
    private LoginScreen loginScreen;
    Stage stage;

    /**
     * Display Login Screen
     *
     * @param primaryStage Stage to set the login screen to
     * @param client       Client object through whitch the authentication process will be done.
     */
    void displayLogin(Stage primaryStage, Client client) {
        this.client = client;
        this.stage = primaryStage;
        //Set LoginScreen and Display it
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Contract.clientFXML + "LoginScreen.fxml"));
            Parent root = fxmlLoader.load();
            loginScreen = fxmlLoader.getController();
            loginScreen.authControl = this;
            Scene scene = new Scene(root, 480, 250);
            scene.getStylesheets().add(getClass().getResource(Contract.css).toExternalForm());
            primaryStage.setTitle("AES Client");
            //primaryStage.setMaximized(true);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendUserForAuthentication(User user) {
        client.requestAuth(user, this);
    }

    /**
     * This method will receive the user sent from server through the client.
     * If the type of the user is 0, it means that the User is not authenticated.
     * <p>
     * 1 - Student
     * 2 - Teacher
     * 3 - Principal
     *
     * @param user user object holds information for the menu.
     */
    void receiveAuthenticationAnswer(User user) {

        if (user == null) {
            loginScreen.displayErrorMessage();
            System.out.println("Authentication Failed!");
            return;
        }
        switch (user.getType()) {
            case 1:
                //Open Student Menu
                System.out.println("User is a student and he is logged in!");
                Student student = new Student(user);
                client.user = student;
                loadStudentMenu(student);
                break;
            case 2:
                //Open Teacher Menu
                System.out.println("User is a teacher and he is logged in!");
                Teacher teacher = new Teacher(user);
                client.user = teacher;
                loadTeacherMenu(teacher);
                break;
            case 3:
                //Open Principal Menu
                System.out.println("User is a principal and he is logged in!");
                Principal principal = new Principal(user);
                client.user = principal;
                loadPrincipalMenu(principal);
                break;
            default:
                loginScreen.displayErrorMessage();
                System.out.println("Authentication Failed for user: " + user.toString());
                break;
        }
    }

    /**
     * Load the UserMenu Screen for Student user type.
     *
     * @param student User Object for the screen
     */
    private void loadStudentMenu(Student student) {
        Platform.runLater(() -> {
            try {
                stage.close();
                StudentControl studentControl = new StudentControl();
                studentControl.student = student;
                studentControl.client = client;
                Stage stage = new Stage();
                stage.setTitle("AES - " + student.getFirst_name() + " " + student.getLast_name());
                studentControl.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Load the UserMenu Screen for Teacher user type.
     *
     * @param teacher User Object for the screen.
     */
    private void loadTeacherMenu(Teacher teacher) {
        Platform.runLater(() -> {
            try {
                stage.close();
                TeacherControl teacherControl = new TeacherControl();
                teacherControl.teacher = teacher;
                teacherControl.client = client;
                Stage stage = new Stage();
                stage.setTitle("AES - " + teacher.getFirst_name() + " " + teacher.getLast_name());
                teacherControl.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Load the Usermenu Screen for Principal user type.
     *
     * @param principal User Object for the screen.
     */
    private void loadPrincipalMenu(Principal principal) {
        Platform.runLater(() -> {
            try {
                stage.close();
                PrincipalControl principalControl = new PrincipalControl();
                principalControl.principal = principal;
                principalControl.client = client;
                Stage stage = new Stage();
                stage.setTitle("AES - " + principal.getFirst_name() + " " + principal.getLast_name());
                principalControl.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
