package server.sql;

public class SQLContract {
   // jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
	final public static String databaseURL = "jdbc:mysql://localhost/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static String user="root";       //default database user
	public static String pass="Braude";     //default database password

    public static final String USER_AUTH = "SELECT * FROM users " +
            "WHERE user_name =?;";

    //Queries of questions
    public static final String ALL_QUESTION = "SELECT * FROM aes.questions;";
    public static final String ENTER_NEW_QUESTION = "INSERT INTO aes.questions" +
            "(id_question,question_text,ans_1,ans_2,ans_3,ans_4,indicator,users_user_name,subjects_id_subjec)" +
            " VALUES (?,?,?,?,?,?,?,?,?);";
    public static final String DELETE_QUESTION = "DELETE FROM aes.questions " +
            "WHERE id_question= ?;";
    public static final String SHOW_QUESTION_BY_ID = "SELECT * FROM aes.questions " +
            "WHERE id_question =?;";
    public static final String SHOW_TEACHERS_QUESTION = "SELECT * FROM aes.questions " +
            "WHERE users_user_name =?;";
    public static final String SHOW_QUESTION_IN_SUBJECTS = "SELECT * FROM aes.questions " +
            "WHERE subjects_id_subject=?;";

    //Queries of exam
    public static final String ALL_EXAM = "SELECT * FROM aes.exams;";
    public static final String ENTER_NEW_EXAM = "INSERT INTO aes.exams(id_exam,exam_duration,teacher_instructions,student_instructions,subjects_id_subject,courses_id_course,users_user_name) " +
            "VALUES (?,?,?,?,?,?,?);";
    public static final String ENTER_NEW_QUESTION_TO_EXAM = "INSERT INTO aes.exams_has_questions(exams_id_exam,questions_id_question,questions_subjects_id_subject,question_grade) " +
            "VALUES (?,?,?,?);";
    public static final String DELETE_EXAM = "DELETE FROM aes.exams " +
            "WHERE id_exam= ?;";
    public static final String SHOW_EXAM_BY_ID = "SELECT * FROM aes.exams " +
            "WHERE id_exam =?;";
    public static final String SHOW_EXAM_IN_SUBJECTS = "SELECT * FROM aes.exams " +
            "WHERE subjects_id_subject =?;";
    public static final String SHOW_EXAM_IN_COURSE = "SELECT * FROM aes.exams " +
            "WHERE courses_id_course =?;";

}
