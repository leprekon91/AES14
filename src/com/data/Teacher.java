package com.data;

public class Teacher extends User {
    public Teacher(String username, String pass) {
        super(username, pass);
    }

    /**
     * Convert Abstract user to teacher
     * @param user
     */
    public Teacher(User user) {
        super(user.getId(), user.getPass());
        this.setType(user.getType());
    }
}
