package client.gui.fxcontrol;

import com.Contract;
import com.data.User;

public class Wait {
    /**
     * define which screen to go to
     *
     * @param u user objects defines the path to go.
     */
    public void Continue(User u) {
        switch (u.getAuth()) {
            case Contract.AUTH_YES:
                System.out.println("User Authorized");
                break;
            case Contract.AUTH_NO:
                System.out.println("User not Authorized");
                break;
            default:
                System.out.println("User Failed To Auth");
                break;
        }
    }
}
