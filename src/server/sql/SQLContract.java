package server.sql;

public class SQLContract {
   // jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
	final public static String databaseURL = "jdbc:mysql://localhost/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static String user="root";       //default database user
	public static String pass="Braude";     //default database password

    public static final String USER_AUTH = "SELECT * FROM users " +
            "WHERE user_name =?;";

}
