package server.sql;

import com.data.Question;
import server.ocsf.ConnectionToClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionTable {
    public ConnectionToClient connectionToClient;//used to define whom to send the answer to...

    public QuestionTable(ConnectionToClient client) {
        this.connectionToClient = client;
    }

    /**
     * Create a question entry in the database
     *
     * @param question question to be created
     */
    public void createQuestion(Question question) {
        //TODO STUB method
        System.out.println("QuestionTable - Create Question\n" +
                "Question: " + question);
        PreparedStatement stmt;
        Connection con = MysqlManager.ConnectToDB();
        try {
            stmt = con.prepareStatement(SQLContract.CREATE_QUESTION);
            stmt.setInt(1, question.getQID());
            stmt.setString(2, question.getText());
            stmt.setString(3, question.getAns(1));
            stmt.setString(4, question.getAns(2));
            stmt.setString(5, question.getAns(3));
            stmt.setString(6, question.getAns(4));
            stmt.setInt(7, question.getIndicator());
            stmt.setString(8, question.getAuthor());

            ResultSet rs = stmt.executeQuery();
            rs.close();
            stmt.close();
            MysqlManager.closeConnection(con);
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
    }

    /**
     * Read Question entry from the database, create a question object and return it.
     *
     * @param qid id of the question entry to read
     * @return Question Object filled with the entry data.
     */
    public Question readQuestion(int qid) {
        //TODO STUB method
        System.out.println("QuestionTable - Read Question\n" +
                "Question ID: " + qid);
        PreparedStatement stmt;
        Connection con = MysqlManager.ConnectToDB();
        try {
            stmt = con.prepareStatement(SQLContract.READ_QUESTION);
            stmt.setInt(1, qid);
            stmt.setInt(2, qid);


            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                //TODO NEW Question
            }
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
        return new Question(qid);
    }

    /**
     * Update a question entry with a new question object data
     *
     * @param question question object corresponding to the entry that is going to be updated
     */
    public void updateQuestion(Question question) {
        //TODO STUB method
        System.out.println("QuestionTable - Update Question\n" +
                "Question: " + question);

    }

    /**
     * Delete Question from the database.
     *
     * @param question question object corresponding to the entry that is going to be deleted
     */
    public void deleteQuestion(Question question) {
        //TODO STUB method
        System.out.println("QuestionTable - Delete Question\n" +
                "Question: " + question);
    }

    public ArrayList<Question> selectAllQuestionsBySubject(int subjectID) {
        //TODO STUB method
        System.out.println("QuestionTable - Select Questions By Subject\n" +
                "Subject: " + subjectID);
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question(1));
        questions.add(new Question(2));
        questions.add(new Question(3));
        return questions;
    }

    public ArrayList<Question> selectAllQuestionsByExam(int examID) {
        //TODO STUB method
        System.out.println("QuestionTable - Select Questions By Exam\n" +
                "Exam: " + examID);
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question(4));
        questions.add(new Question(5));
        questions.add(new Question(6));
        return questions;
    }

    public ArrayList<Question> selectAllQuestionsByTeacher(String teacherID) {
        //TODO STUB method
        System.out.println("QuestionTable - Select Questions By teacher\n" +
                "Teacher: " + teacherID);
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question(7));
        questions.add(new Question(8));
        questions.add(new Question(9));
        return questions;
    }
}
