package server.sql;

/**
 *
 */
public class SQLContract {
    // jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    final public static String databaseURL = "jdbc:mysql://localhost/AES?" +
            "useUnicode=true" +
            "&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false" +
            "&serverTimezone=UTC" +
            "&useSSL=false";


    public static String user = "root";       //default database user
    public static String pass = "Braude";     //default database password

    public static final String USER_AUTH = "SELECT * FROM users " +
            "WHERE user_name =?;";

    //Queries for Question Table
    public static final String ALL_QUESTION = "SELECT questions.*,subjects.*,users.user_name,users.first_name,users.last_name\n" +
            "FROM questions \n" +
            "\tJOIN subjects \n" +
            "\t\tON questions.subjects_id_subject = subjects.id_subject\n" +
            "\tJOIN users \n" +
            "\t\tON questions.teacher_name = users.user_name\n" +
            "WHERE (users.user_name=questions.teacher_name AND questions.subjects_id_subject=subjects.id_subject);";
    //-----------------CRUD---------------------------------------------------------------------------------------------
    public static final String CREATE_QUESTION = "INSERT INTO questions" +
            "(question_text,ans_1,ans_2,ans_3,ans_4,indicator,teacher_name,subjects_id_subject)" +
            " VALUES (?,?,?,?,?,?,?,?);";
    public static final String READ_QUESTION = "SELECT * FROM questions WHERE id_question=? AND subjects_id_subject=?;";

    public static final String UPDATE_QUESTION = "UPDATE `questions`\n" +
            "SET\n" +
            "`question_text` = ?,\n" +
            "`ans_1` = ?,\n" +
            "`ans_2` = ?,\n" +
            "`ans_3` = ?,\n" +
            "`ans_4` = ?,\n" +
            "`indicator` = ?,\n" +
            "`teacher_name` = ?\n" +
            "WHERE `id_question` = ? AND `subjects_id_subject` = ?;";
    public static final String DELETE_QUESTION = "DELETE FROM questions " +
            "WHERE id_question = ? AND subjects_id_subject = ?;";
    //------------------------------------------------------------------------------------------------------------------
    public static final String SHOW_QUESTION_BY_ID = "SELECT * FROM questions " +
            "WHERE id_question =?;";
    public static final String SHOW_TEACHERS_QUESTION = "SELECT * FROM questions " +
            "WHERE teacher_name =?;";
    public static final String SHOW_QUESTION_IN_SUBJECTS = "SELECT * FROM questions " +
            "WHERE subjects_id_subject=?;";
    //------------------------------------------------------------------------------------------------------------------


    //Queries for Exam Table
    public static final String ALL_EXAM = "SELECT * FROM exams;";
    //-----------------CRUD---------------------------------------------------------------------------------------------
    public static final String CREATE_EXAM = "INSERT INTO exams" +
            "(id_exam,exam_duration,teacher_instructions,student_instructions,subjects_id_subject,courses_id_course,users_user_name) " +
            "VALUES (?,?,?,?,?,?,?);";
    public static final String READ_EXAM = "SELECT * FROM exams " +
            "WHERE id_exam =?;";
    public static final String UPDATE_EXAM = "UPDATE exams\n" +
            "SET\n" +
            "`exam_duration` = ?,\n" +
            "`teacher_instructions` = ?,\n" +
            "`student_instructions` = ?,\n" +
            "`users_user_name` = ?\n" +
            "WHERE `id_exam` = ? AND `subjects_id_subject` = ? AND `courses_id_course` = ?;";
    public static final String DELETE_EXAM = "DELETE FROM exams " +
            "WHERE id_exam= ?;";
    //------------------------------------------------------------------------------------------------------------------
    public static final String ADD_QUESTION_TO_EXAM = "INSERT INTO exams_has_questions" +
            "(exams_id_exam,questions_id_question,questions_subjects_id_subject,question_grade) " +
            "VALUES (?,?,?,?);";

    public static final String GET_ALL_EXAMS_BY_SUBJECT = "SELECT * FROM exams " +
            "WHERE subjects_id_subject =?;";
    public static final String GET_ALL_EXAMs_BY_COURSE = "SELECT * FROM exams " +
            "WHERE courses_id_course =?;";

    public static String SUBJECT_BY_TEACHER = "SELECT subjects.id_subject,subjects.subject_name\n" +
            "FROM teachers_teach_subjects JOIN subjects\n" +
            "ON subjects_id_subject=id_subject\n" +
            "WHERE teacher_name=?;";
}
