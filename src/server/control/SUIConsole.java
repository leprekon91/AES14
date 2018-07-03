package server.control;

/**
 * SUI Console - Console interface for the server instead the usual JavaFX UI Package.
 * Used in jUnit tests for comfort of output.
 */
public class SUIConsole implements ServerUI {
    @Override
    public void logMsg(String str) {
        System.out.println("[Server].Message: " + str);
    }
}
