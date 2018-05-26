package server.gui.fxcontrol;

import com.graphics.DoughnutChart;
import com.graphics.StatusLine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.Random;

public class UserPanel {
    public Pane chartPane;
    public TableColumn tblUser;
    public TableColumn tblType;
    public TableColumn tblIp;
    public TableColumn tblName;
    public TableView table;
    public AnchorPane statusPane;
    public StatusLine statusLine;


    public void initialize() {

        ObservableList<PieChart.Data> pieChartData = createData(1, 1, 1);
        DoughnutChart chart = new DoughnutChart(pieChartData);

        chart.setLegendVisible(false);
        chart.setMaxWidth(400);
        chart.setMaxHeight(250);
        chartPane.getChildren().add(chart);

        //status line:
        statusLine = new StatusLine(0);
        statusLine.setTitle("User Stress Load");
        statusPane.getChildren().add(statusLine);
        setAnchorsFitScreen(statusLine);


        //fix table width
        tblUser.prefWidthProperty().bind(table.widthProperty().divide(4));
        tblType.prefWidthProperty().bind(table.widthProperty().divide(4));
        tblIp.prefWidthProperty().bind(table.widthProperty().divide(4));
        tblName.prefWidthProperty().bind(table.widthProperty().divide(4));
    }

    private ObservableList<PieChart.Data> createData(int sNum, int tNum, int pNum) {
        return FXCollections.observableArrayList(
                new PieChart.Data("Students\t0", sNum),
                new PieChart.Data("Teachers\t0", tNum),
                new PieChart.Data("Principal\t0", pNum));
    }

    private void updateUserData(int sNum, int tNum, int pNum){
        ObservableList<PieChart.Data> pieChartData = ((DoughnutChart) chartPane.getChildren().get(0)).getData();

        pieChartData.get(0).setPieValue(sNum);//students
        pieChartData.get(0).setName("Students\t"+sNum);

        pieChartData.get(1).setPieValue(tNum);//teachers
        pieChartData.get(1).setName("Teachers\t"+tNum);

        pieChartData.get(2).setPieValue(pNum);//principal
        pieChartData.get(2).setName("Principal\t"+pNum);

        statusLine.addValue(sNum+tNum+pNum);
    }

    private void setAnchorsFitScreen(Node n) {
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
    }
}
