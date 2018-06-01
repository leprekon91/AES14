package server.sql;

import com.Contract;
import com.data.Message;
import com.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorizeUser {
    private static PreparedStatement stmt;

    public static Message authorize(String username, String password) {
        //Prepare Answer Message:
        Message ans = new Message(0, null);
        //Connect to database
        Connection con = MysqlManager.ConnectToDB();
        try {
            stmt = con.prepareStatement(SQLContract.USER_AUTH);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            //if user exists,set the message to authorized
            User user = new User("", "");
            while (rs.next()) {
                user.setId(rs.getString("id"));
                user.setPass(rs.getString("password"));
                user.setType(rs.getInt("type"));
            }
            if (password.equals(user.getPass()))
                ans.setType(Contract.AUTH_YES);
            else
                ans.setType(Contract.AUTH_NO);
            ans.setData(user);
            if (stmt != null)
                stmt.close();
            MysqlManager.closeConnection(con);
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }

        return ans;
    }
}
