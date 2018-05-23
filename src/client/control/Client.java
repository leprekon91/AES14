package client.control;

import com.data.Message;

import client.ocsf.AbstractClient;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018
 * 
 *       Client implementation. This class will handle all communication with
 *       the server, using the language defined in the com.Contract class file.
 */
public class Client extends AbstractClient {

	public Client(String host, int port) {
		super(host, port);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		// TODO handle message from server
		if (!(msg instanceof Message)) {
			System.out.println("Server sent an unidentifiable message!");
		}

	}
}
