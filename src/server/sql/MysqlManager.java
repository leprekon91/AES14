package server.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MySQL Manager Class. Creates connections and manages queries.
 */
public class MysqlManager {

    /**
     * Create Connection Instance To DataBase According to Contract
     *
     * @return Connection Object
     */
    public static Connection ConnectToDB() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            Connection con = DriverManager.getConnection(SQLContract.databaseURL, SQLContract.user, SQLContract.pass);

            System.out.println("SQL connection established");

            return con;
        } catch (SQLException ex) {
            sqlExceptionHandler(ex);
        }
        return null;
    }

    /**
     * Close connection To the DataBase
     *
     * @param con Connection to close
     */
    public static void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            sqlExceptionHandler(e);
        }
    }

    /**
     * Handles System Error Output for an SQL Exception
     *
     * @param ex SQLException to Handle
     */
    public static void sqlExceptionHandler(SQLException ex) {
        System.err.println("SQLException: " + ex.getMessage());
        System.err.println("SQLState: " + ex.getSQLState());
        System.err.println("VendorError: " + ex.getErrorCode());
    }

}
