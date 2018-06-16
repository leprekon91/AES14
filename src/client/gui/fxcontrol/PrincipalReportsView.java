package client.gui.fxcontrol;

import client.control.PrincipalControl;
import com.Contract;
import com.data.Message;
import com.data.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.MaskerPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class PrincipalReportsView {
    public MaskerPane progressPane;

    private static PrincipalReportsView INSTANCE;

    public static PrincipalReportsView getInstance() {
        if (INSTANCE == null) INSTANCE = new PrincipalReportsView();
        return INSTANCE;
    }

    public void initialize() {
        INSTANCE = this;
    }

    public void showStudentReportDialog(ActionEvent actionEvent) {
        //start task of getting all students
        progressPane.setVisible(true);
        try {
            PrincipalControl.getInstance().client.sendToServer(new Message(Contract.STUDENTS, new ArrayList<Student>()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * display dialog to select students, get the students from the server and then display a report.
     *
     * @param students
     */
    public void receiveAllStudentsForReport(ArrayList<Student> students) {
        progressPane.setVisible(false);
        ObservableList<Student> studentList = FXCollections.observableArrayList(); //list contains students
        studentList.setAll(students);
        FilteredList<Student> filteredData = new FilteredList<Student>(studentList, s -> true);
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Student Selection");
        dialog.setHeaderText("Please Select A Student");
        dialog.setGraphic(null);
        ButtonType buttonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.setMaxHeight(Double.MAX_VALUE);
        grid.setMaxWidth(Double.MAX_VALUE);

        //create filter text field
        TextField filter = new TextField();
        filter.setMaxHeight(Double.MAX_VALUE);
        filter.setMaxWidth(Double.MAX_VALUE);
        filter.setPromptText("Search...");
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(student -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCase = newValue.toLowerCase();
                        return student.getFullName().toLowerCase().contains(lowerCase);
                    }

            );
        });

        //create student list view
        ListView list = new ListView();
        list.setMaxHeight(Double.MAX_VALUE);
        list.setMaxWidth(Double.MAX_VALUE);
        list.setItems(filteredData);
        list.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            @Override
            public ListCell<Student> call(ListView<Student> p) {
                ListCell<Student> cell = new ListCell<Student>() {
                    @Override
                    protected void updateItem(Student t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getFullName());
                        } else setText(null);
                    }
                };
                return cell;
            }
        });

        //add content to dialog:
        grid.add(filter, 0, 0);
        grid.add(list, 0, 1);

        //add grid to dialog
        dialog.getDialogPane().setContent(grid);

        //add css to the dialog
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource(Contract.css).toExternalForm());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonType) {
                return (Student) list.getSelectionModel().getSelectedItems().get(0);
            }
            return null;
        });
        //show dialog
        Optional<Student> result = dialog.showAndWait();

        //send request of report by student
        result.ifPresent(student -> {
            try {
                PrincipalControl.getInstance().client.sendToServer(new Message(Contract.GET_REPORT_BY_STUDENT, null));
                progressPane.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void openReport(int[] data) {
        progressPane.setVisible(false);
        try {
            Report.openReport(new Stage(), data, "Grades Summary", "Grade Category", "# of Grades");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
