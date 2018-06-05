package client.control;

import client.ocsf.AbstractClient;
import com.Contract;
import com.data.Message;
import com.data.Question;
import com.data.Student;
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


    public User user;
    private AuthControl authControl;//reference for authentication controller

    /**
     * Client constructor, opens connection to server once he is created.
     *
     * @param host
     * @param port
     */
    public Client(String host, int port) {
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
                receiveAuth(message);
                break;
            case Contract.QUESTION: //Question Object was received.
                Question question = (Question) ((Message) msg).getData();
                System.out.println("Question Object was received: " + question);
                break;
        }

    }

    @Override
    protected void connectionException(Exception exception) {
        super.connectionException(exception);
        exception.printStackTrace();
    }

    @Override
    protected void connectionClosed() {
        super.connectionClosed();
        System.out.println("Closed Connection!");
    }

    /**
     * Request Authentication from server for a specific user.
     * Also, save the reference to the class that asked for it.
     *
     * @param user
     * @param authControl
     */
    public void requestAuth(User user, AuthControl authControl) {
        this.authControl = authControl;
        try {
            System.out.println("Sending request for login of User: " + user);
            this.sendToServer(new Message(Contract.AUTHORIZE, user));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recieve and apply Auth. reply Message from Server
     * through authControl.
     *
     * @param msg Message recieved from server.
     */
    public void receiveAuth(Message msg) {
        System.out.println("Authentication Reply Received");

        authControl.receiveAuthenticationAnswer((User) msg.getData());
    }

}
