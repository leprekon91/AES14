package server.control;

import server.ocsf.AbstractServer;
import server.ocsf.ConnectionToClient;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018
 * 
 *       Server implementation. This class will handle all communication with
 *       the clients, using the language defined in the com.Contract class file.
 */
public class Server extends AbstractServer {

	public Server(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
