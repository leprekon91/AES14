package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.data.Question;
import com.data.Subject;
import com.data.Teacher;
import com.data.User;
import com.style.icons.FontAwesome;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherMenu {


    public TeacherControl teacherControl = TeacherControl.getInstance();
    public AnchorPane questionPane;
    public ListView questionList;
    public Label plusIcon;
    public Label editIcon;
    public Label delIcon;


    public void initialize() {
        plusIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
        editIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
        delIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));

        questionList.setItems(TeacherControl.getInstance().questions);

    }


    public void openSingleQuestion(Question question) {
        try {
            SingleQuestion.openDialog(new Stage(), question);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openNewQuestionDialog(ActionEvent actionEvent) {
        Question question = new Question(
                "",
                new String[]{
                        "",
                        "",
                        "",
                        ""
                },
                4,
                null,
                teacherControl.teacher
        );
        openSingleQuestion(question);
        //add question to list
        //send question to the database
    }


    //--------------------------------------------------------
    public void openQDialogTest(ActionEvent actionEvent) {
        Question question = new Question(
                "This is Question Text",
                new String[]{
                        "Answer 1",
                        "Answer 2",
                        "Answer 3",
                        "Answer 4"
                },
                4,
                new Subject(
                        1,
                        "Math"
                ),
                new Teacher(
                        new User(
                                "userName",
                                "fName",
                                "lName",
                                2)
                )
        );
        question.setQID(5);
        openSingleQuestion(question);
    }
//--------------------------------------------------------
}
