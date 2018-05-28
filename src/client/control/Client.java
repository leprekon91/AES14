package client.control;

import client.gui.fxcontrol.MainScreen;
import client.ocsf.AbstractClient;
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
    public MainScreen mainScreen;
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


    public void requestAuth(User user) {
        this.user = user;
        try {
            System.out.println("Sending request for login");
            this.sendToServer(new Message(Contract.AUTHORIZE, user));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recieveAuth(Message msg) {
        System.out.println("Authentication Received");
        this.mainScreen.wait.Continue((User) msg.getData());

    }
}
