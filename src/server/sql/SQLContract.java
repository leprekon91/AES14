package server.sql;

/**
 *
 */
public class SQLContract {
    // jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    final public static String databaseURL = "jdbc:mysql://localhost/aes?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static String user = "root";       //default database user
    public static String pass = "Braude";     //default database password

    public static final String USER_AUTH = "SELECT * FROM users " +
            "WHERE user_name =?;";

    //Queries for Question Table
    public static final String ALL_QUESTION = "SELECT * FROM aes.questions;";
    //-----------------CRUD---------------------------------------------------------------------------------------------
    public static final String CREATE_QUESTION = "INSERT INTO aes.questions" +
            "(question_text,ans_1,ans_2,ans_3,ans_4,indicator,users_user_name,subjects_id_subject)" +
            " VALUES (?,?,?,?,?,?,?,?);";
    public static final String READ_QUESTION = "SELECT * FROM aes.questions WHERE id_question=? AND subjects_id_subject=?;";

    public static final String UPDATE_QUESTION = "UPDATE `aes`.`questions`\n" +
            "SET\n" +
            "`question_text` = ?,\n" +
            "`ans_1` = ?,\n" +
            "`ans_2` = ?,\n" +
            "`ans_3` = ?,\n" +
            "`ans_4` = ?,\n" +
            "`indicator` = ?,\n" +
            "`users_user_name` = ?\n" +
            "WHERE `id_question` = ? AND `subjects_id_subject` = ?;";
    public static final String DELETE_QUESTION = "DELETE FROM aes.questions " +
            "WHERE id_question= ?;";
    //------------------------------------------------------------------------------------------------------------------
    public static final String SHOW_QUESTION_BY_ID = "SELECT * FROM aes.questions " +
            "WHERE id_question =?;";
    public static final String SHOW_TEACHERS_QUESTION = "SELECT * FROM aes.questions " +
            "WHERE users_user_name =?;";
    public static final String SHOW_QUESTION_IN_SUBJECTS = "SELECT * FROM aes.questions " +
            "WHERE subjects_id_subject=?;";
    //------------------------------------------------------------------------------------------------------------------


    //Queries for Exam Table
    public static final String ALL_EXAM = "SELECT * FROM aes.exams;";
    //-----------------CRUD---------------------------------------------------------------------------------------------
    public static final String CREATE_EXAM = "INSERT INTO aes.exams" +
            "(id_exam,exam_duration,teacher_instructions,student_instructions,subjects_id_subject,courses_id_course,users_user_name) " +
            "VALUES (?,?,?,?,?,?,?);";
    public static final String READ_EXAM = "SELECT * FROM aes.exams " +
            "WHERE id_exam =?;";
    public static final String UPDATE_EXAM = "UPDATE `aes`.`exams`\n" +
            "SET\n" +
            "`exam_duration` = ?,\n" +
            "`teacher_instructions` = ?,\n" +
            "`student_instructions` = ?,\n" +
            "`users_user_name` = ?\n" +
            "WHERE `id_exam` = ? AND `subjects_id_subject` = ? AND `courses_id_course` = ?;";
    public static final String DELETE_EXAM = "DELETE FROM aes.exams " +
            "WHERE id_exam= ?;";
    //------------------------------------------------------------------------------------------------------------------
    public static final String ADD_QUESTION_TO_EXAM = "INSERT INTO aes.exams_has_questions" +
            "(exams_id_exam,questions_id_question,questions_subjects_id_subject,question_grade) " +
            "VALUES (?,?,?,?);";

    public static final String GET_ALL_EXAMS_BY_SUBJECT = "SELECT * FROM aes.exams " +
            "WHERE subjects_id_subject =?;";
    public static final String GET_ALL_EXAMs_BY_COURSE = "SELECT * FROM aes.exams " +
            "WHERE courses_id_course =?;";

}
