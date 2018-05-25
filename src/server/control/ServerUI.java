package server.control;

public interface ServerUI {
    /**
     * Method that when overriden is used to display Error messages onto
     * a UI.
     */
    public abstract void logMsg(String str);
}
