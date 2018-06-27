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
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        //inject SQL Dependency
        AuthorizeUser.getInstance().sqli = new TestSQLI();

    }

    /**
     * check if the user can be authorized. if his data is correct
     */
    @org.junit.Test
    public void authorizeUser() {
        System.out.println(getClass().getName() + ": TEST 1 - Log In User control test");
        //get actual user:
        Message actual = AuthorizeUser.getInstance().authorize("u", "p", null);
        //set up expected value
        User expectedUser = new User("a", "John", "Smith", 1);
        expectedUser.setPass("p");
        Message expectedMsg = new Message(Contract.AUTH_YES, expectedUser);
        //print all of it:
        System.out.println(expectedMsg);
        System.out.println("***");
        System.out.println(actual);
        //assert values
        Assert.assertTrue(expectedMsg.equals(actual));

    }
}