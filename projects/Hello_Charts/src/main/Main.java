package main;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import static main.Main.Label.*;
import static main.Main.Label.UP;
import static main.Math2.randVal;

public class Main {
    public static final int WIDTH = 1000, HEIGHT = 800;

    public enum Label {
        UP,         // Ход графика вверх с шумом
        DOWN,       // Ход графика вниз с шумом
        STAGNATION, // Абсолютно без изменений
        OTHER;      // остается в общем на каком-то уровне, но с шумом
    }


    public static int getLabelID(Label label){
        if(label == UP)
            return 0;
        else if(label == DOWN)
            return 1;
        else if(label == STAGNATION)
            return 2;
        else // label == OTHER
            return 3;
    }
    public static int getLabelID(String label){
        if(label.equals("UP"))
            return 0;
        else if(label.equals("DOWN"))
            return 1;
        else if(label.equals("STAGNATION"))
            return 2;
        else // label == OTHER
            return 3;
    }

    public static Label getLabelByID(int id){
        if(id == 0)
            return UP;
        else if(id == 1)
            return DOWN;
        else if(id == 2)
            return STAGNATION;
        else // label == OTHER
            return OTHER;
    }

    public static Label randLabel(){
        int type = (int) randVal(0, 4);
        if(type == 0)
            return UP;
        else if(type == 1)
            return Label.DOWN;
        else if(type == 2)
            return Label.STAGNATION;
        else
            return Label.OTHER;
    }

    public static void show(XYChart.Series[] series, String title){
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        AreaChart linechart = new AreaChart(xAxis, yAxis);
        //Setting the data to Line chart
        for(XYChart.Series s: series) {
            linechart.getData().add(s);
            linechart.setPickOnBounds(true);
            linechart.setCreateSymbols(false);
        }

        linechart.setPrefWidth(WIDTH);
        linechart.setPrefHeight(HEIGHT);
        Stage window = new Stage();
        Group group = new Group(linechart);
        Scene scene = new Scene(group, WIDTH, HEIGHT);
        window.setScene(scene);
        window.setTitle(title);
        window.show();
    }

}