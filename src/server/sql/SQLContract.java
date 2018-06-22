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
            "\t\tON questions.teacher_user = users.user_name\n" +
            "WHERE (users.user_name=questions.teacher_user AND questions.subjects_id_subject=subjects.id_subject);";
    //-----------------CRUD---------------------------------------------------------------------------------------------
    public static final String CREATE_QUESTION = "INSERT INTO questions" +
            "(question_text,ans_1,ans_2,ans_3,ans_4,indicator,teacher_user,subjects_id_subject)" +
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
            "`teacher_user` = ?\n" +
            "WHERE `id_question` = ? AND `subjects_id_subject` = ?;";
    public static final String DELETE_QUESTION = "DELETE FROM questions " +
            "WHERE id_question = ? AND subjects_id_subject = ?;";
    //------------------------------------------------------------------------------------------------------------------
    public static final String SHOW_QUESTION_BY_ID = "SELECT * FROM questions " +
            "WHERE id_question =?;";
    public static final String ALL_QUESTIONS_IN_EXAM = "SELECT questions.*,subjects.*,users.user_name,users.first_name,users.last_name,exams_has_questions.question_grade\n" +
            "FROM questions\n" +
            "\tJOIN subjects\n" +
            "\t\tON questions.subjects_id_subject = subjects.id_subject\n" +
            "\tJOIN users \n" +
            "\t\tON questions.teacher_user = users.user_name\n" +
            "\tJOIN exams_has_questions\n" +
            "\t\tON questions.id_question= exams_has_questions.questions_id_question\n" +
            "WHERE users.user_name=questions.teacher_user AND questions.subjects_id_subject=subjects.id_subject AND exams_has_questions.exams_id_exam =? AND exams_has_questions.exam_courses_id= ? AND exams_has_questions.questions_subjects_id_subject = ?;";
    public static final String RECIVE_EXAM_ID = "SELECT LAST_INSERT_ID();";
    public static final String SHOW_TEACHERS_QUESTION = "SELECT * FROM questions " +
            "WHERE teacher_user =?;";
    public static final String SHOW_QUESTION_IN_SUBJECTS = "SELECT * FROM questions " +
            "WHERE subjects_id_subject=?;";
    //------------------------------------------------------------------------------------------------------------------


    //Queries for Exam Table
    //-----------------CRUD---------------------------------------------------------------------------------------------
    public static final String CREATE_EXAM = "INSERT INTO exams" +
            "(exam_duration,teacher_instructions,student_instructions,subjects_id_subject,courses_id_course,users_user_name,used) " +
            "VALUES (?,?,?,?,?,?,?);";
    public static final String CREATE_EXAM_HAS_QUESTIONS = "INSERT INTO exams_has_questions" +
            "(exams_id_exam,questions_id_question,questions_subjects_id_subject,exam_courses_id,question_grade) " +
            "VALUES (?,?,?,?,?);";

    public static final String READ_EXAM = "SELECT exams.*,subjects.subject_name,courses.course_name,users.first_name ,users.last_name \n" +
            "FROM exams\n" +
            "\tJOIN subjects \n" +
            "\t\tON exams.subjects_id_subject = subjects.id_subject \n" +
            "\tJOIN courses\n" +
            "\t\tON exams.courses_id_course = courses.id_course \n" +
            "\tJOIN users\n" +
            "\t\tON exams.users_user_name = users.user_name \n" +
            "WHERE exams.id_exam = ? AND exams.courses_id_course = ? AND exams.subjects_id_subject = ?;";

    static final String UPDATE_EXAM = "UPDATE exams\n" +
            "SET\n" +
            "`exam_duration` = ?,\n" +
            "`teacher_instructions` = ?,\n" +
            "`student_instructions` = ?,\n" +
            "`users_user_name` = ?\n" +
            "`used` = ?\n" +
            "WHERE `id_exam` = ? AND `subjects_id_subject` = ? AND `courses_id_course` = ?;";
    public static final String DELETE_EXAM = "DELETE FROM exams " +
            "WHERE id_exam= ? AND subjects_id_subject = ? AND courses_id_course = ?;";
    public static final String DELETE_QUESTION_FROM_EXAM = "DELETE FROM exams_has_questions " +
            "WHERE exams_id_exam = ? AND questions_subjects_id_subject = ? AND exam_courses_id =?;";
    //------------------------------------------------------------------------------------------------------------------
    public static final String ADD_QUESTION_TO_EXAM = "INSERT INTO exams_has_questions\n" +
            "(exams_id_exam,questions_id_question,questions_subjects_id_subject,exam_courses_id,question_grade) \n" +
            "VALUES (?,?,?,?,?);";
    public static final String ADD_QUESTION_TO_NEW_EXAM = "INSERT INTO exams_has_questions" +
            "(exams_id_exam,questions_id_question,questions_subjects_id_subject,exam_courses_id,question_grade) " +
            "VALUES (LAST_INSERT_ID(),?,?,?,?);";

    public static final String GET_ALL_EXAMS_BY_SUBJECT = "SELECT * FROM exams " +
            "WHERE subjects_id_subject =?;";
    public static final String GET_ALL_EXAMS_BY_COURSE = "SELECT * FROM exams " +
            "WHERE courses_id_course =?;";

    public static final String SUBJECT_BY_TEACHER = "SELECT subjects.id_subject,subjects.subject_name\n" +
            "FROM teachers_teach_subjects JOIN subjects\n" +
            "ON subjects_id_subject=id_subject\n" +
            "WHERE teacher_name=?;";
    public static final String COURSES_BY_TEACHER = "SELECT courses.id_course,courses.course_name,subjects.id_subject,subjects.subject_name FROM courses\n" +
            "\tjoin subjects\n" +
            "    on courses.subjects_id_subjects=subjects.id_subject\n" +
            "\tJOIN teachers_teach_subjects\n" +
            "    on teachers_teach_subjects.subjects_id_subject=subjects.id_subject\n" +
            "WHERE teachers_teach_subjects.teacher_name = ?;";



    public static final String UPDATE_QUESTION_IN_EXAM = "UPDATE `exams_has_questions`\n" +
            "SET\n" +
            "`question_grade` = ?,\n" +
            "WHERE `exams_id_exam` = ? AND `questions_id_question` = ? AND `questions_subjects_id_subject` = ? ;";


    //---------------------------------------------------------------------------------------------------------------

    public static final String ALL_EXAM = "SELECT exams.*,subjects.*,courses.*, users.first_name, users.last_name\n" +
            "FROM exams\n" +
            "\tJOIN subjects\n" +
            "\t\tON exams.subjects_id_subject = subjects.id_subject\n" +
            "\tJOIN courses\n" +
            "\t\tON exams.courses_id_course = courses.id_course\n" +
            "\tJOIN users\n" +
            "\t\tON exams.users_user_name = users.user_name;";
    //---------------------------------------------------------------------------------------------------------------
    //Queries for Exam solutions Table
    public static final String CREATE_QUESTION_SOLUTION = "INSERT INTO student_answers\n" +
            "(student_user,exams_has_questions_exams_id_exam, exams_has_questions_exam_courses_id, exams_has_questions_questions_subjects_id_subject, exams_has_questions_questions_id_question, answer )\n" +
            "VALUES (?,?,?,?,?,?);";
    public static final String CREATE_EXAM_SOLUTION = "INSERT INTO exam_solutions\n" +
            "(student_user, exams_id_exam, exams_subjects_id_subject, exams_courses_id_course, grade, approved, teacher_notes, teacher_user, execution_duration,suspected_of_copying, exam_type )\n" +
            " VALUES (?,?,?,?,?,?,?,?,?,?,?);";

    public static final String READ_QUESTION_SOLUTION =
            "SELECT * FROM student_answers\n" +
                    "                    WHERE exams_has_questions_exams_id_exam = ? AND exams_has_questions_exam_courses_id = ? AND exams_has_questions_questions_subjects_id_subject = ? AND student_user = ?;";
    public static final String READ_EXAM_SOLUTION =
            " SELECT exam_solutions.* ,users.user_name, users.first_name, users.last_name\n" +
                    "FROM exam_solutions\n" +
                    "\tJOIN users\n" +
                    "\t\tON exam_solutions.student_user = users.user_name\n" +
                    "WHERE exams_id_exam = ? AND exams_courses_id_course = ? AND exams_subjects_id_subject = ? AND student_user = ?;";

    public static final String UPDATE_QUESTION_SOLUTION =
            "UPDATE student_answers\n" +
                    "SET\n" +
                    "answer = ?\n" +
                    "WHERE student_user = ? AND exams_has_questions_exams_id_exam = ? AND exams_has_questions_exam_courses_id = ? AND exams_has_questions_questions_subjects_id_subject = ? AND exams_has_questions_questions_id_question =? ;";
    public static final String UPDATE_EXAM_SOLUTION =
            "UPDATE exam_solutions\n" +
                    "SET\n" +
                    "grade = ?,\n" +
                    "approved = ?,\n" +
                    "teacher_notes = ?,\n" +
                    "suspected_of_copying = ?,\n" +
                    "exam_type = ? ,\n" +
                    "execution_duration= ?\n" +
                    "WHERE student_user = ? AND exams_id_exam = ? AND exams_courses_id_course = ? AND exams_subjects_id_subject = ?;";
    public static final String DELETE_QUESTION_SOLUTION = "DELETE FROM student_answers \n" +
            "WHERE exams_has_questions_exams_id_exam = ? AND exams_has_questions_exam_courses_id =? AND exams_has_questions_questions_subjects_id_subject =? AND student_user = ? AND exams_has_questions_questions_id_question = ?;";
    public static final String DELETE_EXAM_SOLUTION = "DELETE FROM exam_solutions \n" +
            "WHERE exams_id_exam = ? AND exams_courses_id_course = ? AND exams_subjects_id_subject = ? AND student_user = ?;";

    // ------------------------------------------------------------------
    public static final String GET_ALL_STUDENTS_IN_COURSE = "SELECT users.user_name,users.first_name,users.last_name FROM users\n" +
            "\tJOIN student_studies_in_course\n" +
            "    ON users.user_name=student_studies_in_course.student_name\n" +
            "    where student_studies_in_course.courses_id_course=?;";
    //statistics
    public static final String STATISTICS_BY_COURSE = "select exam_solutions.grade from exam_solutions where exams_courses_id_course = ?;";
    public static final String STATISTICS_BY_SUBJECT = "select exam_solutions.grade from exam_solutions where  exams_subjects_id_subject= ?;";
    public static final String STATISTICS_BY_STUDENT = "select exam_solutions.grade from exam_solutions where student_user = ?;";
    public static final String STATISTICS_BY_EXAM = "select exam_solutions.grade from exam_solutions " +
            "WHERE  exams_subjects_id_subject= ? AND exams_courses_id_course = ? AND exams_id_exam = ?;";
    public static final String STATISTICS_BY_TEACHER = "select exam_solutions.grade from exam_solutions where  teacher_user= ?;";

}