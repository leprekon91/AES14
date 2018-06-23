package client.gui.fxcontrol;

import client.control.StudentControl;
import com.Contract;
import com.data.ExamInProgress;
import com.data.Word_Solved_Exam;
import com.jfoenix.controls.JFXProgressBar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class WordExamUpload {
    public BorderPane dragboardPane;
    public Label timeLeft;
    public JFXProgressBar progress;
    public Label iconLbl;
    public Label icon;
    private File uploadFile;
    private ExamInProgress eip;
    private Timeline timeline;
    private double duration = 0;
    private double elapsed = 0;
    private double time;

    public static void openWindow(Stage stage, ExamInProgress eip) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SingleQuestion.class.getResource(Contract.clientFXML + "WordExamUpload.fxml"));
            Parent root = fxmlLoader.load();
            WordExamUpload dialogController = fxmlLoader.getController();
            dialogController.setEip(eip);
            dialogController.setDuration(eip.getExam().getAssignedTime());
            Scene scene = new Scene(root, 600, 500);
            scene.getStylesheets().add(QuestionView.class.getResource(Contract.css).toExternalForm());
            stage.setTitle("Upload Solution");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
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
                        //stop from uploading
                        stopUploading();
                    }
                })

        );
        timeline.playFromStart();
    }

    private void stopUploading() {
        Color c = Color.web("0xC32F29");
        iconLbl.setTextFill(c);
        icon.setTextFill(c);
        icon.setText("\uD83D\uDDBF");
        iconLbl.setText("Upload Is Locked!\n Time ran out or the exam was locked remotely");


    }

    public void openFile(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Exam File");
        uploadFile = fileChooser.showOpenDialog(dragboardPane.getScene().getWindow());
    }

    public void fileDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            uploadFile = new File(db.getString());
            success = true;
        }
        /*
         * let the source know whether the string was successfully
         * transferred and used
         */
        event.setDropCompleted(success);

        event.consume();

    }

    public void generateSolution(File upload) {
        Word_Solved_Exam word_solved_exam = new Word_Solved_Exam(
                eip.getExam(),
                StudentControl.getInstance().student,
                (int) (elapsed / 60),
                eip.getExaminingTeacher(),
                0,
                false,
                "",
                true,
                false, upload);
    }

    public ExamInProgress getEip() {
        return eip;
    }

    public void setEip(ExamInProgress eip) {
        this.eip = eip;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
