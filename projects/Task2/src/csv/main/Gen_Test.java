package csv.main;

import csv.converter.Converter;
import csv.generator.Generator;
import javafx.application.Application;
import javafx.stage.Stage;

public class Gen_Test extends Application {
    @Override
    public void start(Stage stage) {new Gen_Test().main();stage.show();stage.close();}

    public static final int[] DURATION_SEC = new int[]{120*60, 240*60}; //minutes~seconds/60
    public static final int TIME_STEP = 10*60; // seconds
    public static final int OPERATION_DAILY = 50; // seconds
    public static final String PATH = "C:\\Java\\6.csv";//"C:\\Users\\Pupochek\\Documents\\AITU PC\\Данные Задание 2\\01\\1.csv";
    public static final float MIN =  0.1f, MAX = 1f, BIAS = 0.3f;

    void main() {
        //new Generator();
        new Converter();
    }
}
