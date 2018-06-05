package com.data;

public class Principal extends User {

    public Principal(String username, String pass) {
        super(username, pass);
    }

    /**
     * Convert Abstract user to principal
     * @param user
     */
    public Principal(User user) {
        super(user.getUsername(), user.getPass());
        this.setFirst_name(user.getFirst_name());
        this.setLast_name(user.getLast_name());
        this.setType(user.getType());
    }
}
