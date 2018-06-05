package com.data;

import java.io.Serializable;

/**
 * Stub Entity Class represents a Question *for testing purposes only!*
 */
public class Question implements Serializable {
    private int id;

    public Question(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Question Stub Class! id="+this.id;
    }
}
