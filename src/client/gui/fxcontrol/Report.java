package client.gui.fxcontrol;

import com.Contract;
import com.style.icons.FontAwesome;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Report {

    public BarChart barChart;
    public NumberAxis yAxis;
    public CategoryAxis xAxis;
    public String chartTitle;
    public String xLabel;
    public String yLabel;
    public Label lblAvg;
    public Label lblMed;

    public Label medianIcon;
    public Label averageIcon;


    private XYChart.Series series;
    private double average = 0;
    private double median = 0;

    public static void openReport(
            Stage primaryStage,
            HashMap<String, Integer> data,
            String chartTitle,
            String xLabel,
            String yLabel) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Report.class.getResource(Contract.clientFXML + "Report.fxml"));
        Parent root = fxmlLoader.load();
        ((Report) fxmlLoader.getController()).chartTitle = chartTitle;
        ((Report) fxmlLoader.getController()).xLabel = xLabel;
        ((Report) fxmlLoader.getController()).yLabel = yLabel;
        ((Report) fxmlLoader.getController()).createData("Example", data);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Report.class.getResource(Contract.css).toExternalForm());
        primaryStage.setTitle("Report");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void createData(String dataName, HashMap<String, Integer> dataMap) {
        series = new XYChart.Series();
        series.setName(dataName);
        ArrayList<Integer> medianArray = new ArrayList<>();
        Set<Map.Entry<String, Integer>> st = dataMap.entrySet();

        for (Map.Entry mapEntry : st
                ) {
            String label = (String) mapEntry.getKey();
            int value = (int) mapEntry.getValue();
            series.getData().add(new XYChart.Data<String, Integer>(label, value));
            average += value;
            medianArray.add(value);
        }

        medianArray.sort(Integer::compareTo);
        if (medianArray.size() % 2 == 1)
            median = medianArray.get(((medianArray.size() + 1) / 2) - 1);
        else {
            double median1 = medianArray.get((medianArray.size() / 2) - 1);
            double median2 = medianArray.get(medianArray.size() / 2);
            median = (median1 + median2) / 2;
        }
        average /= st.size();
        barChart.getData().add(series);
        lblAvg.setText("Average: " + average);
        lblMed.setText("Median: " + median);


        barChart.setTitle(chartTitle);
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
    }

    public void initialize() {
        averageIcon.setText(FontAwesome.ICON_ALIGN_CENTER);
        medianIcon.setText(FontAwesome.ICON_PLUS);

        averageIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
        medianIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));


    }
}
