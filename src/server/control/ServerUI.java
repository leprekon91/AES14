package server.control;


import server.gui.fxcontrol.Dashboard;

public interface ServerUI {

    /**
     * Method that when overriden is used to display Error messages onto
     * a UI.
     */
    void logMsg(String str);


    void updateClients(int numOfClients);

}
