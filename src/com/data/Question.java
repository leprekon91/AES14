package com.data;

import java.io.Serializable;

/**
 * Stub Entity Class represents a Question *for testing purposes only!*
 */
public class Question implements Serializable {
    private int id;

    //TODO this is a STUB Class!
    public Question(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Question Stub Class! id=" + this.id;
    }

    public int getQID() {
        return id;
    }

    public String getText() {
        return "Question " + this.id + " - Text";
    }

    public String getAns(int ansIndex) {
        return "Ans " + ansIndex;
    }

    public int getIndicator() {
        return id;
    }

    public String getAuthor() {
        return "Author";
    }
}
