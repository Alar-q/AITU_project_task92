package main;

import javafx.application.Application;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static main.Main.getLabelByID;
import static main.Main.show;
import static main.Math2.parseFloat;

public class Visualization extends Application {
    public static void main(String args[]){
        launch(args);
    }

    public static String path = "C:\\Java\\1.csv";

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(path));

        List<Float> vals = new ArrayList<>();
        List<Integer> dts = new ArrayList<>();

        /**
         *  UP = 0;
         *  DOWN = 1;
         *  STAGNATION = 2;
         *  OTHER = 3;
         * */
        Series[] series = new Series[4];
        for(int i=0; i<4; i++) {
            series[i] = new Series();
            series[i].setName(getLabelByID(i).toString());
        }

        Time24 time = new Time24(0, 0, 0);
        for (int i=0; sc.hasNextLine(); i++) {
            String d = sc.nextLine();

            //h:m:s;val;label
            String[] str = d.split(";");
            Time24 dt = new Time24(str[0]);
            float val = parseFloat(str[1]);
            int label = Integer.parseInt(str[2]);

            time.add(dt.total_secs());

            vals.add(val);
            dts.add(time.total_secs());

            Data data = new Data(dts.get(i), vals.get(i));
            series[label].getData().add(data);
        }

        show(series, "Chart");
    }

}

