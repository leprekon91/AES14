package server.sql;

import com.data.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionTable {


    /**
     * Create a question entry in the database
     *
     * @param question question to be created
     */
    public static void createQuestion(Question question) {
        System.out.println("QuestionTable - Create Question\n" +
                "Question: " + question);
        //TEST: Question Creation:
        int indicator = question.getCorrectAnswer();
        int subjectId = question.getSubjectId();
        String teacherID = question.getTeacherId();
        String questionText = question.getQuestionText();
        String[] data = question.getPossibleAnswers();
        PreparedStatement stmt;
        Connection con = MysqlManager.ConnectToDB();
        //------------------------------------
        try {
            stmt = con.prepareStatement(SQLContract.CREATE_QUESTION);

            stmt.setString(1, questionText);
            stmt.setString(2, data[0]);
            stmt.setString(3, data[1]);
            stmt.setString(4, data[2]);
            stmt.setString(5, data[3]);
            stmt.setInt(6, indicator);
            stmt.setString(7, teacherID);
            stmt.setInt(8, subjectId);

            stmt.execute();
            stmt.close();
            MysqlManager.closeConnection(con);
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
    }

    /**
     * Read Question entry from the database, create a question object and return it.
     *
     * @param question empty question object to be filled with data from the DB.
     * @return Question Object filled with the entry data.
     */
    public static Question readQuestion(Question question) {
        //TODO test
        System.out.println("QuestionTable - Read Question\n" +
                "Question ID: " + question.getQID());
        PreparedStatement stmt;
        Connection con = MysqlManager.ConnectToDB();

        int qid = question.getQID();
        int subject = question.getSubjectId();
        try {
            stmt = con.prepareStatement(SQLContract.READ_QUESTION);
            stmt.setInt(1, qid);
            stmt.setInt(2, subject);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                question.setQuestionText(rs.getString("question_text"));
                String ans1 = rs.getString("ans_1");
                String ans2 = rs.getString("ans_2");
                String ans3 = rs.getString("ans_3");
                String ans4 = rs.getString("ans_4");
                question.setPossibleAnswers(new String[]{ans1, ans2, ans3, ans4});
                question.setCorrectAnswer(rs.getInt("indicator"));
                question.setTeacherId(rs.getString("users_user_name"));
            }
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
        return question;
    }

    /**
     * Update a question entry with a new question object data
     *
     * @param question question object corresponding to the entry that is going to be updated
     */
    public static void updateQuestion(Question question) {
        System.out.println("QuestionTable - Update Question\n" +
                "Question: " + question);

        int indicator = question.getCorrectAnswer();
        int subjectId = question.getSubjectId();
        String teacherID = question.getTeacherId();
        String questionText = question.getQuestionText();
        String[] data = question.getPossibleAnswers();
        PreparedStatement stmt;
        Connection con = MysqlManager.ConnectToDB();
        //------------------------------------
        try {
            stmt = con.prepareStatement(SQLContract.UPDATE_QUESTION);

            stmt.setString(1, questionText);
            stmt.setString(2, data[0]);
            stmt.setString(3, data[1]);
            stmt.setString(4, data[2]);
            stmt.setString(5, data[3]);
            stmt.setInt(6, indicator);
            stmt.setString(7, teacherID);
            stmt.setInt(8, question.getQID());
            stmt.setInt(9, question.getSubjectId());

            stmt.execute();
            stmt.close();
            MysqlManager.closeConnection(con);
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
    }

    /**
     * Delete Question from the database.
     *
     * @param question question object corresponding to the entry that is going to be deleted
     */
    public static void deleteQuestion(Question question) {
        System.out.println("QuestionTable - Delete Question\n" +
                "Question: " + question);
        //------------------------------------
        PreparedStatement stmt;
        Connection con = MysqlManager.ConnectToDB();
        //------------------------------------
        try {
            stmt = con.prepareStatement(SQLContract.DELETE_QUESTION);
            stmt.setInt(1, question.getQID());
            stmt.setInt(2, question.getSubjectId());
            stmt.execute();
            stmt.close();
            MysqlManager.closeConnection(con);
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
    }

    public static ArrayList<Question> selectAllQuestionsBySubject(int subjectID) {
        //TODO STUB method
        System.out.println("QuestionTable - Select Questions By Subject\n" +
                "Subject: " + subjectID);
        ArrayList<Question> questions = new ArrayList<>();

        return questions;
    }

    public static ArrayList<Question> selectAllQuestionsByExam(int examID) {
        //TODO STUB method
        System.out.println("QuestionTable - Select Questions By Exam\n" +
                "Exam: " + examID);
        ArrayList<Question> questions = new ArrayList<>();

        return questions;
    }

    public static ArrayList<Question> selectAllQuestionsByTeacher(String teacherID) {
        //TODO STUB method
        System.out.println("QuestionTable - Select Questions By teacher\n" +
                "Teacher: " + teacherID);
        ArrayList<Question> questions = new ArrayList<>();

        return questions;
    }
}
