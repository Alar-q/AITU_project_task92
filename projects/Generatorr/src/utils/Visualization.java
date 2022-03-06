package utils;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

import java.util.List;

import static utils.LabelsUtil.getLabelByID;

public class Visualization {
    public static final int WIDTH = 800, HEIGHT = 600;

    /** dTime между значениями одинаковое */
    public static void showLabels(List<Float> vals, List<Integer> labels, String title, boolean inDots){
        Series[] series = new Series[4];
        for(int i=0; i<4; i++) {
            series[i] = new Series();
            series[i].setName(getLabelByID(i).toString());
        }

        for (int i=0; i<vals.size(); i++) {
            Data data = new Data(i, vals.get(i));
            series[labels.get(i)].getData().add(data);
        }
        show(series, title, inDots);
    }
    public static void showLabels(List<Float> vals, List<Integer> labels, String title){
        showLabels(vals, labels, title, false);
    }
    /** dTime между значениями одинаковое */
    public static void show(List<Float> vals, String title){
        Series series = new Series();

        for (int i=0; i<vals.size(); i++) {
            Data data = new Data(i, vals.get(i));
            series.getData().add(data);
        }
        show(series, title);
    }


    public static void show(Series[] series, String title, boolean inDots){
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        XYChart chart;
        if(inDots)
            chart = new ScatterChart(xAxis, yAxis);
        else chart = new LineChart(xAxis, yAxis);

        chart.getData().addAll(series);

        chart.setPrefWidth(WIDTH);
        chart.setPrefHeight(HEIGHT);

        Stage window = new Stage();
        Group group = new Group(chart);
        Scene scene = new Scene(group, WIDTH, HEIGHT);
        window.setScene(scene);
        window.setTitle(title);
        window.show();
    }
    public static void show(Series series, String title){
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart chart = new LineChart(xAxis, yAxis);
        //chart.setCreateSymbols(false);

        chart.getData().addAll(series);

        chart.setPrefWidth(WIDTH);
        chart.setPrefHeight(HEIGHT);

        Stage window = new Stage();
        Group group = new Group(chart);
        Scene scene = new Scene(group, WIDTH, HEIGHT);
        window.setScene(scene);
        window.setTitle(title);
        window.show();
    }

}

