package server.sql;

import com.data.User;

public interface SQLInjection {
    User getUser(String username, String password);
}
