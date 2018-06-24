package server.sql;

import com.data.Question;
import com.data.Subject;
import com.data.Teacher;
import com.data.User;

import java.sql.*;
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
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(SQLContract.CREATE_QUESTION);
            stmt.setString(1, question.getQuestionText());
            stmt.setString(2, question.getAns(0));
            stmt.setString(3, question.getAns(1));
            stmt.setString(4, question.getAns(2));
            stmt.setString(5, question.getAns(3));
            stmt.setInt(6, question.getCorrectAnswer());
            stmt.setString(7, question.getAuthor().getUsername());
            stmt.setInt(8, question.getSubject().getSubjectID());
            stmt.execute();

            stmt.close();
            con.close();
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

        System.out.println("QuestionTable - Read Question\n" +
                "Question ID: " + question.getQID());
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

        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(SQLContract.UPDATE_QUESTION);
            stmt.setString(1, question.getQuestionText());
            stmt.setString(2, question.getAns(0));
            stmt.setString(3, question.getAns(1));
            stmt.setString(4, question.getAns(2));
            stmt.setString(5, question.getAns(3));
            stmt.setInt(6, question.getCorrectAnswer());
            stmt.setString(7, question.getAuthor().getUsername());
            stmt.setInt(8, question.getQID());
            stmt.setInt(9, question.getSubject().getSubjectID());
            stmt.execute();

            stmt.close();
            con.close();
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
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(SQLContract.DELETE_QUESTION);
            stmt.setInt(1, question.getQID());
            stmt.setInt(2, question.getSubject().getSubjectID());

            stmt.execute();

            stmt.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
    }

    public static ArrayList<Question> selectAllQuestionsBySubject(int subjectID) {

        System.out.println("QuestionTable - Select Questions By Subject\n" +
                "Subject: " + subjectID);
        ArrayList<Question> questions = new ArrayList<>();

        return questions;
    }

    public static ArrayList<Question> selectAllQuestionsByExam(int examID) {

        ArrayList<Question> questions = new ArrayList<>();
        System.out.println("QuestionTable - Select Questions By Exam\n" +
                "Exam: " + examID);
        return questions;
    }

    public static ArrayList<Question> selectAllQuestionsByTeacher(String teacherID) {

        System.out.println("QuestionTable - Select Questions By teacher\n" +
                "Teacher: " + teacherID);
        ArrayList<Question> questions = new ArrayList<>();

        return questions;
    }

    public static void selectAllQuestions(ArrayList<Question> data) {
        System.out.println("QuestionTable - All Questions\n");
        //-------------------------------------------------------------------------------------------------------------
        Connection con = MysqlManager.ConnectToDB();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQLContract.ALL_QUESTION);

            while (rs.next()) {
                Question question = new Question(
                        rs.getString("question_text"),
                        new String[]{
                                rs.getString("ans_1"),
                                rs.getString("ans_2"),
                                rs.getString("ans_3"),
                                rs.getString("ans_4")
                        },
                        rs.getInt("indicator"),
                        new Subject(
                                rs.getInt("id_subject"),
                                rs.getString("subject_name")
                        ),
                        new Teacher(
                                new User(
                                        rs.getString("user_name"),
                                        rs.getString("first_name"),
                                        rs.getString("last_name"),
                                        2)
                        )
                );
                question.setQID(rs.getInt("id_question"));
                data.add(question);
            }


            stmt.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }

    }
}
