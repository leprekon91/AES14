package server.control;


/**
 * Interface for logging messages from server system.
 */
public interface ServerUI {

    /**
     * Method that when overriden is used to display Error messages onto
     * a UI.
     */
    void logMsg(String str);

}
