package server.control;

import com.Contract;
import com.data.Message;
import com.data.User;
import server.ocsf.AbstractServer;
import server.ocsf.ConnectionToClient;
import server.sql.AuthorizeUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018
 * <p>
 * Server implementation. This class will handle all communication with
 * the clients, using the language defined in the com.Contract class file.
 */
public class Server extends AbstractServer {

    private ServerUI SUI;

    public Server(int port, ServerUI sui) {
        super(port);
        this.SUI = sui;
    }

    /**
     * This method overrides the one in the superclass.  Called
     * when the server starts listening for connections.
     */
    protected void serverStarted() {
        SUI.logMsg
                ("Server listening for connections on port " + getPort());
    }

    /**
     * This method overrides the one in the superclass.  Called
     * when the server stops listening for connections.
     */
    protected void serverStopped() {
        SUI.logMsg("Server has stopped listening for connections.");
    }

    /**
     * This method overrides the one in the superclass.  Called
     * when a client has connected to the server
     */
    @Override
    protected void clientConnected(ConnectionToClient client) {
        SUI.logMsg("Client " + client.toString() + " connected.");
    }

    @Override
    protected synchronized void clientException(ConnectionToClient client, Throwable e) {
        super.clientException(client, e);
        SUI.logMsg("Client: "+client.toString()+" has encountered an exception!");
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        SUI.logMsg(exceptionAsString);
    }

    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        super.clientDisconnected(client);
        SUI.logMsg("Client " + client.toString() + " disconnected.");
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        if (msg instanceof Message ) {
            SUI.logMsg("Message received from Client: " + client.toString()
                    + " Message:\n" +
                    "\ttype:" + ((Message) msg).getType()
            );
            AuthorizeUser authorizeUser = AuthorizeUser.getInstance();
            authorizeUser.connectionToClient=client;
            int contractType = ((Message) msg).getType();
            try {
                switch (contractType) {
                    case Contract.AUTHORIZE: //Client Requests Authorization

                        User u = (User) ((Message) msg).getData();
                        Message authResponse = authorizeUser.authorize(u.getUsername(), u.getPass());
                        client.sendToClient(authResponse);
                        break;
                    case Contract.LOG_OFF:  //client requests logoff - delete client from list
                        if (authorizeUser.usernameExistsInLoggedInUsers(((User) ((Message) msg).getData()).getUsername())) {
                            authorizeUser.deleteUserByUsername(((User) ((Message) msg).getData()).getUsername());

                        }
                        break;
                }
            } catch (IOException e) {
                SUI.logMsg(e.getMessage());
            }

        } else {
            SUI.logMsg("Client" + client.toString() + "\tsent an unidentifiable message.");
        }

    }


}
