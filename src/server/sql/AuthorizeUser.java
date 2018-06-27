package server.sql;

import com.Contract;
import com.data.Message;
import com.data.Student;
import com.data.Teacher;
import com.data.User;
import server.ocsf.ConnectionToClient;

import java.sql.*;
import java.util.ArrayList;
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
     * @param connectionToClient
     * @return Reply with Yes Or NO
     */
    public Message authorize(String username, String password, ConnectionToClient connectionToClient) {
        //Prepare Answer Message:
        Message ans = new Message(0, null);
        //check if the user is already logged in
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

    public String findUsernameByClient(ConnectionToClient client) {
        Set<Map.Entry<String, ConnectionToClient>> st = loggedInUsers.entrySet();
        for (Map.Entry<String, ConnectionToClient> u :
                st) {
            if (u.getValue().equals(client)) {
                return u.getKey();
            }
        }

        return null;
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

    public static void getAllTeachers(ArrayList<Teacher> teachers) {
        Connection con = MysqlManager.ConnectToDB();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQLContract.ALL_TEACHERS);

            while (rs.next()) {
                Teacher t = new Teacher(
                        new User(
                                rs.getString("user_name"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                2)
                );
                teachers.add(t);
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
    }

    public static void getAllStudents(ArrayList<Student> students) {
        Connection con = MysqlManager.ConnectToDB();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQLContract.ALL_STUDENTS);

            while (rs.next()) {
                Student t = new Student(
                        new User(
                                rs.getString("user_name"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                2)
                );
                students.add(t);
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            MysqlManager.sqlExceptionHandler(e);
        }
    }
}
