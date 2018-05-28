package client.control;

import com.Contract;
import com.data.User;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * this class will authorize a user.
 */
public class UserAuth {
    private User user;
    private int Authorized=3;
    private Client client;

    private Stage progressStage;

    /**
     *
     * @param user user to be authorized (or not)
     * @param client connection to a server
     */
    public UserAuth(User user, Client client) {
        this.user = user;
        this.client = client;
        Authentication service =new Authentication();
        service.setOnScheduled(e -> progressStage.show());
        service.setOnSucceeded(e -> progressStage.hide());
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        progressStage = new Stage();
        progressStage.setScene(new Scene(new StackPane(progressIndicator), 200, 200));
        progressStage.setTitle("Logging in...");
        progressStage.setAlwaysOnTop(true);
        service.restart();

    }
    private class Authentication extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            Task<Void> task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {

                    client.requestAuth(Authorized,user);
                    while(user.getAuth()==Contract.AUTHORIZE)
                        Thread.sleep(100);
                    if(user.getAuth()==Contract.AUTH_YES)
                        System.out.println("User Is Authorized");
                    else{
                        System.out.println("User Not Authorized!");
                    }
                    return null;
                }
            };
            return task;
        }
    }

}
