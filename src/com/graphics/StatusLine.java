package com.graphics;




import com.Contract;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


public class StatusLine extends LineChart<Number,Number> {
    private NumberAxis xAxis;
    private NumberAxis yAxis;
      XYChart.Series series;
    final int MAX_NUM = 100;

    public StatusLine(int baseLine) {
        super(new NumberAxis(), new NumberAxis());
        xAxis = (NumberAxis) this.getXAxis();
        yAxis = (NumberAxis) this.getYAxis();
        series = new XYChart.Series();

        this.getData().add(series);
        this.getStylesheets().add(this.getClass().getResource(Contract.css).toExternalForm());
        this.setCreateSymbols(false);
        this.setAnimated(false);
        this.getXAxis().setTickLabelsVisible(false);
        this.getXAxis().setOpacity(0);
        this.setLegendVisible(false);
        this.setVerticalZeroLineVisible(false);
        this.setVerticalGridLinesVisible(false);

        //init dummy data
        for (int i = 0; i < MAX_NUM; i++) {
            series.getData().add(new XYChart.Data(i, baseLine));
        }
    }

    public void addValue(int value){
        shiftSeriesYValue(series,value);
    }

    //Shift all YValue
    //add the new item
    public void shiftSeriesYValue(XYChart.Series series, int newValue) {
        int numOfPoint = series.getData().size();
        for (int i = 0; i < numOfPoint - 1; i++) {
            XYChart.Data<Number, Number> ShiftDataUp =
                    (XYChart.Data<Number, Number>) series.getData().get(i + 1);
            Number shiftValue = ShiftDataUp.getYValue();
            XYChart.Data<Number, Number> ShiftDataDn =
                    (XYChart.Data<Number, Number>) series.getData().get(i);
            ShiftDataDn.setYValue(shiftValue);
        }
        XYChart.Data<Number, Number> lastData =
                (XYChart.Data<Number, Number>) series.getData().get(numOfPoint - 1);
        lastData.setYValue(newValue);
    }


}
