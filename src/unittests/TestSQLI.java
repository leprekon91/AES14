package unittests;

import com.data.User;
import server.sql.SQLInjection;

public class TestSQLI implements SQLInjection {


    @Override
    public User getUser(String username, String password) {
        return TestUserGroup.getInstance().getUser(username);
    }
}
