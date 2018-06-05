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
        super(user.getUsername(), user.getPass());
        this.setFirst_name(user.getFirst_name());
        this.setLast_name(user.getLast_name());
        this.setType(user.getType());
    }
}
