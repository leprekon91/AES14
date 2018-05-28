package server.sql;

public class SQLContract {

	final public static String databaseURL = "jdbc:mysql://localhost/test";

    public static String user="root";
	public static String pass="Braude";

    public static final String USER_AUTH = "Select users.* from users\n" +
            "where username = '?' and password = '?';";

}
