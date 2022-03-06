package main;

import converter.Converter;
import generator.Generator;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {new Main().main();stage.show();stage.close();}

    public static final int[] DURATION_SEC = new int[]{30*60, 120*60}; //seconds
    public static final int TIME_STEP = 60; // seconds
    public static final int OPERATION_DAILY = 36; // seconds
    public static final String PATH = "C:\\Java\\4.csv";
    public static final float MIN =  0.1f, MAX = 160.0f, BIAS = 10f;

    void main() {
        new Generator();
        new Converter();
    }
}
