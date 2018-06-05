package client.gui.fxcontrol;

import client.control.TeacherControl;
import com.data.Question;
import javafx.event.ActionEvent;

public class TeacherMenu {
    public TeacherControl teacherControl;
    public void sendCreateQuestionMessage(ActionEvent actionEvent) {
        try {
            teacherControl.createQuestion(new Question(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
