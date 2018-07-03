package unittests;

import client.control.Client;
import com.data.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.control.Server;

import java.io.IOException;

public class ClientTest {
    private Server server = new Server(5555, null, new TestSQLI());

    /**
     * Run the server with an sql injection and a CLI setting.
     *
     * @throws Exception error while starting server.
     */
    @Before
    public void setUp() throws Exception {

        server = new Server(5555, null, new TestSQLI());
        server.listen();

    }

    /**
     * Test if the client can connect with the server.
     */
    @Test
    public void testConnectivityWithServer() {
        Client client = new Client("localhost", 5555);
        try {
            client.openConnection();
            Assert.assertTrue(client.isConnected());
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * test user's ability to login
     */
    @Test
    public void testSendingAuthMessage() {
        //send message for student authorization.

        Client client = new Client("localhost", 5555);
        User user = new User("t", "p2");
        client.requestAuth(user, TestAuthControl.getInstance());

        //wait for response
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //check that a user is logged in
        Assert.assertTrue(TestAuthControl.getInstance().userIsLoggedIn());

        //check that this is the correct user
        User expectedUser = new User("t", "p2", "John", "Teacher", 2);
        User actualUser = TestAuthControl.getInstance().getUser();
        Assert.assertEquals(expectedUser, actualUser);
        //close connection
        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test inability to login twice with the same user credentials
     */
    @Test
    public void testUserTriesToLoginTwice() {
        Client client = new Client("localhost", 5555);
        //login for the first time
        User user = new User("s", "p1");
        client.requestAuth(user, TestAuthControl.getInstance());

        //wait for response
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //login for the second time
        client.requestAuth(user, TestAuthControl.getInstance());

        //wait for response
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertNull(TestAuthControl.getInstance().getUser());

        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test for wrong password login attempt fail
     */
    @Test
    public void testWrongPasswordAttempt() {
        Client client = new Client("localhost", 5555);
        //Try login with wrong password
        User user = new User("p", "123");
        client.requestAuth(user, TestAuthControl.getInstance());
        //wait for response
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //check failure of login
        Assert.assertNull(TestAuthControl.getInstance().getUser());
        //tear down client
        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test for wrong username login attempt fail
     */
    @Test
    public void testWrongUsernameAttempt() {
        Client client = new Client("localhost", 5555);
        //Try login with wrong password
        User user = new User("username", "123");
        client.requestAuth(user, TestAuthControl.getInstance());
        //wait for response
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //check failure of login
        Assert.assertNull(TestAuthControl.getInstance().getUser());
        //tear down client
        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNullUsernameAttempt() {
        Client client = new Client("localhost", 5555);
        //Try login with wrong password
        User user = new User(null, "123");
        client.requestAuth(user, TestAuthControl.getInstance());
        //wait for response
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //check failure of login
        Assert.assertNull(TestAuthControl.getInstance().getUser());
        //tear down client
        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNullPasswordAttempt() {
        Client client = new Client("localhost", 5555);
        //Try login with wrong password
        User user = new User("t2", null);
        client.requestAuth(user, TestAuthControl.getInstance());
        //wait for response
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //check failure of login
        Assert.assertNull(TestAuthControl.getInstance().getUser());
        //tear down client
        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tear down the server
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        server.stopListening();
        server.close();
    }
}