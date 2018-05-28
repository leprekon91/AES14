package server.control;

import com.Contract;
import com.data.Message;
import com.data.User;
import server.ocsf.AbstractServer;
import server.ocsf.ConnectionToClient;
import server.sql.AuthorizeUser;
import server.sql.MysqlManager;

import java.io.IOException;
import java.sql.Connection;

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
        //TODO: update GUI
        SUI.updateClients(this.getNumberOfClients());
        System.out.println(this.getNumberOfClients());
    }

    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        super.clientDisconnected(client);
        SUI.logMsg("Client " + client.toString() + " disconnected.");
        SUI.updateClients(this.getNumberOfClients());
        System.out.println("client disconnected");
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        if (msg instanceof Message) {
            SUI.logMsg("Message received from Client: " + client.toString()
                    + " Message:\n" +
                    "\ttype:" + ((Message) msg).getType() + "\n" +
                    "\tDATA: " + ((Message) msg).getData().toString()
            );
            if(((Message) msg).getType()==Contract.AUTHORIZE){
                authorizeUser((User)((Message) msg).getData());
            }

        } else {
            SUI.logMsg("Client" + client.toString() + "\tsent an unidentifiable message.");
        }

    }

    private void authorizeUser(User data) {
        AuthorizeUser.authorize(data);
    }


}
