package server.sql;

import com.Contract;
import com.data.Message;
import com.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorizeUser {

    private static AuthorizeUser INSTANCE = null;
    private ArrayList<User> loggedInUsers;

    private AuthorizeUser() {
        this.loggedInUsers = new ArrayList<>();
    }

    public Message authorize(String username, String password) {
        //Prepare Answer Message:
        Message ans = new Message(0, null);
        if (usernameExistsInLoggedInUsers(username)) {
            ans.setType(Contract.AUTH_NO);
            return ans;
        }
        //Statement
        PreparedStatement stmt;
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

    public static AuthorizeUser getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AuthorizeUser();

        return INSTANCE;
    }

    public boolean usernameExistsInLoggedInUsers(String username) {
        for (User u :
                loggedInUsers) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void deleteUserByUsername(String username) {
        for (int i = 0; i < loggedInUsers.size(); i++)
            if (loggedInUsers.get(i).getUsername().equals(username))
                loggedInUsers.remove(loggedInUsers.get(i));


    }
}
