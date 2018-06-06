package client.gui.fxcontrol;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class PrincipalMenu {
    public void openExampleReport(ActionEvent actionEvent) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("a", 6);
        hashMap.put("b", 0);
        hashMap.put("c", 9);
        hashMap.put("d", 2);
        hashMap.put("e", 4);
        hashMap.put("f", 7);
        hashMap.put("g", 3);
        hashMap.put("h", 7);
        try {
            Report.openReport(new Stage(), hashMap, "Title", "x values", "y values");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
