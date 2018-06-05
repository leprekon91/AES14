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

    /**
     * This method overrides the one in the superclass.  Called
     * * when a connectionToClient thread has encountered an exception.
     *
     * @param client the client that raised the exception.
     * @param e      exception that was thrown
     */
    @Override
    protected synchronized void clientException(ConnectionToClient client, Throwable e) {
        super.clientException(client, e);
        SUI.logMsg("Client: " + client.toString() + " has encountered an exception!");
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        SUI.logMsg(exceptionAsString);
    }

    /**
     * This method overrides the one in the superclass.  Called
     * when a client has disconnected from the server
     */
    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        super.clientDisconnected(client);
        SUI.logMsg("Client " + client.toString() + " disconnected.");
    }

    /**
     * message from client handler
     *
     * @param msg    the message sent.
     * @param client the connection connected to the client that sent the message
     */
    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        if (msg instanceof Message) {
            SUI.logMsg("Message received from Client: " + client.toString()
                    + " Message:\n" +
                    "\ttype:" + ((Message) msg).getType()
            );
            AuthorizeUser authorizeUser = AuthorizeUser.getInstance();
            authorizeUser.connectionToClient = client;
            int contractType = ((Message) msg).getType();
            //------------------------Message decode--------------------------------------------------------------------
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
                    case Contract.CREATE_EXAM:              //Create a New Exam
                    case Contract.READ_EXAM:                //Read Exam Object by ID
                    case Contract.UPDATE_EXAM:              //Update an existing Exam
                    case Contract.DELETE_EXAM:              //Delete an existing Exam
                    case Contract.GET_EXAMS_BY_COURSE:      //Get Exams in a specific course
                    case Contract.GET_EXAMS_BY_SUBJECT:     //Get Exams in a specific Subject
                        break;
                    case Contract.CREATE_QUESTION:          //Create a new Question
                    case Contract.READ_QUESTION:            //Read an existing question By ID
                    case Contract.UPDATE_QUESTION:          //Update an existing Question
                    case Contract.DELETE_QUESTION:          //Delete Question from A database
                    case Contract.GET_QUESTIONS_BY_EXAM:    //Get all questions in a specific Exam
                    case Contract.GET_QUESTIONS_BY_SUBJECT: //Get Questions in a specific Subject
                    case Contract.GET_QUESTIONS_BY_TEACHER: //Get Questions written
                        break;
                    case Contract.GET_COURSE:               //Get Course details
                    case Contract.GET_SUBJECT:              //Get Subject details
                        break;
                    case Contract.GET_GRADES_BY_EXAM:       //Get Grades of an exam
                    case Contract.GET_GRADES_BY_STUDENT:    //Get grades of a specific student
                        break;

                }
            } catch (IOException e) {
                SUI.logMsg(e.getMessage());
            }
            //----------------------------------------------------------------------------------------------------------
        } else {
            SUI.logMsg("Client" + client.toString() + "\tsent an unidentifiable message.");
        }

    }

    protected void messageDecode(Message msg) {

    }
}
