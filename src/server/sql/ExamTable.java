package server.sql;

import com.data.*;

import java.sql.*;
import java.util.ArrayList;

public class ExamTable {

    /**
     * Create a exam entry in the database
     *
     * @param exam to be created
     */
    public static void createExam(Exam exam) {
        System.out.println("ExamTable - Create Exam\n" +
                "Exam: " + exam);
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmtQ = null;
        PreparedStatement stmtE = null;
        Statement stmtExamID = null;

        try {
            stmtE = con.prepareStatement(SQLContract.CREATE_EXAM);
            stmtE.setInt(1, exam.getAssignedTime());
            stmtE.setString(2, exam.getTeacherNotes());
            stmtE.setString(3, exam.getStudentNotes());
            stmtE.setInt(4, exam.getExamSubject().getSubjectID());
            stmtE.setInt(5, exam.getExamCourse().getCourseNumber());
            stmtE.setString(6, exam.getExamAuthorTeacher().getUsername());
            stmtE.setBoolean(7, exam.isUsed());
            stmtE.execute();
            stmtE.close();

            stmtExamID = con.createStatement();
            ResultSet ExamID = stmtExamID.executeQuery(SQLContract.RECIVE_EXAM_ID);
            ExamID.next();
            exam.setExamNumber(ExamID.getInt("LAST_INSERT_ID()"));


            for (Question q : exam.getExamQuestions()) {
                stmtQ = con.prepareStatement(SQLContract.ADD_QUESTION_TO_EXAM);
                stmtQ.setInt(1, exam.getExamNumber());
                stmtQ.setInt(2, q.getQID());
                stmtQ.setInt(3, q.getSubject().getSubjectID());
                stmtQ.setInt(4, exam.getExamCourse().getCourseNumber());
                stmtQ.setInt(5, q.getGrade());
                stmtQ.execute();
                stmtQ = null;
            }


            stmtQ.close();
            ExamID.close();
            con.close();

        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
    }

    /**
     * Read Exam entry from the database, create a Exam object and return it.
     *
     * @param examID exam integer ID
     * @param course sourse integer id
     * @return Exam Object filled with the entry data.
     */
    public static Exam readExam(int examID, int course, int subject) {
        System.out.println("ExamTable - Read Exam\n" +
                "Exam ID: " + examID);

        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmtQ = null;
        PreparedStatement stmtE = null;
        try {

            stmtE = con.prepareStatement(SQLContract.READ_EXAM);
            stmtE.setInt(1, examID);
            stmtE.setInt(2, course);
            stmtE.setInt(3, subject);
            ResultSet rs = stmtE.executeQuery();

            ArrayList<Question> questionArray = new ArrayList<Question>();
            stmtQ = con.prepareStatement(SQLContract.ALL_QUESTIONS_IN_EXAM);
            stmtQ.setInt(1, examID);
            stmtQ.setInt(2, course);
            stmtQ.setInt(3, subject);
            ResultSet qrs = stmtQ.executeQuery();
            ArrayList<Integer> ExamsGrade = new ArrayList<Integer>();
            while (qrs.next()) {
                Question question = new Question(
                        qrs.getString("question_text"),
                        new String[]{
                                qrs.getString("ans_1"),
                                qrs.getString("ans_2"),
                                qrs.getString("ans_3"),
                                qrs.getString("ans_4")
                        },
                        qrs.getInt("indicator"),
                        new Subject(
                                qrs.getInt("id_subject"),
                                qrs.getString("subject_name")
                        ),
                        new Teacher(
                                new User(
                                        qrs.getString("user_name"),
                                        qrs.getString("first_name"),
                                        qrs.getString("last_name"),
                                        2)
                        )
                );
                ExamsGrade.add(qrs.getInt("question_grade"));
                question.setQID(qrs.getInt("id_question"));
                questionArray.add(question);


            }
            rs.next();


            Exam resultExam = new Exam(
                    questionArray,
                    rs.getString("teacher_instructions"),
                    rs.getString("student_instructions"),
                    new Course(
                            rs.getInt("courses_id_course"),
                            rs.getString("course_name"),
                            new Subject(
                                    rs.getInt("subjects_id_subject"),
                                    rs.getString("subject_name")
                            )
                    ),
                    new Subject(
                            rs.getInt("subjects_id_subject"),
                            rs.getString("subject_name")
                    ),
                    new Teacher(
                            new User(
                                    rs.getString("users_user_name"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    2)
                    ),
                    rs.getBoolean("used"),
                    rs.getInt("exam_duration")

            );

            resultExam.setExamNumber(rs.getInt("id_exam"));
            if (stmtQ != null)
                stmtQ.close();
            if (stmtE != null)
                stmtE.close();
            con.close();
            return (resultExam);

        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
        return null;

    }

    /**
     * Update a exam entry with a new exam object data
     *
     * @param exam object corresponding to the entry that is going to be updated
     */
    public static void updateExam(Exam exam) {
        System.out.println("ExamsTable - Update Exam\n" +
                "Exam: " + exam);

        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;
        PreparedStatement stmtQ = null;
        try {
            stmt = con.prepareStatement(SQLContract.UPDATE_EXAM);
            stmt.setInt(1, exam.getAssignedTime());
            stmt.setString(2, exam.getTeacherNotes());
            stmt.setString(3, exam.getStudentNotes());
            stmt.setString(4, exam.getExamAuthorTeacher().getUsername());
            stmt.setBoolean(5, exam.isUsed());
            stmt.setInt(6, exam.getExamNumber());
            stmt.setInt(7, exam.getExamSubject().getSubjectID());
            stmt.setInt(8, exam.getExamCourse().getCourseNumber());

            int i = 0;
            stmtQ = con.prepareStatement(SQLContract.UPDATE_QUESTION_IN_EXAM);
            for (Question q : exam.getExamQuestions()) {
                stmtQ.setInt(1, exam.getQuestionGrades()[i]);
                stmtQ.setInt(2, exam.getExamNumber());
                stmtQ.setInt(3, q.getQID());
                stmtQ.setInt(4, q.getSubject().getSubjectID());
                i++;
            }

            stmt.execute();
            stmtQ.execute();

            stmt.close();
            stmtQ.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }

    }


    /**
     * Delete Exam(and the question of the exam) from the database.
     *
     * @param exam object corresponding to the entry that is going to be deleted
     */
    public static void deleteExam(Exam exam) {
        System.out.println("ExamTable - Delete Exam\n" +
                "Exam: " + exam);
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmtQ = null;
        PreparedStatement stmtE = null;
        try {
            if (exam.getExamQuestions().size() >= 0) {
                stmtQ = con.prepareStatement(SQLContract.DELETE_QUESTION_FROM_EXAM);
                stmtQ.setInt(1, exam.getExamNumber());
                stmtQ.setInt(2, exam.getExamSubject().getSubjectID());
                stmtQ.setInt(3, exam.getExamCourse().getCourseNumber());
                stmtQ.execute();
            }
            stmtE = con.prepareStatement(SQLContract.DELETE_EXAM);
            stmtE.setInt(1, exam.getExamNumber());
            stmtE.setInt(2, exam.getExamSubject().getSubjectID());
            stmtE.setInt(3, exam.getExamCourse().getCourseNumber());
            stmtE.execute();
            if (stmtQ != null)
                stmtQ.close();
            stmtE.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
    }

    public static ArrayList<Exam> selectAllExamBySubject(int subjectID) {
        //TODO STUB method
        System.out.println("ExamTable - Select Exam By Subject\n" +
                "Subject: " + subjectID);
        ArrayList<Exam> exam = new ArrayList<>();

        return exam;
    }

    public static ArrayList<Exam> selectAllExamByCourse(int courseID) {
        //TODO STUB method
        System.out.println("ExamTable - Select Exam By course\n" +
                "Exam: " + courseID);
        ArrayList<Exam> exam = new ArrayList<>();

        return exam;
    }

    public static ArrayList<Exam> selectAllExamByTeacher(String teacherID) {
        //TODO STUB method
        System.out.println("ExamTable - Select Exam By teacher\n" +
                "Teacher: " + teacherID);
        ArrayList<Exam> exam = new ArrayList<>();

        return exam;
    }

    public static void selectAllExam(ArrayList<Exam> data) {
        System.out.println("ExamTable - All Exam\n");

        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmtQ = null;
        PreparedStatement stmtE = null;
        try {
            stmtE = con.prepareStatement(SQLContract.ALL_EXAM);
            ResultSet rs = stmtE.executeQuery();
            ArrayList<Question> questionArray = new ArrayList<Question>();

            while (rs.next()) {
                stmtQ = con.prepareStatement(SQLContract.ALL_QUESTIONS_IN_EXAM);
                stmtQ.setInt(1, rs.getInt("id_exam"));
                stmtQ.setInt(2, rs.getInt("courses_id_course"));
                stmtQ.setInt(3, rs.getInt("subjects_id_subject"));
                ResultSet qrs = stmtQ.executeQuery();
                while (qrs.next()) {
                    Question question = new Question(
                            qrs.getString("question_text"),
                            new String[]{
                                    qrs.getString("ans_1"),
                                    qrs.getString("ans_2"),
                                    qrs.getString("ans_3"),
                                    qrs.getString("ans_4")
                            },
                            qrs.getInt("indicator"),
                            new Subject(
                                    qrs.getInt("id_subject"),
                                    qrs.getString("subject_name")
                            ),
                            new Teacher(
                                    new User(
                                            qrs.getString("user_name"),
                                            qrs.getString("first_name"),
                                            qrs.getString("last_name"),
                                            2)
                            )
                    );

                    question.setQID(qrs.getInt("id_question"));
                    questionArray.add(question);
                }
                Exam exam = new Exam(
                        questionArray,
                        rs.getString("teacher_instructions"),
                        rs.getString("student_instructions"),
                        new Course(
                                rs.getInt("id_course"),
                                rs.getString("course_name"),
                                new Subject(
                                        rs.getInt("id_subject"),
                                        rs.getString("subject_name")
                                )
                        ),
                        new Subject(
                                rs.getInt("id_subject"),
                                rs.getString("subject_name")
                        ),
                        new Teacher(
                                new User(
                                        rs.getString("users_user_name"),
                                        rs.getString("first_name"),
                                        rs.getString("last_name"),
                                        2)
                        ),
                        rs.getBoolean("used"),
                        rs.getInt("exam_duration")


                );
                exam.setExamNumber(rs.getInt("id_exam"));
                questionArray.clear();
                data.add(exam);
            }
            stmtE.execute();
            stmtQ.execute();
            stmtQ.close();
            stmtE.close();
            con.close();

        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
    }
    //---------------------------------------------------------------------------------------------------------------

    /**
     * Solved_Exam methods
     */
    public static void createSolvedExam(Solved_Exam solvedExam) {
        System.out.println("ExamTable - Create Solved_Exam\n" +
                "Solved Exam: " + solvedExam);
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;
        PreparedStatement stmtS = null;

        try {
            int i = 0;
            for (Question q : solvedExam.getExam().getExamQuestions()) {
                stmt = con.prepareStatement(SQLContract.CREATE_QUESTION_SOLUTION);
                stmt.setString(1, solvedExam.getSolvingStudent().getUsername());
                stmt.setInt(2, solvedExam.getExam().getExamNumber());
                stmt.setInt(3, solvedExam.getExam().getExamCourse().getCourseNumber());
                stmt.setInt(4, q.getSubject().getSubjectID());
                stmt.setInt(5, q.getQID());
                stmt.setInt(6, solvedExam.getStudentAnswers()[i]);
                i++;
                stmt.execute();
                stmt = null;
            }
            //stmt.execute();

            stmtS = con.prepareStatement(SQLContract.CREATE_EXAM_SOLUTION);
            stmtS.setString(1, solvedExam.getSolvingStudent().getUsername());
            stmtS.setInt(2, solvedExam.getExam().getExamNumber());
            stmtS.setInt(3, solvedExam.getExam().getExamSubject().getSubjectID());
            stmtS.setInt(4, solvedExam.getExam().getExamCourse().getCourseNumber());
            stmtS.setInt(5, solvedExam.getExamGrade());
            stmtS.setBoolean(6, solvedExam.isApproved());
            stmtS.setString(7, solvedExam.getTeacherGradingNotes());
            stmtS.setString(8, solvedExam.getExam().getExamAuthorTeacher().getUsername());
            stmtS.setInt(9, solvedExam.getExamSolvingDuration());
            stmtS.setBoolean(10, solvedExam.isCopySuspect());
            stmtS.setBoolean(11, solvedExam.isWordExam());


            stmtS.execute();
            if (stmt != null)
                stmt.close();
            stmtS.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
    }

    public static Solved_Exam readSolvedExam(Solved_Exam solvedExam, int examID, int course, int subject, String studentName) {
        System.out.println("StudentAnswersTable - Read SolvedExam\n" +
                "Solved Exam ID: " + solvedExam.getExam().getExamNumber());
        //users_user_name, exams_id_exam, exams_subjects_id_subject, exams_courses_id_course, grade, execution_duration, approved, teacher_notes
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;
        PreparedStatement stmtE = null;

        solvedExam.setExam(readExam(examID, course, subject));
        try {
            stmtE = con.prepareStatement(SQLContract.READ_EXAM_SOLUTION);
            stmtE.setInt(1, examID);
            stmtE.setInt(2, course);
            stmtE.setInt(3, subject);
            stmtE.setString(4, studentName);
            ResultSet rs = stmtE.executeQuery();


            stmt = con.prepareStatement(SQLContract.READ_QUESTION_SOLUTION);
            stmt.setInt(1, examID);
            stmt.setInt(2, course);
            stmt.setInt(3, subject);
            stmt.setString(4, studentName);
            ResultSet qrs = stmt.executeQuery();

            ArrayList<Integer> answersArrayl = new ArrayList<Integer>();
            while (qrs.next()) {
                answersArrayl.add(qrs.getInt("answer"));
            }
            int answersArray[] = new int[answersArrayl.size()];
            int i = 0;
            for (Integer ansNum : answersArrayl)
                answersArray[i] = ansNum;

            rs.next();
            Solved_Exam resultSolvedExam = new Solved_Exam(
                    solvedExam.getExam(),
                    answersArray,
                    new Student(
                            new User(
                                    rs.getString("user_name"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    1)
                    ),
                    rs.getInt("execution_duration"),
                    new Teacher(
                            new User(
                                    rs.getString("user_name"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    2)
                    ),
                    rs.getInt("grade"),
                    rs.getBoolean("approved"),
                    rs.getString("teacher_notes"),
                    rs.getBoolean("exam_type"),
                    rs.getBoolean("suspected_of_copying")
            );


            stmtE.close();
            stmt.close();
            con.close();
            return resultSolvedExam;

        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
        return null;

    }

    public static void updateSolvedExam(Solved_Exam solvedExam) {
        System.out.println("StudentAnswersTable - Update Exam\n" +
                "Solved Exam: " + solvedExam);
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;
        PreparedStatement stmtS = null;

        try {
            int i = 0;
            for (Question q : solvedExam.getExam().getExamQuestions()) {
                stmt = con.prepareStatement(SQLContract.UPDATE_QUESTION_SOLUTION);
                stmt.setInt(1, solvedExam.getStudentAnswers()[i]);
                stmt.setString(2, solvedExam.getSolvingStudent().getUsername());
                stmt.setInt(3, solvedExam.getExam().getExamNumber());
                stmt.setInt(4, solvedExam.getExam().getExamCourse().getCourseNumber());
                stmt.setInt(5, q.getSubject().getSubjectID());
                stmt.setInt(6, q.getQID());
                i++;
                stmt.execute();

                stmt = null;
            }

            stmtS = con.prepareStatement(SQLContract.UPDATE_EXAM_SOLUTION);
            stmtS.setInt(1, solvedExam.getExamGrade());
            stmtS.setBoolean(2, solvedExam.isApproved());
            stmtS.setString(3, solvedExam.getTeacherGradingNotes());
            stmtS.setBoolean(4, solvedExam.isCopySuspect());
            stmtS.setBoolean(5, solvedExam.isWordExam());
            stmtS.setInt(6, solvedExam.getExamSolvingDuration());
            stmtS.setString(7, solvedExam.getSolvingStudent().getUsername());
            stmtS.setInt(8, solvedExam.getExam().getExamNumber());
            stmtS.setInt(9, solvedExam.getExam().getExamCourse().getCourseNumber());
            stmtS.setInt(10, solvedExam.getExam().getExamSubject().getSubjectID());

            stmtS.execute();
            if (stmt != null)
                stmt.close();
            stmtS.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }

    }


    public static void deleteSolvedExam(Solved_Exam solvedExam) {
        System.out.println("StudentAnswersTable - Delete Exam\n" +
                "Solved Exam: " + solvedExam);
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;
        PreparedStatement stmtS = null;
        try {
            for (Question q : solvedExam.getExam().getExamQuestions()) {
                stmt = con.prepareStatement(SQLContract.DELETE_QUESTION_SOLUTION);
                stmt.setInt(1, solvedExam.getExam().getExamNumber());
                stmt.setInt(2, solvedExam.getExam().getExamCourse().getCourseNumber());
                stmt.setInt(3, solvedExam.getExam().getExamSubject().getSubjectID());
                stmt.setString(4, solvedExam.getSolvingStudent().getUsername());
                stmt.setInt(5, q.getQID());
                stmt.execute();
                stmt = null;
            }


            stmtS = con.prepareStatement(SQLContract.DELETE_EXAM_SOLUTION);
            stmtS.setInt(1, solvedExam.getExam().getExamNumber());
            stmtS.setInt(2, solvedExam.getExam().getExamCourse().getCourseNumber());
            stmtS.setInt(3, solvedExam.getExam().getExamSubject().getSubjectID());
            stmtS.setString(4, solvedExam.getSolvingStudent().getUsername());
            stmtS.execute();
            if (stmt != null)
                stmt.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }

    }


}

