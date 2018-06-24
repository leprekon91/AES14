package server.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StatisticsTable {

    public static void CourseStatics(ArrayList<Integer> ExamsGrade) {
        System.out.println(" Course Statics  - All Grade\n");
        //-------------------------------------------------------------------------------------------------------------
        Connection con = MysqlManager.ConnectToDB();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQLContract.STATISTICS_BY_COURSE);

            while (rs.next()) {

                ExamsGrade.add(rs.getInt("grade"));

            }

            con.commit();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }

    }

    public static void SubjectStatics(ArrayList<Integer> ExamsGrade) {
        System.out.println("Subject Statics - All Grade\n");
        //-------------------------------------------------------------------------------------------------------------
        Connection con = MysqlManager.ConnectToDB();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQLContract.STATISTICS_BY_SUBJECT);

            while (rs.next()) {

                ExamsGrade.add(rs.getInt("grade"));

            }


            stmt.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }

    }

    public static void StudentStatics(ArrayList<Integer> ExamsGrade) {
        System.out.println(" Student Statics- All Grade\n");
        //-------------------------------------------------------------------------------------------------------------
        Connection con = MysqlManager.ConnectToDB();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQLContract.STATISTICS_BY_STUDENT);

            while (rs.next()) {

                ExamsGrade.add(rs.getInt("grade"));

            }


            stmt.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }

    }

    public static void TeacherStatics(ArrayList<Integer> ExamsGrade) {
        System.out.println(" Teacher Statics- All Grade\n");
        //-------------------------------------------------------------------------------------------------------------
        Connection con = MysqlManager.ConnectToDB();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQLContract.STATISTICS_BY_TEACHER);

            while (rs.next()) {

                ExamsGrade.add(rs.getInt("grade"));

            }


            stmt.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }

    }

    public static void ExamStatics(ArrayList<Integer> ExamsGrade) {
        System.out.println(" Exam Statics- All Grade\n");
        //-------------------------------------------------------------------------------------------------------------
        Connection con = MysqlManager.ConnectToDB();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQLContract.STATISTICS_BY_EXAM);

            while (rs.next()) {

                ExamsGrade.add(rs.getInt("grade"));

            }


            stmt.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }

    }

}
