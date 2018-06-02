package com.data;

public class Student extends User {
    public Student(String username, String pass) {
        super(username, pass);
    }

    /**
     * Convert Abstract user to student
     * @param user
     */
    public Student(User user) {
        super(user.getId(), user.getPass());
        this.setType(user.getType());
    }
}
