package server.sql;

import com.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTable implements SQLInjection {
    @Override
    public User getUser(String username, String password) {
        User user = new User("", "");
        user.setType(0);
        //Statement
        PreparedStatement stmt;
        //Connect to database
        Connection con = MysqlManager.ConnectToDB();
        try {
            stmt = con.prepareStatement(SQLContract.USER_AUTH);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                //Create new User Object from database values
                user.setUsername(rs.getString("user_name"));
                user.setPass(rs.getString("pass"));
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setType(rs.getInt("type"));
            }
            if (stmt != null)
                stmt.close();
            MysqlManager.closeConnection(con);
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
        return user;
    }
}
