package client.gui.fxcontrol;

import com.Contract;
import com.style.icons.FontAwesome;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
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
    private XYChart.Series series;
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
        ((Report) fxmlLoader.getController()).createData("Report", data);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Report.class.getResource(Contract.css).toExternalForm());
        primaryStage.setTitle("Report");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void createData(String dataName, int[] data) {
        series = new XYChart.Series();
        series.setName(dataName);
        int[] dataMap = generateData(data);

        if (dataMap.length != 0) {
            series.getData().add(new XYChart.Data<>("0-9", dataMap[0]));
            series.getData().add(new XYChart.Data<>("10-19", dataMap[1]));
            series.getData().add(new XYChart.Data<>("20-29", dataMap[2]));
            series.getData().add(new XYChart.Data<>("30-39", dataMap[3]));
            series.getData().add(new XYChart.Data<>("40-49", dataMap[4]));
            series.getData().add(new XYChart.Data<>("50-59", dataMap[5]));
            series.getData().add(new XYChart.Data<>("60-69", dataMap[6]));
            series.getData().add(new XYChart.Data<>("70-79", dataMap[7]));
            series.getData().add(new XYChart.Data<>("80-89", dataMap[8]));
            series.getData().add(new XYChart.Data<>("90-100", dataMap[9]));
        } else {
            series.getData().add(new XYChart.Data<>("0-9", 0));
            series.getData().add(new XYChart.Data<>("10-19", 0));
            series.getData().add(new XYChart.Data<>("20-29", 0));
            series.getData().add(new XYChart.Data<>("30-39", 0));
            series.getData().add(new XYChart.Data<>("40-49", 0));
            series.getData().add(new XYChart.Data<>("50-59", 0));
            series.getData().add(new XYChart.Data<>("60-69", 0));
            series.getData().add(new XYChart.Data<>("70-79", 0));
            series.getData().add(new XYChart.Data<>("80-89", 0));
            series.getData().add(new XYChart.Data<>("90-100", 0));
        }
        barChart.getData().add(series);


        barChart.setTitle(chartTitle);

        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);

        lblAvg.setText("Average - " + avg);
        lblMed.setText("Median - " + med);
    }

    public int[] generateData(int[] data) {
        if (data.length == 0) {
            return data;
        }
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

    public void saveReport(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files (.csv)", ".csv"));
        File file = fileChooser.showSaveDialog(averageIcon.getScene().getWindow());

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(' ');
            fileWriter.append(',');
            fileWriter.append("Category");
            fileWriter.append(',');
            fileWriter.append("# of Grades");
            fileWriter.append('\n');

            for (int i = 0; i < series.getData().size(); i++) {
                fileWriter.append(' ');
                fileWriter.append(',');
                XYChart.Data<String, Integer> data;
                data = (XYChart.Data<String, Integer>) series.getData().get(i);
                fileWriter.append(data.getXValue());
                fileWriter.append(',');
                fileWriter.append(String.valueOf(data.getYValue()));
                fileWriter.append('\n');

            }

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
