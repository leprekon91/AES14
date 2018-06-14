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
import java.util.Arrays;

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

    double avg = 0;
    double med = 0;

    public static void openReport(
            Stage primaryStage,
            int[] data,
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

    public void createData(String dataName, int[] data) {
        XYChart.Series series = new XYChart.Series();
        series.setName(dataName);
        int[] dataMap = generateData(data);


        series.getData().add(new XYChart.Data<>("0-9", dataMap[0]));
        series.getData().add(new XYChart.Data<>("10-19", dataMap[0]));
        series.getData().add(new XYChart.Data<>("20-29", dataMap[0]));
        series.getData().add(new XYChart.Data<>("30-39", dataMap[0]));
        series.getData().add(new XYChart.Data<>("40-49", dataMap[0]));
        series.getData().add(new XYChart.Data<>("50-59", dataMap[0]));
        series.getData().add(new XYChart.Data<>("60-69", dataMap[0]));
        series.getData().add(new XYChart.Data<>("70-79", dataMap[0]));
        series.getData().add(new XYChart.Data<>("80-89", dataMap[0]));
        series.getData().add(new XYChart.Data<>("90-100", dataMap[0]));
        barChart.getData().add(series);


        barChart.setTitle(chartTitle);

        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);

        lblAvg.setText("Average - " + avg);
        lblMed.setText("Median - " + med);
    }

    public int[] generateData(int[] data) {
        int[] cnts = new int[10];
        int sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
            if (data[i] / 10 == 10) cnts[9]++;
            else cnts[data[i] / 10]++;
        }
        //calculate
        Arrays.sort(data);
        if (data.length % 2 == 0)
            med = ((double) data[data.length / 2] + (double) data[data.length / 2 - 1]) / 2;
        else
            med = (double) data[data.length / 2];
        avg = sum / data.length;
        return cnts;
    }

    public void initialize() {
        averageIcon.setText(FontAwesome.ICON_ALIGN_CENTER);
        medianIcon.setText(FontAwesome.ICON_ALIGN_JUSTIFY);

        averageIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));
        medianIcon.setFont(FontAwesome.getFont(FontAwesome.SOLID));


    }
}
