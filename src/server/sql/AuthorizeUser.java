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

    public static Message authorize(User user) {
        Connection con = MysqlManager.ConnectToDB();
        try {
            stmt = con.prepareStatement(SQLContract.USER_AUTH);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPass());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user.setId(rs.getString("id"));
                user.setType(rs.getInt("type"));

            }
            if (stmt != null)
                stmt.close();
            if (con != null)
                con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
        Message ans =new Message(0,user);
        if (user.getId() != null)
            ans.setType(Contract.AUTH_YES);
        else ans.setType(Contract.AUTH_NO);
        return ans;
    }
}
