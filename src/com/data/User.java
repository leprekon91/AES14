package com.data;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String pass;
    private int type;


    public User(String id, String pass) {
        this.id = id;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pass='" + pass + '\'' +
                ", type=" + type +
                '}';
    }
}
