package client.gui.fxcontrol;

import client.control.PrincipalControl;
import com.Contract;
import com.data.*;
import com.jfoenix.controls.JFXListView;
import javafx.stage.Stage;
import org.controlsfx.control.MaskerPane;

import java.io.IOException;
import java.util.ArrayList;

public class PrincipalReportsView {
    public MaskerPane progressPane;

    private static PrincipalReportsView INSTANCE;
    public JFXListView studentList;
    public JFXListView teacherList;
    public JFXListView courseList;

    public static PrincipalReportsView getInstance() {
        if (INSTANCE == null) INSTANCE = new PrincipalReportsView();
        return INSTANCE;
    }

    public void initialize() {
        INSTANCE = this;
        studentList.setItems(PrincipalControl.getInstance().students);
        teacherList.setItems(PrincipalControl.getInstance().teachers);
        courseList.setItems(PrincipalControl.getInstance().courses);


        studentList.setOnMouseClicked(click -> {

            if (click.getClickCount() == 2) {
                //Use ListView's getSelected Item
                Student s = (Student) studentList.getSelectionModel()
                        .getSelectedItem();
                requestStudentReport(s);

            }
        });
        teacherList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                //Use ListView's getSelected Item
                Teacher t = (Teacher) teacherList.getSelectionModel()
                        .getSelectedItem();
                requestTeacherReport(t);

            }
        });
        courseList.setOnMouseClicked(click -> {

            if (click.getClickCount() == 2) {
                //Use ListView's getSelected Item
                Course c = (Course) studentList.getSelectionModel()
                        .getSelectedItem();
                requestCourseReport(c);

            }
        });
    }

    private void requestStudentReport(Student s) {
        try {
            progressPane.setVisible(true);
            PrincipalControl.getInstance().client.sendToServer(new Message(Contract.GET_REPORT_BY_STUDENT, s.getUsername()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestTeacherReport(Teacher t) {
        try {
            progressPane.setVisible(true);
            PrincipalControl.getInstance().client.sendToServer(new Message(Contract.GET_REPORT_BY_TEACHER, t.getUsername()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestCourseReport(Course c) {
        try {
            progressPane.setVisible(true);
            PrincipalControl.getInstance().client.sendToServer(new Message(Contract.GET_REPORT_BY_COURSE, c.getCourseNumber()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * open report using solved exams
     *
     * @param ses
     */
    public void openReport(ArrayList<Solved_Exam> ses) {
        progressPane.setVisible(false);
        ArrayList<Integer> dataList = new ArrayList<>();
        for (Solved_Exam se :
                ses) {
            dataList.add(se.getExamGrade());
        }
        int[] arr = dataList.stream().mapToInt(i -> i).toArray();
        openReport(arr);
    }


    /**
     * open report using integer array
     * @param data
     */
    public void openReport(int[] data) {
        progressPane.setVisible(false);
        try {
            Report.openReport(new Stage(), data, "Grades Summary", "Grade Category", "# of Grades");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
