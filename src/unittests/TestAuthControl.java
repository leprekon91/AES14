package unittests;

import client.control.AuthorizationResponse;
import com.data.User;

/**
 * AuthControl Injection class implementation. using this for unit tests instead of the usual javaFX threaded control
 */
public class TestAuthControl implements AuthorizationResponse {
    private static TestAuthControl INSTANCE;

    private User user;

    public static TestAuthControl getInstance() {
        if (INSTANCE == null) INSTANCE = new TestAuthControl();
        return INSTANCE;
    }

    @Override
    public void receiveAuthenticationAnswer(User user) {
        this.user = user;
    }

    public boolean userIsLoggedIn() {
        return (this.user != null);
    }

    public User getUser() {
        return this.user;
    }
}
