package client.control;

import com.data.User;

public interface AuthorizationResponse {
    void receiveAuthenticationAnswer(User user);
}
