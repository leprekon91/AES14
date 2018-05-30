package server.sql;

public class SQLContract {

	final public static String databaseURL = "jdbc:mysql://localhost/test";

    public static String user="root";       //default database user
	public static String pass="Braude";     //default database password

    public static final String USER_AUTH = "SELECT * FROM USER " +
            "WHERE username =?;";

}
