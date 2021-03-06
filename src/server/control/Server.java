package server.control;

import com.Contract;
import com.data.*;
import server.ocsf.AbstractServer;
import server.ocsf.ConnectionToClient;
import server.sql.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Server implementation. This class will handle all communication with
 * the clients, using the language defined in the com.Contract class file.
 */
public class Server extends AbstractServer {

    private ServerUI SUI;


    public Server(int port, ServerUI sui, SQLInjection sqli) {
        super(port);
        if (sui != null) {
            this.SUI = sui;
        } else {
            this.SUI = new SUIConsole();
        }
        AuthorizeUser.getInstance().sqli = sqli;
    }

    /**
     * This method overrides the one in the superclass.  Called
     * when the server starts listening for connections.
     */
    protected void serverStarted() {
        SUI.logMsg
                ("Server listening for connections on port " + getPort());
    }

    /**
     * This method overrides the one in the superclass.  Called
     * when the server stops listening for connections.
     */
    protected void serverStopped() {
        SUI.logMsg("Server has stopped listening for connections.");
    }

    /**
     * This method overrides the one in the superclass.  Called
     * when a client has connected to the server
     */
    @Override
    protected void clientConnected(ConnectionToClient client) {
        SUI.logMsg("Client " + client.toString() + " connected.");
    }

    /**
     * This method overrides the one in the superclass.  Called
     * * when a connectionToClient thread has encountered an exception.
     *
     * @param client the client that raised the exception.
     * @param e      exception that was thrown
     */
    @Override
    protected synchronized void clientException(ConnectionToClient client, Throwable e) {
        super.clientException(client, e);
        if (!(e instanceof EOFException)) {
            SUI.logMsg("Client: " + client.toString() + " has encountered an exception!\n" +
                    "Consider the system log for details.");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            SUI.logMsg(exceptionAsString);
            (AuthorizeUser.getInstance()).deleteUserByClient(client);
        }
    }

    /**
     * This method overrides the one in the superclass.  Called
     * when a client has disconnected from the server
     */
    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        super.clientDisconnected(client);
        SUI.logMsg("Client " + client.toString() + " disconnected.");
    }

    /**
     * message from client handler
     *
     * @param msg    the message sent.
     * @param client the connection connected to the client that sent the message
     */
    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {

        if (msg instanceof Message) {
            //Log message on the sever:
            SUI.logMsg("Message received from Client: " + client.toString()
                    + " Message:\n" +
                    "\ttype:" + ((Message) msg).getType()
            );


            int contractType = ((Message) msg).getType();

            ArrayList<Question> updatedQuestions;
            ArrayList<Exam> allExams;
            ArrayList<Student> students;
            ArrayList<ExamInProgress> eips;
            ArrayList<Teacher> teachers;
            ArrayList<Solved_Exam> solvedExams;

            //------------------------Message decode--------------------------------------------------------------------
            try {
                switch (contractType) {
                    case Contract.AUTHORIZE: //Client Requests Authorization

                        User u = (User) ((Message) msg).getData();
                        Message authResponse = (AuthorizeUser.getInstance()).authorize(u.getUsername(), u.getPass(), client);
                        client.sendToClient(authResponse);

                        break;

                    case Contract.LOG_OFF:  //client requests logoff - delete client from list

                        if ((AuthorizeUser.getInstance()).usernameExistsInLoggedInUsers(((User) ((Message) msg).getData()).getUsername())) {
                            (AuthorizeUser.getInstance()).deleteUserByUsername(((User) ((Message) msg).getData()).getUsername());
                        }

                        break;
                    case Contract.EXAMS:
                        allExams = new ArrayList<>();
                        ExamTable.selectAllExam(allExams);
                        client.sendToClient(new Message(Contract.EXAMS, allExams));
                        break;
                    case Contract.CREATE_EXAM:              //Create a New Exam
                        ExamTable.createExam((Exam) ((Message) msg).getData());
                        allExams = new ArrayList<>();
                        ExamTable.selectAllExam(allExams);
                        client.sendToClient(new Message(Contract.EXAMS, allExams));
                        break;
                    case Contract.READ_EXAM:                //Read Exam Object by ID
                    case Contract.DELETE_EXAM:              //Delete an existing Exam
                        ExamTable.deleteExam((Exam) ((Message) msg).getData());
                        allExams = new ArrayList<>();
                        ExamTable.selectAllExam(allExams);
                        client.sendToClient(new Message(Contract.EXAMS, allExams));
                        break;
                    case Contract.GET_EXAMS_BY_COURSE:      //Get Exams in a specific course
                    case Contract.GET_EXAMS_BY_SUBJECT:     //Get Exams in a specific Subject
                    case Contract.GET_EXAMS_BY_TEACHER:     //Get Exams written by a specific teacher
                        break;
                    case Contract.COURSES_BY_TEACHER:
                        ArrayList<Course> courses = new ArrayList<>();
                        SubjectsTable.getAllTeachersCourses(courses, (String) ((Message) msg).getData());
                        client.sendToClient(new Message(Contract.COURSES_BY_TEACHER, courses));
                        break;
                    case Contract.QUESTIONS:
                        SUI.logMsg("Server Has received a 'Get All Questions' message.");
                        QuestionTable.selectAllQuestions((ArrayList<Question>) ((Message) msg).getData());
                        client.sendToClient(msg);
                        break;
                    case Contract.CREATE_QUESTION:          //Create a new Question
                        SUI.logMsg("Server Has received a 'Create question' message."
                                + "\nQuestion: " + ((Message) msg).getData());
                        QuestionTable.createQuestion((Question) ((Message) msg).getData());
                        updatedQuestions = new ArrayList<>();
                        QuestionTable.selectAllQuestions(updatedQuestions);
                        client.sendToClient(new Message(Contract.QUESTIONS, updatedQuestions));
                        break;
                    case Contract.READ_QUESTION:            //Read an existing question By ID
                        SUI.logMsg("Server Has received a 'Read Question' message."
                                + "\nQuestion ID: " + ((Message) msg).getData());
                        Question question = QuestionTable.readQuestion((Question) ((Message) msg).getData());
                        client.sendToClient(new Message(Contract.QUESTION, question));
                        break;
                    case Contract.UPDATE_QUESTION:          //Update an existing Question
                        SUI.logMsg("Server Has received a 'Update Question' message."
                                + "\nQuestion: " + ((Message) msg).getData());
                        QuestionTable.updateQuestion((Question) ((Message) msg).getData());
                        updatedQuestions = new ArrayList<>();
                        QuestionTable.selectAllQuestions(updatedQuestions);
                        client.sendToClient(new Message(Contract.QUESTIONS, updatedQuestions));
                        break;
                    case Contract.DELETE_QUESTION:          //Delete Question from A database
                        SUI.logMsg("Server Has received a 'Delete Question' message."
                                + "\nQuestion: " + ((Message) msg).getData());
                        QuestionTable.deleteQuestion((Question) ((Message) msg).getData());
                        updatedQuestions = new ArrayList<>();
                        QuestionTable.selectAllQuestions(updatedQuestions);
                        client.sendToClient(new Message(Contract.QUESTIONS, updatedQuestions));
                        break;
                    case Contract.GET_QUESTIONS_BY_EXAM:    //Get all questions in a specific Exam
                        SUI.logMsg("Server Has received a 'Get Questions by Subject ID' message."
                                + "\nQuestion: " + ((Message) msg).getData());
                        ArrayList<Question> Questions = QuestionTable.selectAllQuestionsByExam((Integer) ((Message) msg).getData());
                        client.sendToClient(new Message(Contract.QUESTIONS, Questions));
                        break;
                    case Contract.GET_QUESTIONS_BY_SUBJECT: //Get Questions in a specific Subject
                        SUI.logMsg("Server Has received a 'Get Questions by Exam ID' message."
                                + "\nQuestion: " + ((Message) msg).getData());
                        Questions = QuestionTable.selectAllQuestionsBySubject((Integer) ((Message) msg).getData());
                        client.sendToClient(new Message(Contract.QUESTIONS, Questions));
                        break;
                    case Contract.GET_QUESTIONS_BY_TEACHER: //Get Questions written by a specific teacher
                        SUI.logMsg("Server Has received a 'Get Questions by Teacher ID' message."
                                + "\nQuestion: " + ((Message) msg).getData());
                        Questions = QuestionTable.selectAllQuestionsByTeacher((String) ((Message) msg).getData());
                        client.sendToClient(new Message(Contract.QUESTIONS, Questions));
                        break;
                    case Contract.GET_SUBJECT:              //Get Subject details
                    case Contract.GET_SUBJECTS_BY_TEACHER:
                        SUI.logMsg("Server Has received a 'Get Subjects by Teacher ID' message."
                                + "\nQuestion: " + ((Message) msg).getData());
                        Teacher t = (Teacher) ((Message) msg).getData();
                        ArrayList<Subject> subjects = SubjectsTable.getAllSubjectsByTeacher(t.getUsername());
                        client.sendToClient(new Message(Contract.SUBJECTS, subjects));
                        break;
                    case Contract.GET_GRADES_BY_EXAM:       //Get Grades of an exam
                    case Contract.GET_GRADES_BY_STUDENT:    //Get grades of a specific student
                        solvedExams = ExamTable.selectAllSolvedExamsBy_Student((String) ((Message) msg).getData());
                        client.sendToClient(new Message(Contract.GET_GRADES_BY_STUDENT, solvedExams));
                        break;
                    case Contract.GET_PRINCIPAL_REQUESTS:
                        ((ArrayList<ExtensionRequest>) (((Message) msg).getData())).addAll(RequestManager.getInstance().requests);
                        client.sendToClient(new Message(Contract.PRINCIPAL_REQUESTS, ((Message) msg).getData()));
                        break;
                    case Contract.PRINCIPAL_REQUEST_ANSWER:
                        if (((ExtensionRequest) ((Message) msg).getData()).isApproved()) {
                            //extend the exam
                            ExamInProgressManager.getInstance().extendExamInProgress(
                                    ((ExtensionRequest) ((Message) msg).getData()).getExamInProgress(),
                                    ((ExtensionRequest) ((Message) msg).getData()).getExtAmnt()
                            );
                        }
                        RequestManager.getInstance().removeRequest((ExtensionRequest) ((Message) msg).getData());
                        break;
                    case Contract.ADD_REQUEST:
                        RequestManager.getInstance().requests.add((ExtensionRequest) ((Message) msg).getData());
                        break;
                    case Contract.STUDENTS:
                        students = new ArrayList<>();
                        AuthorizeUser.getAllStudents(students);
                        client.sendToClient(new Message(Contract.STUDENTS, students));
                        break;
                    case Contract.START_EXAM:
                        ExamInProgressManager.getInstance().addExamInProgress((ExamInProgress) ((Message) msg).getData());
                        eips = ExamInProgressManager.getInstance().getExamInProgressArrayByTeacher(((ExamInProgress) ((Message) msg).getData()).getExaminingTeacher().getUsername());
                        client.sendToClient(new Message(Contract.GET_EXAMS_IN_PROGRESS_BY_TEACHER, eips));
                        break;
                    case Contract.STUDENT_STARTS_EXAM:
                        ExamInProgressManager.getInstance().studentStartsAnExam(client, (ExamInProgress) ((Message) msg).getData());
                        client.sendToClient(new Message(Contract.BEGIN_EXAM, ((Message) msg).getData()));
                        break;
                    case Contract.GET_EXAMS_IN_PROGRESS:
                        //if it's a teacher return by teacher else, return by student
                        if (((User) ((Message) msg).getData()).getType() == 2) {
                            eips = ExamInProgressManager.getInstance().getExamInProgressArrayByTeacher(((Teacher) ((Message) msg).getData()).getUsername());
                            client.sendToClient(new Message(Contract.GET_EXAMS_IN_PROGRESS_BY_TEACHER, eips));
                        } else if (((User) ((Message) msg).getData()).getType() == 1) {
                            eips = ExamInProgressManager.getInstance().getExamInProgressArrayByStudent(((Student) ((Message) msg).getData()).getUsername());
                            client.sendToClient(new Message(Contract.GET_EXAMS_IN_PROGRESS_BY_STUDENT, eips));
                        }
                        break;
                    case Contract.STUDENTS_BY_COURSE:
                        System.out.println(getClass().getName());
                        students = new ArrayList<>();
                        SubjectsTable.getAllStudentInCourse(students, (Course) ((Message) msg).getData());
                        client.sendToClient(new Message(Contract.STUDENTS_BY_COURSE, students));
                        break;
                    case Contract.LOCK_EXAM:
                        ExamInProgressManager.getInstance().lockExamInProgress((ExamInProgress) ((Message) msg).getData());
                        break;
                    case Contract.EXTEND_EXAM:
                        ExtensionRequest er = (ExtensionRequest) ((Message) msg).getData();
                        ExamInProgressManager.getInstance().extendExamInProgress(er.getExamInProgress(), er.getExtAmnt());
                        break;
                    case Contract.TEACHERS:
                        teachers = new ArrayList<>();
                        AuthorizeUser.getAllTeachers(teachers);
                        client.sendToClient(new Message(Contract.TEACHERS, teachers));
                        break;
                    case Contract.COURSES:
                        courses = new ArrayList<>();
                        SubjectsTable.getAllCourses(courses);
                        client.sendToClient(new Message(Contract.COURSES, courses));
                        break;
                    case Contract.GET_REPORT_BY_STUDENT:
                        solvedExams = ExamTable.selectAllSolvedExamsBy_Student((String) ((Message) msg).getData());
                        client.sendToClient(new Message(Contract.REPORT, solvedExams));
                        break;
                    case Contract.GET_REPORT_BY_TEACHER:
                        solvedExams = ExamTable.selectAllSolvedExamsBy_ExaminerTeacher((String) ((Message) msg).getData());
                        client.sendToClient(new Message(Contract.REPORT, solvedExams));
                        break;
                    case Contract.GET_REPORT_BY_COURSE:
                        solvedExams = ExamTable.selectAllSolvedExamsBy_CourseNumber((int) ((Message) msg).getData());
                        client.sendToClient(new Message(Contract.REPORT, solvedExams));
                        break;
                    case Contract.EXAM_SUBMITTED:
                        ExamTable.createSolvedExam((Solved_Exam) ((Message) msg).getData());
                        break;

                }

            } catch (IOException e) {
                SUI.logMsg(e.getMessage());
            }
            //----------------------------------------------------------------------------------------------------------
        } else {
            SUI.logMsg("Client" + client.toString() + "\tsent an unidentifiable message.");
        }

    }

}
