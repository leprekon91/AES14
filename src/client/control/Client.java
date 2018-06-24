package client.control;

import client.gui.fxcontrol.CreateNewEIPDialog;
import client.gui.fxcontrol.ExamExecutionQuestions;
import client.gui.fxcontrol.PrincipalReportsView;
import client.ocsf.AbstractClient;
import com.Contract;
import com.data.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018
 * <p>
 * Client implementation. This class will handle all communication with
 * the server, using the language defined in the com.Contract class file.
 */
public class Client extends AbstractClient {


    public User user;
    public TeacherControl teacherControl;
    private AuthControl authControl;//reference for authentication controller

    /**
     * Client constructor, opens connection to server once he is created.
     *
     * @param host
     * @param port
     */
    public Client(String host, int port) {
        super(host, port);

        try {
            openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void handleMessageFromServer(Object msg) {

        if (!(msg instanceof Message)) {
            System.out.println("Server sent an unidentifiable message!");
        }
        Message message = (Message) msg;
        switch (message.getType()) {
            //Authorization Messages:
            case Contract.AUTH_YES:
            case Contract.AUTH_NO:
                receiveAuth(message);
                break;
            case Contract.QUESTION: //Question Object was received.
                break;
            case Contract.QUESTIONS:
                TeacherControl.getInstance().receiveAllQuestions((ArrayList<Question>) ((Message) msg).getData());
                break;
            case Contract.EXAM:
                break;
            case Contract.EXAMS:
                TeacherControl.getInstance().receiveAllExams((ArrayList<Exam>) ((Message) msg).getData());
                break;
            case Contract.SUBJECTS:
                teacherControl.receiveSubjectListByTeacher((ArrayList<Subject>) ((Message) msg).getData());
                break;
            case Contract.PRINCIPAL_REQUESTS:
                PrincipalControl.getInstance().receiveNewRequests((ArrayList<ExtensionRequest>) ((Message) msg).getData());
                break;
            case Contract.STUDENTS:
                PrincipalControl.getInstance().students.setAll((ArrayList<Student>) ((Message) msg).getData());
                break;
            case Contract.TEACHERS:
                PrincipalControl.getInstance().teachers.setAll((ArrayList<Teacher>) ((Message) msg).getData());
                break;
            case Contract.COURSES:
                PrincipalControl.getInstance().courses.setAll((ArrayList<Course>) ((Message) msg).getData());
                break;
            case Contract.COURSES_BY_TEACHER:
                TeacherControl.getInstance().receiveAllCourses((ArrayList<Course>) ((Message) msg).getData());
                break;
            case Contract.STUDENTS_BY_COURSE:
                CreateNewEIPDialog.getInstance().receiveStudents((ArrayList<Student>) ((Message) msg).getData());
                break;
            case Contract.GET_EXAMS_IN_PROGRESS_BY_TEACHER:
                TeacherControl.getInstance().eips.setAll((ArrayList<ExamInProgress>) ((Message) msg).getData());
                break;
            case Contract.GET_EXAMS_IN_PROGRESS_BY_STUDENT:
                StudentControl.getInstance().eips.setAll((ArrayList<ExamInProgress>) ((Message) msg).getData());
                break;
            case Contract.EXAM_LOCK:
                ExamExecutionQuestions.INSTANCE.lockExam();
                break;
            case Contract.BEGIN_EXAM:
                break;
            case Contract.REPORT:
                PrincipalReportsView.getInstance().openReport((ArrayList<Solved_Exam>) ((Message) msg).getData());
                break;


        }

    }

    @Override
    protected void connectionException(Exception exception) {
        super.connectionException(exception);
        exception.printStackTrace();
    }

    @Override
    protected void connectionClosed() {
        super.connectionClosed();
        System.out.println("Closed Connection!");
    }

    /**
     * Request Authentication from server for a specific user.
     * Also, save the reference to the class that asked for it.
     *
     * @param user
     * @param authControl
     */
    public void requestAuth(User user, AuthControl authControl) {
        this.authControl = authControl;
        try {
            System.out.println("Sending request for login");
            this.sendToServer(new Message(Contract.AUTHORIZE, user));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recieve and apply Auth. reply Message from Server
     * through authControl.
     *
     * @param msg Message recieved from server.
     */
    public void receiveAuth(Message msg) {
        System.out.println("Authentication Reply Received");
        if (msg.getType() == Contract.AUTH_YES)
            authControl.receiveAuthenticationAnswer((User) msg.getData());
        else {
            authControl.receiveAuthenticationAnswer(null);
        }
    }

}
