package com.data;

import java.io.Serializable;

public class User implements Serializable {
    /**
     * User entity additional fields
     */
    private String username;
    private String pass;
    private String first_name;
    private String last_name;
    private int type;

    /**
     * User entity constructors
     */
    public User(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }

    public User(String username, String first_name, String last_name, int type) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.type = type;
    }

    /**
     * User entity getters and setters
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isAuthenticated() {
        return !(this.getType() == 0);
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name.substring(0, 1).toUpperCase()
                + last_name.substring(1).toLowerCase();
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name.substring(0, 1).toUpperCase()
                + first_name.substring(1).toLowerCase();
    }

    public String getFullName() {
        return this.first_name + " " + this.last_name;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}