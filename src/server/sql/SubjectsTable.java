package server.sql;

import com.data.Course;
import com.data.Student;
import com.data.Subject;
import com.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectsTable {
    public static ArrayList<Subject> getAllSubjectsByTeacher(String teacherId) {
        ArrayList<Subject> subjects = new ArrayList<>();
        //------------------------------------
        PreparedStatement stmt;
        Connection con = MysqlManager.ConnectToDB();
        //------------------------------------
        try {
            stmt = con.prepareStatement(SQLContract.SUBJECT_BY_TEACHER);
            stmt.setString(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                subjects.add(
                        new Subject(
                                rs.getInt("id_subject"),
                                rs.getString("subject_name")
                        )
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);

        }
        return subjects;
    }

    public static void getAllTeachersCourses(ArrayList<Course> courses, String teacherId) {

        //------------------------------------
        PreparedStatement stmt;
        Connection con = MysqlManager.ConnectToDB();
        //------------------------------------
        try {
            stmt = con.prepareStatement(SQLContract.COURSES_BY_TEACHER);
            stmt.setString(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(
                        new Course(
                                rs.getInt("id_course"),
                                rs.getString("course_name"),
                                new Subject(rs.getInt("id_subject"), rs.getString("subject_name"))
                        )
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);

        }
    }

    public static void getAllStudentInCourse(ArrayList<Student> students, Course course) {

        //------------------------------------
        PreparedStatement stmt;
        Connection con = MysqlManager.ConnectToDB();
        //------------------------------------
        try {
            stmt = con.prepareStatement(SQLContract.GET_ALL_STUDENTS_IN_COURSE);
            stmt.setInt(1, course.getCourseNumber());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(
                        new Student(new User(
                                rs.getString("user_name"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                1
                        ))
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);

        }
    }
}
