package unittests;

import com.Contract;
import com.data.Message;
import com.data.User;
import org.junit.Assert;
import org.junit.Before;
import server.sql.AuthorizeUser;

public class AuthorizeUserTest {


    /**
     * Set up Method
     *
     */
    @Before
    public void setUp() {
        //inject SQL Dependency
        AuthorizeUser.getInstance().sqli = new TestSQLI();

    }

    /**
     * check if the user can be authorized, if his data is correct.
     */
    @org.junit.Test
    public void authorizeUser() {
        System.out.println(getClass().getName() + ": TEST 1 - Log In User control test");
        //get actual user:
        Message actual = AuthorizeUser.getInstance().authorize("s", "p1", null);
        //set up expected value
        User expectedUser = new User("s", "John", "Student", 1);
        expectedUser.setPass("p1");
        Message expectedMsg = new Message(Contract.AUTH_YES, expectedUser);
        //print all of it:
        System.out.println(expectedMsg);
        System.out.println("***");
        System.out.println(actual + "\n");
        //assert values
        Assert.assertEquals(expectedMsg, actual);

    }

    /**
     * check if the user can be authorized, if his password is wrong.
     */
    @org.junit.Test
    public void authorizeUser_WrongPassword() {
        System.out.println(getClass().getName() + ": TEST 2 - Log In User with wrong password");
        //get actual user:
        Message actual = AuthorizeUser.getInstance().authorize("t", "12345", null);
        //set up expected value
        User expectedUser = new User("t", "John", "Teacher", 2);
        expectedUser.setPass("p2");
        Message expectedMsg = new Message(Contract.AUTH_NO, expectedUser);
        //print all of it:
        System.out.println(expectedMsg);
        System.out.println("***");
        System.out.println(actual + "\n");
        //assert values
        Assert.assertEquals(expectedMsg, actual);

    }


    /**
     * check if the user can be authorized,  if the username doesn't exist.
     */
    @org.junit.Test
    public void authorizeUser_WrongUsername() {
        System.out.println(getClass().getName() + ": TEST 3 - Log In User with wrong user name");
        //get actual user:
        Message actual = AuthorizeUser.getInstance().authorize("Alex", "p3", null);
        //set up expected value
        Message expectedMsg = new Message(Contract.AUTH_NO, null);
        //print all of it:
        System.out.println(expectedMsg);
        System.out.println("***");
        System.out.println(actual + "\n");
        //assert values
        Assert.assertEquals(expectedMsg, actual);

    }


    /**
     * check if the user can be authorized,  if the username already logged in.
     */
    @org.junit.Test
    public void authorizeUser_UserAlreadyLoggedIn() {
        System.out.println(getClass().getName() + ": TEST 4 - Log In User with already logged in user name");
        //get actual user:
        Message first = AuthorizeUser.getInstance().authorize("t2", "p4", null);
        User expectedUser = new User("t2", "John", "Teacher", 2);
        expectedUser.setPass("p4");
        Message expectedMsg = new Message(Contract.AUTH_YES, expectedUser);
        System.out.println(expectedMsg);
        System.out.println("***");
        System.out.println(first + "\n");
        //assert values
        Assert.assertEquals(expectedMsg, first);

        //try to connect with the same user again
        Message second = AuthorizeUser.getInstance().authorize("t2", "p4", null);
        //set up expected value
        Message expectedMsg2 = new Message(Contract.AUTH_NO, null);
        //print all of it:
        System.out.println(expectedMsg2);
        System.out.println("***");
        System.out.println(second + "\n");
        //assert values
        Assert.assertEquals(expectedMsg2, second);

    }


    /**
     * check if the user can be authorized, if he left the username field blank (null received as username)
     */
    @org.junit.Test
    public void authorizeUser_NullUserName() {
        System.out.println(getClass().getName() + ": TEST 5 - Log In User with null username ");
        //get actual user:
        Message actual = AuthorizeUser.getInstance().authorize(null, "p2", null);
        //set up expected value
        User expectedUser = null;
        Message expectedMsg = new Message(Contract.AUTH_NO, expectedUser);
        //print all of it:
        System.out.println(expectedMsg);
        System.out.println("***");
        System.out.println(actual + "\n");
        //assert values
        Assert.assertEquals(expectedMsg, actual);

    }

    /**
     * check if the user can be authorized, if he left the password field blank (null received as password)
     */
    @org.junit.Test
    public void authorizeUser_NullPassword() {
        System.out.println(getClass().getName() + ": TEST 6 - Log In User with null password ");
        //get actual user:
        Message actual = AuthorizeUser.getInstance().authorize("t", null, null);
        //set up expected value
        User expectedUser = new User("t", "John", "Teacher", 2);
        expectedUser.setPass("p2");

        Message expectedMsg = new Message(Contract.AUTH_NO, expectedUser);
        //print all of it:
        System.out.println(expectedMsg);
        System.out.println("***");
        System.out.println(actual + "\n");
        //assert values
        Assert.assertEquals(expectedMsg, actual);

    }
}