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
                stmtQ.close();

            }


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

    /**
     * @param data
     */
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
                        new ArrayList<>(questionArray),
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

    /*
      Solved_Exam methods
     */

    /**
     * Create a solved exam entry in the data base
     *
     * @param solvedExam exam object corresponding to the entry that is going to be updated
     */
    public static void createSolvedExam(Solved_Exam solvedExam) {
        System.out.println("ExamTable - Create Solved_Exam\n" +
                "Solved Exam: " + solvedExam);
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;
        PreparedStatement stmtS = null;

        try {
            if (solvedExam.isWordExam() == false) {
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
            } else {
                stmt = con.prepareStatement(SQLContract.CREATE_WORD_EXAM_SOLUTION);
                stmt.setString(1, solvedExam.getSolvingStudent().getUsername());
                stmt.setInt(2, solvedExam.getExam().getExamNumber());
                stmt.setInt(3, solvedExam.getExam().getExamCourse().getCourseNumber());
                stmt.setInt(4, solvedExam.getExam().getExamSubject().getSubjectID());
                stmt.setString(5, ((Word_Solved_Exam) solvedExam).getSolvedExamFile().getPath());
                stmt.execute();
                stmt = null;
            }

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

    /**
     * Read Solved Exam entry from the database, create a Solved Exam object and return it.
     *
     * @param solvedExam Solved exam to be filled with data from the DB, required exam full ID and student username
     * @return Solved Exam Object filled with the entry data.
     */
    public static Solved_Exam readSolvedExam(Solved_Exam solvedExam, int examID, int courseID, int subjectID, String studentName) {
        System.out.println("StudentAnswersTable - Read SolvedExam\n" +
                "Solved Exam ID: " + solvedExam.getExam().getExamNumber());
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;
        PreparedStatement stmtE = null;
        PreparedStatement stmtW = null;

        solvedExam.setExam(readExam(examID, courseID, subjectID));
        try {
            stmtE = con.prepareStatement(SQLContract.READ_EXAM_SOLUTION);
            stmtE.setInt(1, examID);
            stmtE.setInt(2, courseID);
            stmtE.setInt(3, subjectID);
            stmtE.setString(4, studentName);
            ResultSet rs = stmtE.executeQuery();

            stmtW = con.prepareStatement(SQLContract.CHECK_SOLVED_EXAM_TYPE);
            stmtW.setInt(1, examID);
            stmtW.setInt(2, courseID);
            stmtW.setInt(3, subjectID);
            stmtW.setString(4, studentName);
            ResultSet wrs = stmtW.executeQuery();
            wrs.next();
            boolean isWordExam = wrs.getBoolean("exam_type");

            if (isWordExam == false) {
                stmt = con.prepareStatement(SQLContract.READ_QUESTION_SOLUTION);
                stmt.setInt(1, examID);
                stmt.setInt(2, courseID);
                stmt.setInt(3, subjectID);
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
            } else {
                stmt = con.prepareStatement(SQLContract.READ_WORD_EXAM_SOLUTION);
                stmt.setInt(1, examID);
                stmt.setInt(2, courseID);
                stmt.setInt(3, subjectID);
                stmt.setString(4, studentName);
                ResultSet qrs = stmt.executeQuery();
                qrs.next();
                String fileAddress = qrs.getString("word_file_add");
                rs.next();
                Word_Solved_Exam resultSolvedExam = new Word_Solved_Exam(
                        solvedExam.getExam(),
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
                        rs.getBoolean("suspected_of_copying"),
                        fileAddress);
                stmtE.close();
                stmt.close();
                con.close();
                return resultSolvedExam;
            }

        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
        return null;
    }

    /**
     * Update Solved Exam entry in the database.
     *
     * @param solvedExam exam object corresponding to the entry that is going to be updated
     */
    public static void updateSolvedExam(Solved_Exam solvedExam) {
        System.out.println("StudentAnswersTable - Update Exam\n" +
                "Solved Exam: " + solvedExam);
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;
        PreparedStatement stmtS = null;
        try {
            if (solvedExam.isWordExam() == false) {
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
            } else {
                stmt = con.prepareStatement(SQLContract.UPDATE_WORD_EXAM_SOLUTION);
                stmt.setString(1, ((Word_Solved_Exam) solvedExam).getSolvedExamFile().getPath());
                stmt.setString(2, solvedExam.getSolvingStudent().getUsername());
                stmt.setInt(3, solvedExam.getExam().getExamNumber());
                stmt.setInt(4, solvedExam.getExam().getExamCourse().getCourseNumber());
                stmt.setInt(5, solvedExam.getExam().getExamSubject().getSubjectID());
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


    /**
     * Delete Solved Exam entry in the database.
     *
     * @param solvedExam exam object corresponding to the entry that is going to be deleted
     */
    public static void deleteSolvedExam(Solved_Exam solvedExam) {
        System.out.println("StudentAnswersTable - Delete Exam\n" +
                "Solved Exam: " + solvedExam);
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;
        PreparedStatement stmtS = null;
        try {
            if (solvedExam.isWordExam() == false) {
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
            } else {
                stmt = con.prepareStatement(SQLContract.DELETE_WORD_EXAM_SOLUTION);
                stmt.setString(1, ((Word_Solved_Exam) solvedExam).getSolvedExamFile().getPath());
                stmt.setString(2, solvedExam.getSolvingStudent().getUsername());
                stmt.setInt(3, solvedExam.getExam().getExamNumber());
                stmt.setInt(4, solvedExam.getExam().getExamCourse().getCourseNumber());
                stmt.setInt(5, solvedExam.getExam().getExamSubject().getSubjectID());
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

    public static void examSetUsed(Exam exam) {
        try {
            Connection con = MysqlManager.ConnectToDB();
            PreparedStatement stmt = con.prepareStatement(SQLContract.EXAM_SET_USED);
            stmt.setInt(1, exam.getExamNumber());
            stmt.setInt(2, exam.getExamSubject().getSubjectID());
            stmt.setInt(3, exam.getExamCourse().getCourseNumber());
            stmt.execute();
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
    }

//---------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Statistic functions
     */

    public ArrayList<Solved_Exam> selectAllSolvedExamsBy_Student(String studentUserName) {

        ArrayList<Solved_Exam> resultArray = new ArrayList<Solved_Exam>();
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SQLContract.ALL_SOLVED_EXAMS_BY_STUDENT);
            stmt.setString(1, studentUserName);
            ResultSet rs = stmt.executeQuery();
            Solved_Exam tempExam = new Solved_Exam(null, null, null, 0, null, false);
            while (rs.next()) {
                tempExam = readSolvedExam(tempExam, rs.getInt("exams_id_exam"), rs.getInt("exams_courses_id_course"),
                        rs.getInt("exams_subjects_id_subject"), studentUserName);
                resultArray.add(tempExam);
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
        return resultArray;
    }

    public ArrayList<Solved_Exam> selectAllSolvedExamsBy_ExaminerTeacher(String TeacherUserName) {

        ArrayList<Solved_Exam> resultArray = new ArrayList<Solved_Exam>();
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SQLContract.ALL_SOLVED_EXAMS_BY_EXAMINER_TEACHER);
            stmt.setString(1, TeacherUserName);
            ResultSet rs = stmt.executeQuery();
            Solved_Exam tempExam = new Solved_Exam(null, null, null, 0, null, false);
            while (rs.next()) {
                tempExam = readSolvedExam(tempExam, rs.getInt("exams_id_exam"), rs.getInt("exams_courses_id_course"),
                        rs.getInt("exams_subjects_id_subject"), rs.getString("student_user"));
                resultArray.add(tempExam);
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
        return resultArray;
    }

    public ArrayList<Solved_Exam> selectAllSolvedExamsBy_AuthorTeacher(String TeacherUserName) {

        ArrayList<Solved_Exam> resultArray = new ArrayList<Solved_Exam>();
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SQLContract.ALL_SOLVED_EXAMS_BY_AUTHOR_TEACHER);
            stmt.setString(1, TeacherUserName);
            ResultSet rs = stmt.executeQuery();
            Solved_Exam tempExam = new Solved_Exam(null, null, null, 0, null, false);
            while (rs.next()) {
                tempExam = readSolvedExam(tempExam, rs.getInt("exams_id_exam"), rs.getInt("exams_courses_id_course"),
                        rs.getInt("exams_subjects_id_subject"), rs.getString("student_user"));
                resultArray.add(tempExam);
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
        return resultArray;
    }

    public ArrayList<Solved_Exam> selectAllSolvedExamsBy_CourseNumber(int courseId) {

        ArrayList<Solved_Exam> resultArray = new ArrayList<Solved_Exam>();
        Connection con = MysqlManager.ConnectToDB();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(SQLContract.ALL_SOLVED_EXAMS_BY_COURSE);
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            Solved_Exam tempExam = new Solved_Exam(null, null, null, 0, null, false);
            while (rs.next()) {
                tempExam = readSolvedExam(tempExam, rs.getInt("exams_id_exam"), rs.getInt("exams_courses_id_course"),
                        rs.getInt("exams_subjects_id_subject"), rs.getString("student_user"));
                resultArray.add(tempExam);
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
        return resultArray;
    }


}

