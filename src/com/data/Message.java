package com.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Message data type, used in communication between the server and the
 * client. will define the type as Contract enum.
 */
public class Message implements Serializable {
    // Parameters:

    private int type;
    private Object data;

    // Parameters -END

    /**
     * Constructor for Message Object
     *
     * @param type Type of message, must be defined by com.Contract
     * @param data If needed, can be appended to the message as data sent to
     *             recipient.
     */
    public Message(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    // Getters and Setters:

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    // Getters and Setters - END


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return getType() == message.getType() &&
                Objects.equals(getData(), message.getData());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getType(), getData());
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }
}
