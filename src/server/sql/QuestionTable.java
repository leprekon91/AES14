package server.sql;

import com.data.Question;
import com.data.Subject;
import com.data.Teacher;
import com.data.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuestionTable {


    /**
     * Create a question entry in the database
     *
     * @param question question to be created
     */
    public static void createQuestion(Question question) {
        //TODO test
        System.out.println("QuestionTable - Create Question\n" +
                "Question: " + question);

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
        //TODO STUB method

    }


    /**
     * Delete Question from the database.
     *
     * @param question question object corresponding to the entry that is going to be deleted
     */
    public static void deleteQuestion(Question question) {
        System.out.println("QuestionTable - Delete Question\n" +
                "Question: " + question);
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
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }

    }
}
