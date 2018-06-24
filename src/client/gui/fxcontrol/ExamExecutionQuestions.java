package client.gui.fxcontrol;

import client.control.StudentControl;
import com.Contract;
import com.data.*;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

public class ExamExecutionQuestions {
    public Pagination pagination;
    public AnchorPane solutionView;
    public Label timeLeft;
    public JFXProgressBar progress;
    public AnchorPane titleBar;
    public Label checkIcon;
    public Label icon;

    private Exam exam;
    private Timeline timeline;
    private double duration = 0;
    private double elapsed = 0;
    private double time;
    private HashMap<Question, Integer> answers;
    public Teacher examineeTeacher;
    public static ExamExecutionQuestions INSTANCE;

    public static void openWindow(Stage primaryStage, Exam exam, int duration, Teacher examineeTeacher) {

        FXMLLoader fxmlLoader = new FXMLLoader(ExamExecutionQuestions.class.getResource(Contract.clientFXML + "ExamExecutionQuestions.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
            ExamExecutionQuestions examExecutionQuestions = fxmlLoader.getController();
            examExecutionQuestions.setDuration(duration);
            examExecutionQuestions.setExam(exam);
            examExecutionQuestions.examineeTeacher = examineeTeacher;
            INSTANCE = examExecutionQuestions;
            primaryStage.setTitle("Exam " + exam.getExamIDStr());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(ExamExecutionQuestions.class.getResource(Contract.css).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setExam(Exam exam) {
        this.exam = exam;
        pagination = new Pagination(exam.getExamQuestions().size(), 0);
        answers = new HashMap<>();
        AnchorPane.setBottomAnchor(pagination, 8.0);
        AnchorPane.setLeftAnchor(pagination, 8.0);
        AnchorPane.setRightAnchor(pagination, 8.0);
        AnchorPane.setTopAnchor(pagination, 8.0);
        solutionView.getChildren().setAll(pagination);
        pagination.setStyle("-fx-border-color:#555555;-fx-border-radius:10");
        pagination.setPageFactory(pageIndex -> createPage(pageIndex));
        timeLeft.setText((int) duration / 60 + " minutes.");
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), event -> {
                    duration--;
                    elapsed++;
                    int x = (int) duration / 60;
                    if (x == 0) {
                        timeLeft.setText((int) duration + " Seconds.");
                    } else {
                        timeLeft.setText(x + " minutes.");
                    }
                    double prog = (1.0 - (elapsed / time));
                    progress.setProgress(prog);
                    if (prog <= 0.5) {

                        progress.getStyleClass().add("warning");
                    }
                    if (prog <= 0.1) {

                        progress.getStyleClass().add("danger");
                    }
                    if (duration <= 0) {
                        timeline.stop();
                        generateAnswerArray();
                    }
                })

        );
        timeline.playFromStart();
    }

    public VBox createPage(int pageIndex) {
        Question q = exam.getExamQuestions().get(pageIndex);
        VBox box = new VBox(5);
        box.setPadding(new Insets(5, 5, 5, 5));
        box.setAlignment(Pos.TOP_CENTER);
        Label qText = new Label(q.getQuestionText());

        qText.setWrapText(true);
        JFXRadioButton ans1 = new JFXRadioButton(q.getAns(0));
        JFXRadioButton ans2 = new JFXRadioButton(q.getAns(1));
        JFXRadioButton ans3 = new JFXRadioButton(q.getAns(2));
        JFXRadioButton ans4 = new JFXRadioButton(q.getAns(3));

        ToggleGroup toggleGroup = new ToggleGroup();

        ans1.setToggleGroup(toggleGroup);
        ans2.setToggleGroup(toggleGroup);
        ans3.setToggleGroup(toggleGroup);
        ans4.setToggleGroup(toggleGroup);

        ans1.setUserData(1);
        ans2.setUserData(2);
        ans3.setUserData(3);
        ans4.setUserData(4);

        if (answers.get(q) != null) {
            switch (answers.get(q)) {
                case 1:
                    ans1.setSelected(true);
                    break;
                case 2:
                    ans2.setSelected(true);
                    break;
                case 3:
                    ans3.setSelected(true);
                    break;
                case 4:
                    ans4.setSelected(true);
                    break;
            }
        }

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (toggleGroup.getSelectedToggle() != null) {
                    answers.put(q, (Integer) toggleGroup.getSelectedToggle().getUserData());
                }
            }
        });


        box.getChildren().addAll(qText, ans1, ans2, ans3, ans4);
        return box;
    }

    public void setDuration(int duration) {
        this.duration = duration * 60;//convert to seconds
        this.time = duration * 60;
    }

    public int[] generateAnswerArray() {
        int[] ans = new int[exam.getExamQuestions().size()];
        for (int i = 0; i < exam.getExamQuestions().size(); i++) {
            Question q = exam.getExamQuestions().get(i);
            if (answers.get(q) == null) {
                ans[i] = 0;
            } else {
                ans[i] = answers.get(q);
            }
        }
        return ans;
    }

    public void submitTest(ActionEvent actionEvent) {
        timeline.stop();

        pagination.setVisible(false);

        ScaleTransition scaleTransition = new ScaleTransition();

        //Setting the duration for the transition
        scaleTransition.setDuration(Duration.millis(100));

        //Setting the node for the transition
        scaleTransition.setNode(checkIcon);
        scaleTransition.setByX(0.5);
        scaleTransition.setByY(0.5);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        //Playing the animation
        scaleTransition.play();
        scaleTransition.setOnFinished(event -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ((Stage) solutionView.getScene().getWindow()).close();
        });
        solutionView.getChildren().setAll(checkIcon);
        generateAndSendSolvedExam();
    }

    public void generateAndSendSolvedExam() {
        Student solvingStudent = StudentControl.getInstance().student;
        Solved_Exam solved_exam = new Solved_Exam(
                exam,
                generateAnswerArray(),
                solvingStudent,
                (int) (elapsed / 60),
                examineeTeacher,
                false
        );
        System.out.println("Sending to server: " + solved_exam);
        try {
            StudentControl.getInstance().client.sendToServer(new Message(Contract.EXAM_SUBMITTED, solved_exam));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lockExam() {
        timeline.stop();
        pagination.setVisible(false);
        icon.setText("❎");
        icon.setTextFill(Color.RED);
        icon.setStyle("-fx-border-color: red;");
        checkIcon.setText("Exam Is Locked!");
        checkIcon.setTextFill(Color.RED);
        ScaleTransition scaleTransition = new ScaleTransition();

        //Setting the duration for the transition
        scaleTransition.setDuration(Duration.millis(100));

        //Setting the node for the transition
        scaleTransition.setNode(checkIcon);
        scaleTransition.setByX(0.5);
        scaleTransition.setByY(0.5);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        //Playing the animation
        scaleTransition.play();
        scaleTransition.setOnFinished(event -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ((Stage) solutionView.getScene().getWindow()).close();
        });
        solutionView.getChildren().setAll(checkIcon);
        generateAndSendSolvedExam();
    }
}
