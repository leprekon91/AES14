package server.control;

import com.data.ExtensionRequest;

import java.util.ArrayList;

public class RequestManager {
    private static RequestManager ourInstance = new RequestManager();
    public ArrayList<ExtensionRequest> requests;

    public static RequestManager getInstance() {
        return ourInstance;
    }


    private RequestManager() {
        this.requests = new ArrayList<>();
    }

    public void removeRequest(ExtensionRequest er) {
        for (ExtensionRequest request :
                this.requests) {
            if (request.equals(er)) this.requests.remove(request);
        }
    }
}
