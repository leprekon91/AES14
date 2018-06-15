package server.sql;

import com.Contract;
import com.data.Message;
import com.data.User;
import server.ocsf.ConnectionToClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * AuthorizeUser manages connected clients and authorizes them.
 */
public class AuthorizeUser {
    //Singleton Instance
    private static AuthorizeUser INSTANCE = null;
    //current connection to client
    // public ConnectionToClient connectionToClient;
    //User List <'user_name',Client Thread> of logged-in users.
    private HashMap<String, ConnectionToClient> loggedInUsers;

    /**
     * Constructor: initializes the User List
     */
    private AuthorizeUser() {
        this.loggedInUsers = new HashMap<>();
    }

    /**
     * Handle Login Request. checks the database against the username and password received.
     *
     * @param username
     * @param password
     * @return Reply with Yes Or NO
     */
    public Message authorize(String username, String password, ConnectionToClient connectionToClient) {
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

                //Create new User Object from database values
                user.setUsername(rs.getString("user_name"));
                user.setPass(rs.getString("pass"));
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setType(rs.getInt("type"));
            }
            if (password.equals(user.getPass())) {
                ans.setType(Contract.AUTH_YES);
                this.loggedInUsers.put(user.getUsername(), connectionToClient);
            } else {
                ans.setType(Contract.AUTH_NO);
            }
            ans.setData(user);
            if (stmt != null)
                stmt.close();
            MysqlManager.closeConnection(con);
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }

        return ans;
    }

    /**
     * Get Instance for singleton
     *
     * @return Instance of AuthorizeUser
     */
    public static AuthorizeUser getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AuthorizeUser();

        return INSTANCE;
    }

    /**
     * Check if User is Logged in by searching his username in the list
     *
     * @param username the username string to check.
     * @return True- user is logged in. False- username is not logged in.
     */
    public boolean usernameExistsInLoggedInUsers(String username) {
        Set<Map.Entry<String, ConnectionToClient>> st = loggedInUsers.entrySet();
        for (Map.Entry<String, ConnectionToClient> u :
                st) {
            if (u.getKey().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get The count of logged in users
     *
     * @return number of users
     */
    public int getLoggedinUsertCount() {
        return this.loggedInUsers.size();
    }

    /**
     * delete user from logged in users list
     *
     * @param username username string to search and delete.
     */
    public void deleteUserByUsername(String username) {
        loggedInUsers.remove(username);
    }

    public void deleteUserByClient(ConnectionToClient client) {
        Set<Map.Entry<String, ConnectionToClient>> st = loggedInUsers.entrySet();
        for (Map.Entry<String, ConnectionToClient> u :
                st) {
            if (u.getValue().equals(client)) {
                loggedInUsers.remove(u);
            }
        }
    }
}
