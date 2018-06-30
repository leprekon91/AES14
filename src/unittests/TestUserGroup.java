package unittests;

import com.data.User;

import java.util.ArrayList;

public class TestUserGroup {
    private static TestUserGroup ourInstance = new TestUserGroup();
    private ArrayList<User> users = new ArrayList<>();

    private TestUserGroup() {
        users.add(new User("s", "p1", "John", "Student", 1));
        users.add(new User("t", "p2", "John", "Teacher", 2));
        users.add(new User("p", "p3", "John", "Principal", 3));
    }

    public static TestUserGroup getInstance() {
        return ourInstance;
    }

    public boolean contains(String username) {
        for (User u : this.users)
            if (u.getUsername().equals(username))
                return true;
        return false;
    }

    public User getUser(String username) {
        if (contains(username))
            for (User u : users)
                if (username.equals(u.getUsername()))
                    return u;
        return null;
    }
}
