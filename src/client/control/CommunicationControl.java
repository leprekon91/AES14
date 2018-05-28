package client.control;

import com.data.Message;
import javafx.scene.Parent;

/**
 * This class will control all communication between the gui and the client.
 */
public class CommunicationControl {
    private Parent parentScene;
    private Client client;

    public CommunicationControl(Parent parentScene) {
        this.parentScene = parentScene;
    }

    public Parent getParentScene() {
        return parentScene;
    }

    public void setParentScene(Parent parentScene) {
        this.parentScene = parentScene;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) throws Exception {
        if(client==null){
            throw new Exception("Client is null!");
        }
        this.client = client;
    }

    //-----------------------------------------------------------------------------
    //main communication method
    public void handleMessage(Message msg){

    }

}
