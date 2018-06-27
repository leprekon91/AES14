package unittests;

import com.data.User;
import server.sql.SQLInjection;

import java.util.ArrayList;

public class TestSQLI implements SQLInjection {

    ArrayList<User> u;

    @Override
    public User getUser(String username, String password) {
        User u = new User(username, "p");
        if (username == "u")

            u.setType(1);
        u.setFirst_name("John");
        u.setLast_name("Smith");
        return u;
    }
}
