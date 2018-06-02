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
        super(user.getId(), user.getPass());
        this.setType(user.getType());
    }
}
