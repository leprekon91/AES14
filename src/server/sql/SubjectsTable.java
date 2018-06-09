package server.sql;

import com.data.Subject;

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
}
