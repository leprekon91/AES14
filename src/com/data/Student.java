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
        super(user.getUsername(), user.getPass());
        this.setFirst_name(user.getFirst_name());
        this.setLast_name(user.getLast_name());
        this.setType(user.getType());
    }
}
