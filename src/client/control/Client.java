package client.control;

import client.ocsf.AbstractClient;
import client.ocsf.ObservableClient;
import com.Contract;
import com.data.Message;
import com.data.User;

import java.io.IOException;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018
 * <p>
 * Client implementation. This class will handle all communication with
 * the server, using the language defined in the com.Contract class file.
 */
public class Client extends AbstractClient {

    private User user;


    public Client(String host, int port, Client cc) {
        super(host, port);

        try {
            openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        // TODO handle message from server
        if (!(msg instanceof Message)) {
            System.out.println("Server sent an unidentifiable message!");
        }
        Message message = (Message) msg;
        switch (message.getType()) {
            //Authorization Messages:
            case Contract.AUTH_YES:
            case Contract.AUTH_NO:
                recieveAuth(message);
                break;
        }

    }

    @Override
    protected void connectionClosed() {
        super.connectionClosed();
        System.out.println("Closed Connection!");
    }

    /**
     * Request Authentication from server for a specific user.
     * @param user
     */
    public void requestAuth(User user) {
        this.user = user;
        try {
            System.out.println("Sending request for login");
            this.sendToServer(new Message(Contract.AUTHORIZE, user));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recieve and apply Auth. Message (ANSWER) from Server
     * @param msg Message recieved from server.
     */
    public void recieveAuth(Message msg) {
        System.out.println("Authentication Received");
        //TODO method stub

    }

}
