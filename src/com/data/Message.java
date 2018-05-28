package com.data;

import java.io.Serializable;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018
 * 
 *       Message data type, used in communication between the server and the
 *       client. will define the type as Contract enum.
 */
public class Message implements Serializable {
	// Parameters:

	private int type;
	private Object data;

	// Parameters -END

	/**
	 * Constructor for Message Object
	 * 
	 * @param type
	 *            Type of message, must be defined by com.Contract
	 * @param data
	 *            If needed, can be appended to the message as data sent to
	 *            recipient.
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
}
