package server.control;

import server.ocsf.ConnectionToClient;

public interface ServerUI {
    /**
     * Method that when overriden is used to display Error messages onto
     * a UI.
     */
    public abstract void logMsg(String str);

    public abstract void updateClients(int numOfClients);
}
