package server.sql;

import com.data.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthorizeUser {
    public static User authorize(User user) {
        Connection con = MysqlManager.ConnectToDB();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select users.* from users" +
                    "where username = '" + user.getUsername() + "' and password = '" + user.getPass() + "';");
            user = null;
            while (rs.next()) {

                user.setId(rs.getString("id"));
                user.setUsername(rs.getString("username"));
                user.setPass(rs.getString("password"));
                user.setType(rs.getInt("type"));
            }
            rs.close();

        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
